package com.debijenkorf.imageservice.service;

import com.debijenkorf.imageservice.exception.S3ClientException;

import java.awt.image.BufferedImage;


public interface S3Client {

    public void uploadImage(String filename, BufferedImage image) throws S3ClientException;

    public boolean doesImageExist(String filename) throws S3ClientException;

    public BufferedImage getImage(String filename) throws S3ClientException;

    void flushImage(String awsPathName) throws S3ClientException;
}
