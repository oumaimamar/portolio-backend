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
}
