package com.debijenkorf.imageservice.service;

import com.debijenkorf.imageservice.configuration.PredefinedImageType;
import com.debijenkorf.imageservice.exception.ImageNotFoundException;
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
public class OptimizeImageService {

    public BufferedImage optimize(PredefinedImageType type, BufferedImage image) {
        //Optimize and resize image using type
        return image;
    }
}
