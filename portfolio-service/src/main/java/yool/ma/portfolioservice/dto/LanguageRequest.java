package yool.ma.portfolioservice.dto;

import lombok.Data;
import yool.ma.portfolioservice.model.CertificationType;
import yool.ma.portfolioservice.model.ProficiencyLevel;

@Data
public class LanguageRequest {
    private String name;
    private ProficiencyLevel proficiency;
    private CertificationType certification;
    private String certificateUrl;
    private boolean nativeLanguage;
}
