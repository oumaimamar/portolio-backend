package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.model.MediaType;
import yool.ma.portfolioservice.model.SkillLevel;

import java.time.LocalDateTime;

@Data
public class UserMediaResponse {
    private Long id;
    private String name;
    private MediaType mediaType;

    private String filePath;
    private String description;

    private String category;
    private boolean verified;
}
