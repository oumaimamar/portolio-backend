package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.model.MediaType;
import yool.ma.portfolioservice.model.SkillLevel;

import java.time.LocalDateTime;

@Data
public class UserMediaResponse {
    private Long id;
    private Long profileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
}
