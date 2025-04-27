package yool.ma.portfolioservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "project_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Project project;

    private String fileName;
    private String filePath;
    private String fileType;  // Store mime type (e.g., image/jpeg, application/pdf)
    private long fileSize;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;  // Higher-level type category

    private LocalDateTime uploadDate;

    @PrePersist
    protected void onCreate() {
        uploadDate = LocalDateTime.now();
    }
}
