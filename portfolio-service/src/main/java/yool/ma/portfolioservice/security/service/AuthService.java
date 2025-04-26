package yool.ma.portfolioservice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yool.ma.portfolioservice.dto.auth.JwtResponse;
import yool.ma.portfolioservice.dto.auth.LoginRequest;
import yool.ma.portfolioservice.dto.auth.RegisterRequest;
import yool.ma.portfolioservice.model.Profile;
import yool.ma.portfolioservice.model.Role;
import yool.ma.portfolioservice.model.User;
import yool.ma.portfolioservice.repository.UserRepository;
import yool.ma.portfolioservice.security.jwt.JwtUtils;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        return new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getProfile().getFirstName(),
                user.getProfile().getLastName()

        );
    }

    public User registerUser(RegisterRequest registerRequest) {
        // Create new user account
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : Role.APPRENANT);

        // Create profile for the user
        Profile profile = new Profile();
        profile.setFirstName(registerRequest.getFirstName());
        profile.setLastName(registerRequest.getLastName());
        profile.setUser(user);
        user.setProfile(profile);

        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
