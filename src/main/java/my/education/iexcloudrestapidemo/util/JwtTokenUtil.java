package my.education.iexcloudrestapidemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import my.education.iexcloudrestapidemo.model.Role;
import my.education.iexcloudrestapidemo.model.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class JwtTokenUtil {

    @Value("${jwt.token.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean validityToken(String token) {
        Date expiration = getExpirationFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", getRolesName())
    }

    private List<String> getRolesName(List<Role> roles) {
        return roles.stream().map();
    }
}
