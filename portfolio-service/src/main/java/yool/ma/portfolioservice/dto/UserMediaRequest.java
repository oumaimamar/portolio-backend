package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.model.MediaType;
import yool.ma.portfolioservice.model.SkillLevel;

@Data
public class UserMediaRequest {
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;

    private String titre;
    private String description;  // Added attribute for document description
    private String category;     // Added attribute for organizing documents by category
    private boolean verified;
}
