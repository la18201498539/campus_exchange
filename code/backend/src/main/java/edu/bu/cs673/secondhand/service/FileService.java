package edu.bu.cs673.secondhand.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/***
 Email: ybinman@bu.edu
 DateTime: 10/14/24-14:15
 *****/
public interface FileService {

    /**
     *
     * @param multipartFile
     * @param fileName
     * @return
     * @throws IOException
     */
    boolean uploadFile(MultipartFile multipartFile, String fileName)throws IOException;
}
