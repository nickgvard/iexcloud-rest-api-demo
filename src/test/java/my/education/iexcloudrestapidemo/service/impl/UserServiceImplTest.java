package my.education.iexcloudrestapidemo.service.impl;

import my.education.iexcloudrestapidemo.dto.UserDto;
import my.education.iexcloudrestapidemo.mysql.model.Role;
import my.education.iexcloudrestapidemo.mysql.model.User;
import my.education.iexcloudrestapidemo.mysql.repository.RoleRepository;
import my.education.iexcloudrestapidemo.mysql.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private DefaultOidcUser oidcUser;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenNewUserRegistered() {
        User newUser = user("test@test.com", "test");
        User expected = user(newUser.getEmail(), newUser.getName());
        expected.setId(1L);

        List<SimpleGrantedAuthority> roleUser = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        doReturn(roleUser).when(oidcUser).getAuthorities();
        doReturn(newUser.getEmail()).when(oidcUser).getEmail();
        doReturn(newUser.getName()).when(oidcUser).getFullName();

        given(roleRepository.findRoleByName(expected.getRoles().get(0).getName())).willReturn(expected.getRoles().get(0));
        given(userRepository.save(newUser)).willReturn(expected);

        UserDto actual = userService.sighUp(oidcUser);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getRoles().get(0).getName(), actual.getRolesDto().get(0).getName());
        assertEquals(expected.getRoles().get(0).getId(), actual.getRolesDto().get(0).getId());
    }

    @Test
    void whenExistsUserChangedByNameOnRegistration() {
        User existsUser = user("test@test.com", "test");
        User expected = user(existsUser.getEmail(), "test2");
        expected.setId(1L);

        doReturn(existsUser.getEmail()).when(oidcUser).getEmail();

        given(userRepository.findUserByEmail(existsUser.getEmail())).willReturn(existsUser);

        existsUser.setName(expected.getName());

        given(userRepository.save(existsUser)).willReturn(expected);

        UserDto actual = userService.sighUp(oidcUser);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    private User user(String email, String fullName) {
        return User.builder()
                .email(email)
                .name(fullName)
                .roles(List.of(Role.builder()
                        .id(1L)
                        .name("ROLE_USER")
                        .build()))
                .build();
    }
}