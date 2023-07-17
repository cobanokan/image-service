package com.debijenkorf.imageservice.service.impl;

import com.debijenkorf.imageservice.exception.S3ClientException;
import com.debijenkorf.imageservice.service.S3Client;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Component
public class FakeS3Client implements S3Client {

    private Map<String, BufferedImage> images = new HashMap<>();

    @Override
    public void uploadImage(String filename, BufferedImage image) {
        images.put(filename, image);
    }

    @Override
    public boolean doesImageExist(String filename) {
        return images.containsKey(filename);
    }

    @Override
    public BufferedImage getImage(String filename) {
        return images.get(filename);
    }

    @Override
    public void flushImage(String awsPathName) throws S3ClientException {
        images.remove(awsPathName);
    }
}
