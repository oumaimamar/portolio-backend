package yool.ma.portfolioservice.security.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.regex.Pattern;

public class MeaningfulTextValidator implements ConstraintValidator<ValidDescription, String> {
    private static final int MIN_WORDS = 5;
    private static final double MIN_AVG_WORD_LENGTH = 3.0;
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9\\s]");
    private static final Pattern REPEATED_CHARS_PATTERN = Pattern.compile("(.)\\1{3,}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Empty is valid (use @NotBlank if required)
        }

        // Check for repeated characters (like "aaaaa")
        if (REPEATED_CHARS_PATTERN.matcher(value).find()) {
            return false;
        }

        // Remove special characters for word analysis
        String cleanText = SPECIAL_CHARS_PATTERN.matcher(value).replaceAll("");
        String[] words = cleanText.trim().split("\\s+");

        // Check minimum word count
        if (words.length < MIN_WORDS) {
            return false;
        }

        // Calculate average word length
        double avgLength = Arrays.stream(words)
                .mapToInt(String::length)
                .average()
                .orElse(0);

        return avgLength >= MIN_AVG_WORD_LENGTH;
    }
}
