package yool.ma.portfolioservice.model;

public enum UserMediaCategory {
    RESUME("Resume/CV"),
    CERTIFICATE("Certifications"),
    PORTFOLIO("Portfolio Work"),
    EDUCATION("Education Documents"),
    IDENTIFICATION("Identification"),
    OTHER("Other");

    private final String displayName;

    UserMediaCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
