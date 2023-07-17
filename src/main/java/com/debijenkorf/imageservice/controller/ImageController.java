package com.debijenkorf.imageservice.controller;

import com.debijenkorf.imageservice.configuration.ImageType;
import com.debijenkorf.imageservice.configuration.PredefinedImageType;
import com.debijenkorf.imageservice.configuration.PredefinedImageTypes;
import com.debijenkorf.imageservice.exception.ImageNotFoundException;
import com.debijenkorf.imageservice.service.FlushImageService;
import com.debijenkorf.imageservice.service.ShowImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
@RestController
public class ImageController {

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private ShowImageService showImageService;

    private PredefinedImageTypes predefinedImageTypes;

    private FlushImageService flushImageService;

    @Autowired                                                                                      
    public ImageController(ShowImageService showImageService, PredefinedImageTypes predefinedImageTypes, FlushImageService flushImageService) {
        this.showImageService = showImageService;
        this.predefinedImageTypes = predefinedImageTypes;
        this.flushImageService = flushImageService;
    }

    @GetMapping( value = "/image/show/{type}/")
    public @ResponseBody ResponseEntity<InputStreamResource> showImage(@PathVariable String type, @RequestParam String reference) {
        return showImage(type, null, reference);
    }


    @GetMapping( value = "/image/show/{type}/{name}/")
    public @ResponseBody ResponseEntity<InputStreamResource> showImage(@PathVariable String type, @PathVariable String name, @RequestParam String reference) {
        Optional<PredefinedImageType> optionalImageType = predefinedImageTypes.getImageType(type);
        if (optionalImageType.isEmpty()) {
            logger.info("Invalid predefined image type {}", type);
            throw new ImageNotFoundException();
        }

        PredefinedImageType predefinedImageType = optionalImageType.get();
        BufferedImage image = showImageService.show(predefinedImageType, reference);

        MediaType contentType = predefinedImageType.imageType().equals(ImageType.PNG) ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(getInputStream(image, contentType)));

    }

    @DeleteMapping( value ="/image/flush/{type}/" )
    public void flushImage(@PathVariable String type, @RequestParam String reference) {
        flushImageService.flush(type, reference);
    }

    private InputStream getInputStream(BufferedImage image, MediaType contentType) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, contentType.getSubtype(), os);
        } catch (IOException e) {
            logger.error("Error while writing image to output stream", e);
            throw new ImageNotFoundException();
        }
        return new ByteArrayInputStream(os.toByteArray());
    }


}

