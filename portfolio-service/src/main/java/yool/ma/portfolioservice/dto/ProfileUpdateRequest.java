package yool.ma.portfolioservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String diploma;
    private String profilePicture;
    private String bio;
    private List<String> socialLinks;
}