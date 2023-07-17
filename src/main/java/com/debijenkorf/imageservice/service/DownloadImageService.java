package com.debijenkorf.imageservice.service;

import com.debijenkorf.imageservice.exception.ImageNotFoundException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Component
public class DownloadImageService {

    private final Logger logger = LoggerFactory.getLogger(DownloadImageService.class);

    @Value("${source-root-url}")
    private String sourceRootUrl;

    public BufferedImage downloadImage(String reference) {
        try {
            URL url = new URL(sourceRootUrl + reference);
            BufferedImage image = ImageIO.read(url);
            if (image == null) {
                logger.info("No image found in the source with reference {}", reference);
                throw new ImageNotFoundException();
            }
            return image;
        } catch (IOException e) {
            logger.error("Something went wrong while reaching the source", e);
            throw new ImageNotFoundException();
        }
    }
}
