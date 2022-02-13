package my.education.iexcloudrestapidemo.service.impl;

import lombok.RequiredArgsConstructor;
import my.education.iexcloudrestapidemo.dto.UserDto;
import my.education.iexcloudrestapidemo.mysql.model.Role;
import my.education.iexcloudrestapidemo.mysql.model.User;
import my.education.iexcloudrestapidemo.mysql.repository.RoleRepository;
import my.education.iexcloudrestapidemo.mysql.repository.UserRepository;
import my.education.iexcloudrestapidemo.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDto sighUp(Object oAuth2User) {
        DefaultOidcUser oidcUser = (DefaultOidcUser) oAuth2User;
        String email = oidcUser.getEmail();

        User user = userRepository.findUserByEmail(email);

        if (Objects.isNull(user)) {
            List<Role> authorities = getAuthorities(oidcUser.getAuthorities());
            user = User.builder()
                    .email(email)
                    .name(oidcUser.getFullName())
                    .userImage(oidcUser.getPicture())
                    .roles(authorities)
                    .build();
        } else {
            user.setName(oidcUser.getName());
            user.setUserImage(oidcUser.getPicture());
        }

        User persist = userRepository.save(user);

        return UserDto.toDto(persist);
    }

    private List<Role> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        String roleName = authorities.stream()
                .toList()
                .get(0)
                .getAuthority();

        Role roleByName = roleRepository.findRoleByName(roleName);

        return List.of(roleByName);
    }
}
