package yool.ma.portfolioservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yool.ma.portfolioservice.dto.ProfileUpdateRequest;
import yool.ma.portfolioservice.model.Profile;
import yool.ma.portfolioservice.security.service.ImageVerificationService;
import yool.ma.portfolioservice.security.service.ProfileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    @Autowired
    private ImageVerificationService imageVerificationService;

    private final ProfileService profileService;
    private static final String UPLOAD_DIR = "uploads/profile-pictures/";

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long userId) {
        Profile profile = profileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileUpdateRequest profileUpdateRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Profile updatedProfile = profileService.updateProfile(userId, profileUpdateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/{userId}/picture")
    public ResponseEntity<Map<String, Object>> uploadProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        try {
            // First verify the image
            Map<String, Object> verificationResult = imageVerificationService.verifyImage(file);

            // Check if we should block the upload
            if (verificationResult.get("shouldBlock").equals(true)) {
                Map<String, Object> response = new HashMap<>();
                response.put("error", true);
                response.put("message", verificationResult.get("message"));
                return ResponseEntity.badRequest().body(response);
            }

            // Continue with upload if not blocked
            String uploadDir = System.getProperty("user.dir") + "/uploads/profile-pictures/";
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String webPath = "/uploads/profile-pictures/" + fileName;

            Profile updatedProfile = profileService.updateProfilePicture(userId, webPath);

            Map<String, Object> response = new HashMap<>();
            response.put("profile", updatedProfile);
            response.put("verification", verificationResult);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile picture", e);
        }
    }
}