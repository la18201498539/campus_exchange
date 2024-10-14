package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/***
 Email: ybinman@bu.edu
 DateTime: 10/14/24-14:16
 *****/
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = Logger.getLogger(FileServiceImpl.class.getName());

    @Value("${userFilePath}")
    private String userFilePath;

    public boolean uploadFile(MultipartFile multipartFile,String fileName)throws IOException {
        File fileDir = new File(userFilePath);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                return false;
            }
        }

        logger.info(fileDir.getAbsolutePath() +"/"+fileName);
        File file = new File(fileDir.getAbsolutePath() +"/"+fileName);
        if (file.exists()) {
            if (!file.delete()) {
                return false;
            }
        }
        if (file.createNewFile()) {
            multipartFile.transferTo(file);
            return true;
        }
        return false;
    }
}
