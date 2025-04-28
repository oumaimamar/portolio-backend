package yool.ma.portfolioservice.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import yool.ma.portfolioservice.model.UserMedia;
import yool.ma.portfolioservice.security.service.UserMediaService;


import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user-media")
public class UserMediaController {

    @Autowired
    private UserMediaService userMediaService;

    @PostMapping("/{profileId}/upload")
//    @PreAuthorize("hasRole('APPRENANT') or hasRole('RESPONSABLE')")
    public ResponseEntity<UserMedia> uploadFile(
            @PathVariable Long profileId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("mediaType")yool.ma.portfolioservice.model.MediaType mediaType,
            @RequestParam("titre") String titre,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("verified") boolean verified) {

        UserMedia media = userMediaService.addProjectMedia(profileId, file, mediaType, titre, description, category, verified);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<List<UserMedia>> getUserMedia(@PathVariable Long profileId) {
        List<UserMedia> mediaList = userMediaService.getProjectMediaByProject(profileId);
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping("/{profileId}/type/{mediaType}")
    public ResponseEntity<List<UserMedia>> getProjectMediaByType(
            @PathVariable Long profileId,
            @PathVariable yool.ma.portfolioservice.model.MediaType mediaType) {

        List<UserMedia> mediaList = userMediaService.getProjectMediaByType(profileId, mediaType);
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping("/download/{mediaId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long mediaId) {
        UserMedia media = userMediaService.getProjectMediaByProject(mediaId).stream()
                .filter(m -> m.getId().equals(mediaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Media not found"));

        byte[] fileContent = userMediaService.getProjectMediaContent(mediaId);

        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(media.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getFileName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<?> deleteMedia(@PathVariable Long mediaId) {
        userMediaService.deleteProjectMedia(mediaId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/media-types")
    public ResponseEntity<yool.ma.portfolioservice.model.MediaType[]> getMediaTypes() {
        return ResponseEntity.ok(yool.ma.portfolioservice.model.MediaType.values());
    }
}

