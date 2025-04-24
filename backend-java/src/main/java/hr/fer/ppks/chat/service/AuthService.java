package hr.fer.ppks.chat.service;

import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public LoginResponse login(String username, String password) {
        RoomUser user = (RoomUser) userDetailsService.loadUserByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);

        return LoginResponse.builder()
                .token(jwtService.generateToken(user))
                .expiresIn(jwtService.getExpirationTime())
                .id(user.getId())
                .build();
    }
}
