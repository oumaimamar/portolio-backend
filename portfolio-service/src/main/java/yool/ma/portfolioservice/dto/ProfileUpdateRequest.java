package yool.ma.portfolioservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "Invalid phone number format")
    private String phoneNumber;

    private String diploma;
    private String profilePicture;

    @Size(max = 500)
    private String bio;

    private List<String> socialLinks;
}