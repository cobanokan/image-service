package com.debijenkorf.imageservice.service;

import org.springframework.stereotype.Component;

@Component
public class AWSNamingStrategy {

    public String getPathName(String typeName, String reference) {
        String ref = reference.replace("/", "_");
        String filename = ref.split("\\.")[0];

        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/").append(typeName).append("/");
        if (filename.length() > 4) {
            String firstFolder = filename.substring(0, 4);
            pathBuilder.append(firstFolder).append("/");
        }

        if (filename.length() > 8) {
            String secondFolder = filename.substring(4, 8);
            pathBuilder.append(secondFolder).append("/");
        }

        pathBuilder.append(ref);

        return pathBuilder.toString();
    }
}
