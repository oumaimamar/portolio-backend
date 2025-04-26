package yool.ma.portfolioservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tech_skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechSkill {

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
