package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.ennum.ProjectStatus;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private Set<String> skills;
    // You can add mediaFiles and feedbacks here if needed
}
