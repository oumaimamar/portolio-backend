package yool.ma.portfolioservice.controller;

import yool.ma.portfolioservice.dto.ProjectMediaRequest;
import yool.ma.portfolioservice.dto.ProjectMediaResponse;
import yool.ma.portfolioservice.security.service.ProjectMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects/{projectId}/media")
@RequiredArgsConstructor
public class ProjectMediaController {

    private final ProjectMediaService projectMediaService;

    @PostMapping
    public ResponseEntity<ProjectMediaResponse> createProjectMedia(
            @PathVariable Long projectId,
            @RequestBody ProjectMediaRequest request) {
        ProjectMediaResponse response = projectMediaService.createProjectMedia(projectId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMediaResponse>> getAllMediaByProjectId(
            @PathVariable Long projectId) {
        List<ProjectMediaResponse> responses = projectMediaService.getAllMediaByProjectId(projectId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{mediaId}")
    public ResponseEntity<ProjectMediaResponse> getProjectMediaById(
            @PathVariable Long projectId,
            @PathVariable Long mediaId) {
        ProjectMediaResponse response = projectMediaService.getProjectMediaById(mediaId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{mediaId}")
    public ResponseEntity<ProjectMediaResponse> updateProjectMedia(
            @PathVariable Long projectId,
            @PathVariable Long mediaId,
            @RequestBody ProjectMediaRequest request) {
        ProjectMediaResponse response = projectMediaService.updateProjectMedia(mediaId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteProjectMedia(
            @PathVariable Long projectId,
            @PathVariable Long mediaId) {
        projectMediaService.deleteProjectMedia(mediaId);
        return ResponseEntity.noContent().build();
    }
}
