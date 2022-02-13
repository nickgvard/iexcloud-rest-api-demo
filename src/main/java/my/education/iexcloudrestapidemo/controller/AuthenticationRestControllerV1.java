package my.education.iexcloudrestapidemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/oauth2")
@Slf4j
public class AuthenticationRestControllerV1 {

    @GetMapping("/callback")
    public ResponseEntity<?> getRedirect(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String token = ((DefaultOidcUser) oAuth2User).getIdToken().getTokenValue();
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
