package yool.ma.portfolioservice.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



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
    private Project project;

    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
}
