
package yool.ma.portfolioservice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yool.ma.portfolioservice.dto.MessageResponse;
import yool.ma.portfolioservice.dto.auth.JwtResponse;
import yool.ma.portfolioservice.dto.auth.LoginRequest;
import yool.ma.portfolioservice.dto.auth.RegisterRequest;
import yool.ma.portfolioservice.ennum.Role;
import yool.ma.portfolioservice.model.Profile;
import yool.ma.portfolioservice.model.User;
import yool.ma.portfolioservice.repository.UserRepository;
import yool.ma.portfolioservice.security.jwt.JwtUtils;

import java.util.Optional;

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

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        // Vérifier si l'utilisateur existe déjà avec le même username ET email
        Optional<User> existingUser = userRepository.findByUsernameAndEmail(
                registerRequest.getUsername(),
                registerRequest.getEmail()
        );

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Si déjà LAUREAT
            if (user.getRole() == Role.LAUREAT) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Already registered as LAUREAT"));
            }

            // Mise à jour vers LAUREAT
            user.setPassword(encoder.encode(registerRequest.getPassword()));
            user.setRole(Role.LAUREAT);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User upgraded to LAUREAT successfully!"));
        }

        // Vérifications standard pour nouveau compte
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use"));
        }

        // Nouvel utilisateur - APPRENANT par défaut
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.APPRENANT);

        Profile profile = new Profile();
        profile.setFirstName(registerRequest.getFirstName());
        profile.setLastName(registerRequest.getLastName());
        profile.setEmail(registerRequest.getEmail());
        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully as APPRENANT!"));
    }


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
