package yool.ma.portfolioservice.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yool.ma.portfolioservice.dto.ProjectMediaRequest;
import yool.ma.portfolioservice.dto.ProjectMediaResponse;
import yool.ma.portfolioservice.model.Project;
import yool.ma.portfolioservice.model.ProjectMedia;
import yool.ma.portfolioservice.repository.ProjectMediaRepository;
import yool.ma.portfolioservice.repository.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMediaService {

    private final ProjectMediaRepository projectMediaRepository;
    private final ProjectRepository projectRepository;

    // Create a new project media
    public ProjectMediaResponse createProjectMedia(Long projectId, ProjectMediaRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        ProjectMedia projectMedia = new ProjectMedia();
        mapRequestToProjectMedia(request, projectMedia);
        projectMedia.setProject(project);

        ProjectMedia savedProjectMedia = projectMediaRepository.save(projectMedia);
        return mapToResponse(savedProjectMedia);
    }

    // Get all media for a project
    public List<ProjectMediaResponse> getAllMediaByProjectId(Long projectId) {
        return projectMediaRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get media by ID
    public ProjectMediaResponse getProjectMediaById(Long id) {
        ProjectMedia projectMedia = projectMediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project media not found with id: " + id));
        return mapToResponse(projectMedia);
    }

    // Update media
    @Transactional
    public ProjectMediaResponse updateProjectMedia(Long id, ProjectMediaRequest request) {
        ProjectMedia projectMedia = projectMediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project media not found with id: " + id));

        mapRequestToProjectMedia(request, projectMedia);
        ProjectMedia updatedProjectMedia = projectMediaRepository.save(projectMedia);
        return mapToResponse(updatedProjectMedia);
    }

    // Delete media
    public void deleteProjectMedia(Long id) {
        if (!projectMediaRepository.existsById(id)) {
            throw new RuntimeException("Project media not found with id: " + id);
        }
        projectMediaRepository.deleteById(id);
    }

    // Helper methods
    private ProjectMediaResponse mapToResponse(ProjectMedia projectMedia) {
        ProjectMediaResponse response = new ProjectMediaResponse();
        response.setId(projectMedia.getId());
        response.setProjectId(projectMedia.getProject().getId());
        response.setFileName(projectMedia.getFileName());
        response.setFilePath(projectMedia.getFilePath());
        response.setFileType(projectMedia.getFileType());
        response.setFileSize(projectMedia.getFileSize());
        return response;
    }

    private void mapRequestToProjectMedia(ProjectMediaRequest request, ProjectMedia projectMedia) {
        projectMedia.setFileName(request.getFileName());
        projectMedia.setFilePath(request.getFilePath());
        projectMedia.setFileType(request.getFileType());
        projectMedia.setFileSize(request.getFileSize());
    }
}
