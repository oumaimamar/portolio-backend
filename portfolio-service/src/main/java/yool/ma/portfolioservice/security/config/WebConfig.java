package yool.ma.portfolioservice.security.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/profile-pictures/**")
                .addResourceLocations("file:uploads/profile-pictures/")
                .setCachePeriod(3600);
    }
}