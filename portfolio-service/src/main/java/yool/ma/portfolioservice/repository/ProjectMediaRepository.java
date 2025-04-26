package yool.ma.portfolioservice.repository;

import yool.ma.portfolioservice.model.ProjectMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectMediaRepository extends JpaRepository<ProjectMedia, Long> {
    List<ProjectMedia> findByProjectId(Long projectId);
}