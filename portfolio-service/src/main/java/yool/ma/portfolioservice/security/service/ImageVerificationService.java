package yool.ma.portfolioservice.security.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageVerificationService {

    public Map<String, Object> verifyImage(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image == null) {
                result.put("isHuman", false);
                result.put("message", "⚠ Invalid image format (upload allowed)");
                result.put("shouldBlock", false);
                return result;
            }

            // Calculate various image properties
            double aspectRatio = (double) image.getHeight() / image.getWidth();
            boolean hasGoodAspect = aspectRatio >= 0.8 && aspectRatio <= 2.5; // Wider range for acceptance
            boolean hasColorVariation = hasSignificantColorVariation(image);
            boolean hasFaceLikeFeatures = checkFaceLikeFeatures(image);

            // More lenient verification - only block obviously non-human images
            boolean isLikelyHuman = hasGoodAspect && (hasColorVariation || hasFaceLikeFeatures);

            String message;
            boolean shouldBlock = false;

            if (isLikelyHuman) {
                message = "✓ Profile picture accepted";
            } else if (!hasGoodAspect) {
                message = "⚠ Unusual image proportions (upload blocked)";
                shouldBlock = true;
            } else if (!hasColorVariation) {
                message = "⚠ Image lacks sufficient color variation (upload blocked)";
                shouldBlock = true;
            } else {
                message = "✓ Image accepted (verification inconclusive)";
            }

            result.put("isHuman", isLikelyHuman);
            result.put("message", message);
            result.put("shouldBlock", shouldBlock);

        } catch (Exception e) {
            result.put("isHuman", false);
            result.put("message", "⚠ Could not analyze image (upload allowed)");
            result.put("shouldBlock", false);
        }

        return result;
    }

    private boolean hasSignificantColorVariation(BufferedImage image) {
        // Check multiple points for color variation
        int[] samplePoints = {
                image.getRGB(image.getWidth()/4, image.getHeight()/4),
                image.getRGB(image.getWidth()*3/4, image.getHeight()/4),
                image.getRGB(image.getWidth()/2, image.getHeight()/2),
                image.getRGB(image.getWidth()/4, image.getHeight()*3/4),
                image.getRGB(image.getWidth()*3/4, image.getHeight()*3/4)
        };

        // Calculate average differences
        long totalDiff = 0;
        int comparisons = 0;

        for (int i = 0; i < samplePoints.length; i++) {
            for (int j = i+1; j < samplePoints.length; j++) {
                totalDiff += colorDifference(samplePoints[i], samplePoints[j]);
                comparisons++;
            }
        }

        double avgDifference = (double) totalDiff / comparisons;
        return avgDifference > 5000; // Lower threshold than before
    }

    private int colorDifference(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;

        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;

        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }

    private boolean checkFaceLikeFeatures(BufferedImage image) {
        // Very basic checks that might indicate a face
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;

        // Check if center is brighter than edges (common in portraits)
        int centerBrightness = calculateBrightness(image.getRGB(centerX, centerY));
        int edgeBrightness = calculateBrightness(image.getRGB(10, 10));

        return centerBrightness > edgeBrightness;
    }

    private int calculateBrightness(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        return (r + g + b) / 3;
    }
}