package yool.ma.portfolioservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yool.ma.portfolioservice.dto.ProfileUpdateRequest;
import yool.ma.portfolioservice.model.Profile;
import yool.ma.portfolioservice.security.service.ProfileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

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
    public ResponseEntity<Profile> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        Profile updatedProfile = profileService.updateProfile(userId, profileUpdateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/{userId}/picture")
    public ResponseEntity<Profile> uploadProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        try {
            // Define the upload directory
            String uploadDir = System.getProperty("user.dir") + "/uploads/profile-pictures/";

            // Create upload directory if it doesn't exist
            Files.createDirectories(Paths.get(uploadDir));

            // Generate unique filename
            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Paths.get(uploadDir + fileName);

            // Save file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // The URL path that will be used by the frontend to access the image
            String webPath = "/uploads/profile-pictures/" + fileName;

            Profile updatedProfile = profileService.updateProfilePicture(userId, webPath);
            return ResponseEntity.ok(updatedProfile);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile picture", e);
        }
    }
    }