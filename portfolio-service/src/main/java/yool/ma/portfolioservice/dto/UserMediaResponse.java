package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.model.MediaType;
import yool.ma.portfolioservice.model.SkillLevel;

import java.time.LocalDateTime;

@Data
public class UserMediaResponse {
    private Long id;
    private String name;
    private SkillLevel level;
    private String category;
    private int yearsOfExperience;
    private boolean verified;
}
