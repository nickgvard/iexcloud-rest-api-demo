package my.education.iexcloudrestapidemo.service.impl;

import my.education.iexcloudrestapidemo.dto.UserDto;
import my.education.iexcloudrestapidemo.mysql.model.Role;
import my.education.iexcloudrestapidemo.mysql.model.User;
import my.education.iexcloudrestapidemo.mysql.repository.RoleRepository;
import my.education.iexcloudrestapidemo.mysql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DefaultOidcUser oidcUser;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private Collection<? extends GrantedAuthority> authorities;

    @Test
    void whenNewUserRegistered() {
        User newUser = user("test@test.com", "test");

        User expected = user(newUser.getEmail(), newUser.getName());
        expected.setId(1L);



//        given(userRepository.findUserByEmail(expected.getEmail())).willReturn(null);
//
//        userRepository.findUserByEmail(newUser.getEmail());
//
//        given(oidcUser.getAuthorities().stream().toList().get(0).getAuthority()).willReturn(expected.getRoles().get(0).getName());
//
//        String roleUser = oidcUser.getAuthorities().stream().toList().get(0).getAuthority();
//
//        given(roleRepository.findRoleByName(roleUser)).willReturn(expected.getRoles().get(0));
//
//        roleRepository.findRoleByName(roleUser);
//        List<SimpleGrantedAuthority> role_user = List.of(new SimpleGrantedAuthority("ROLE_USER"));
//        given(oidcUser.getAuthorities()).willReturn();

        given(userRepository.save(newUser)).willReturn(expected);

        UserDto actual = userService.sighUp(oidcUser);

        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getRoles().get(0).getName(), actual.getRolesDto().get(0).getName());
        assertEquals(expected.getId(), actual.getId());
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