package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.model.SkillLevel;

@Data
public class UserMediaRequest {
    private String name;
    private SkillLevel level;
    private String category;
    private int yearsOfExperience;
    private boolean verified;
}
