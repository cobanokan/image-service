package com.debijenkorf.imageservice.configuration;

public record PredefinedImageType(String name, Integer height, Integer width, Integer quality, ScaleType scaleType, ImageType imageType, String fillColor) {
}
