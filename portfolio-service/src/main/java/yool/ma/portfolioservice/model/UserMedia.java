package yool.ma.portfolioservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
    @Table(name = "user_media")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    private String category; // e.g., "Programming", "Database", "DevOps"
    private int yearsOfExperience;
    private boolean verified;
}