package com.debijenkorf.imageservice.service;

import com.debijenkorf.imageservice.configuration.PredefinedImageType;
import com.debijenkorf.imageservice.configuration.PredefinedImageTypes;
import com.debijenkorf.imageservice.exception.ImageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlushImageService {

    private final Logger logger = LoggerFactory.getLogger(FlushImageService.class);

    private final AWSNamingStrategy awsNamingStrategy;

    private final PredefinedImageTypes predefinedImageTypes;

    private final S3Client s3Client;

    @Autowired
    public FlushImageService(AWSNamingStrategy awsNamingStrategy, PredefinedImageTypes predefinedImageTypes, S3Client s3Client) {
        this.awsNamingStrategy = awsNamingStrategy;
        this.predefinedImageTypes = predefinedImageTypes;
        this.s3Client = s3Client;
    }

    public void flush(String imageType, String reference) {
        if("original".equals(imageType)) {
            for (PredefinedImageType predefinedImageType : predefinedImageTypes.getImageTypes()) {
                String awsPathName = awsNamingStrategy.getPathName(predefinedImageType.name(), reference);
                logger.info("Flushing image at path: {}", awsPathName);
                s3Client.flushImage(awsPathName);
            }
        }

        String awsPathName = awsNamingStrategy.getPathName(imageType, reference);
        logger.info("Flushing image at path: {}", awsPathName);
        s3Client.flushImage(awsPathName);
    }
}
