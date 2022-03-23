package my.education.iexcloudrestapidemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.education.iexcloudrestapidemo.mysql.model.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationDto {

    private String name;
    private String email;

    public static UserRegistrationDto toDto(UserDto userDto) {
        return UserRegistrationDto.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static UserRegistrationDto toDto(User user) {
        return UserRegistrationDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
