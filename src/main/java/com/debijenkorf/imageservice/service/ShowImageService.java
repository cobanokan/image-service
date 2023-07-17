package com.debijenkorf.imageservice.service;

import com.debijenkorf.imageservice.configuration.PredefinedImageType;
import com.debijenkorf.imageservice.exception.ImageNotFoundException;
import com.debijenkorf.imageservice.exception.S3ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ShowImageService {

    private final Logger logger = LoggerFactory.getLogger(ShowImageService.class);

    private final AWSNamingStrategy awsNamingStrategy;

    private final S3Client s3Client;

    private final DownloadImageService downloadImageService;

    private final OptimizeImageService optimizeImageService;

    @Autowired
    public ShowImageService(AWSNamingStrategy awsNamingStrategy, S3Client s3Client, DownloadImageService downloadImage, OptimizeImageService optimizeImageService) {
        this.awsNamingStrategy = awsNamingStrategy;
        this.s3Client = s3Client;
        this.downloadImageService = downloadImage;
        this.optimizeImageService = optimizeImageService;
    }

    public BufferedImage show(PredefinedImageType predefinedImageType, String reference) {
        String awsPathName = awsNamingStrategy.getPathName(predefinedImageType.name(), reference);

        try {
            if (s3Client.doesImageExist(awsPathName)) {
                return s3Client.getImage(awsPathName);
            } else {
                String originalImagePath = awsNamingStrategy.getPathName("original", reference);

                BufferedImage originalImage = null;
                if (s3Client.doesImageExist(originalImagePath)) {
                    originalImage = s3Client.getImage(originalImagePath);
                } else {
                    originalImage = downloadImageService.downloadImage(reference);
                    s3Client.uploadImage(originalImagePath, originalImage);
                }

                BufferedImage resizedImage = optimizeImageService.optimize(predefinedImageType, originalImage);
                s3Client.uploadImage(awsPathName, resizedImage);
                return resizedImage;
            }
        } catch (S3ClientException e) {
            logger.error("Failed to connect to S3", e);
            throw new ImageNotFoundException();
        }
    }
}
