package my.education.iexcloudrestapidemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import my.education.iexcloudrestapidemo.mysql.model.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String name;
    private String userImage;
    private String email;
    private List<RoleDto> rolesDto;

    public static User toEntity(UserDto userDto) {
        return User
                .builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .roles(Objects.isNull(userDto.getRolesDto()) ? null : userDto
                        .getRolesDto()
                        .stream()
                        .map(RoleDto::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public static UserDto toDto(User userEntity) {
        return UserDto
                .builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .rolesDto(Objects.isNull(userEntity.getRoles()) ? null : userEntity
                        .getRoles()
                        .stream()
                        .map(RoleDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
