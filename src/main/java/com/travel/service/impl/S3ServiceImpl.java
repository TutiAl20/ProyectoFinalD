package com.travel.service.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.travel.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3ServiceImpl implements S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public String subirImagen(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Generar un nombre único
        try {
            // Configuración de los metadatos
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // Subir el archivo a S3
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);

            // Retornar la URL del archivo
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen a S3: " + e.getMessage());
        }
    }

    public void eliminarImagen(String imagenUrl) {
        if(imagenUrl != null){
            String key = imagenUrl.replace("https://"+ bucketName + ".s3.amazonaws.com/", "");
            amazonS3.deleteObject(bucketName, key);// Extraer el key
        }
    }

}

