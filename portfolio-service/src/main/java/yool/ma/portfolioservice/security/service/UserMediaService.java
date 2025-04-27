package yool.ma.portfolioservice.security.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yool.ma.portfolioservice.dto.UserMediaRequest;
import yool.ma.portfolioservice.dto.UserMediaResponse;
import yool.ma.portfolioservice.model.*;
import yool.ma.portfolioservice.repository.*;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMediaService {

    private final UserMediaRepository userMediaRepository;
    private final ProfileRepository profileRepository;

    public UserMediaResponse createUserMedia(Long profileId, UserMediaRequest request) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + profileId));

        if (userMediaRepository.existsByNameAndProfileId(request.getName(), profileId)) {
            throw new RuntimeException("Document already exists for this profile");
        }

        UserMedia userMedia = new UserMedia();
        mapRequestToUserMedia(request, userMedia);
        userMedia.setProfile(profile);

        UserMedia savedUserMedia = userMediaRepository.save(userMedia);
        return mapToResponse(savedUserMedia);
    }

    public List<UserMediaResponse> getAllUserMediasByProfileId(Long profileId) {
        return userMediaRepository.findByProfileId(profileId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserMediaResponse updateUserMedia(Long id, UserMediaRequest request) {
        UserMedia userMedia = userMediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));

        mapRequestToUserMedia(request, userMedia);
        UserMedia updatedUserMedia = userMediaRepository.save(userMedia);
        return mapToResponse(updatedUserMedia);
    }

    public void deleteUserMedia(Long id) {
        if (!userMediaRepository.existsById(id)) {
            throw new RuntimeException("Document not found with id: " + id);
        }
        userMediaRepository.deleteById(id);
    }

    private UserMediaResponse mapToResponse(UserMedia userMedia) {
        UserMediaResponse response = new UserMediaResponse();
        response.setId(userMedia.getId());
        response.setName(userMedia.getName());
        response.setMediaType(userMedia.getMediaType());
        response.setFilePath(userMedia.getFilePath());
        response.setDescription(userMedia.getDescription());
        response.setCategory(userMedia.getCategory());
        response.setVerified(userMedia.isVerified());
        return response;
    }

    private void mapRequestToUserMedia(UserMediaRequest request, UserMedia userMedia) {
        userMedia.setName(request.getName());
        userMedia.setMediaType(request.getMediaType());
        userMedia.setFilePath(request.getFilePath());
        userMedia.setDescription(request.getDescription());
        userMedia.setCategory(request.getCategory());
        userMedia.setVerified(request.isVerified());
    }
}
