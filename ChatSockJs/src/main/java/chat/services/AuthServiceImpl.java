package chat.services;

import chat.dto.TokenDto;
import chat.models.User;
import chat.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public TokenDto signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(userRepository.save(user));
        return getToken(user);
    }

    @Override
    public TokenDto signIn(User model) {
        Optional<User> userOptional = userRepository.findUserByUsername(model.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(model.getPassword(), user.getPassword())) {
                return getToken(user);
            }
        }
        return new TokenDto(null);
    }

    @Override
    public TokenDto getToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("name", user.getName())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return new TokenDto(token);
    }
}
