package com.debijenkorf.imageservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "predefined")
public class PredefinedImageTypes {
    private List<PredefinedImageType> imageTypes;

    public Optional<PredefinedImageType> getImageType(String type) {
        return imageTypes.stream().filter(t -> t.name().equals(type)).findFirst();
    }

    public List<PredefinedImageType> getImageTypes() {
        return imageTypes;
    }

    public void setImageTypes(List<PredefinedImageType> imageTypes) {
        this.imageTypes = imageTypes;
    }
}
