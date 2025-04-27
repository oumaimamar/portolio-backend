package yool.ma.portfolioservice.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import yool.ma.portfolioservice.dto.UserMediaRequest;
import yool.ma.portfolioservice.dto.UserMediaResponse;
import yool.ma.portfolioservice.security.service.UserMediaService;


import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profiles/{profileId}/user-media")
@RequiredArgsConstructor
public class UserMediaController {

    private final UserMediaService userMediaService;

    @PostMapping
    public ResponseEntity<UserMediaResponse> createUserMedia(
            @PathVariable Long profileId,
            @RequestBody UserMediaRequest request) {
        UserMediaResponse response = userMediaService.createUserMedia(profileId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserMediaResponse>> getAllUserMediasByProfileId(
            @PathVariable Long profileId) {
        List<UserMediaResponse> responses = userMediaService.getAllUserMediasByProfileId(profileId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{userMediaId}")
    public ResponseEntity<UserMediaResponse> updateUserMedia(
            @PathVariable Long profileId,
            @PathVariable Long userMediaId,
            @RequestBody UserMediaRequest request) {
        UserMediaResponse response = userMediaService.updateUserMedia(userMediaId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userMediaId}")
    public ResponseEntity<Void> deleteUserMedia(
            @PathVariable Long profileId,
            @PathVariable Long userMediaId) {
        userMediaService.deleteUserMedia(userMediaId);
        return ResponseEntity.noContent().build();
    }
}

