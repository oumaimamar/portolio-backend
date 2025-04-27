package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.model.MediaType;
import yool.ma.portfolioservice.model.SkillLevel;

@Data
public class UserMediaRequest {
    private String name;
    private MediaType mediaType;

    private String filePath;
    private String description;

    private String category;
    private boolean verified;
}
