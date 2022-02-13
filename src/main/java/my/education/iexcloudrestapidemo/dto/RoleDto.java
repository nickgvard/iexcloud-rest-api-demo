package my.education.iexcloudrestapidemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import my.education.iexcloudrestapidemo.mysql.model.Role;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long id;
    private String name;

    public static Role toEntity(RoleDto roleDto) {
        return Role
                .builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }

    public static RoleDto toDto(Role roleEntity) {
        return RoleDto
                .builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();

    }
}
