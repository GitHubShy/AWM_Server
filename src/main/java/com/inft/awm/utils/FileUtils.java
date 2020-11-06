package com.inft.awm.utils;

import com.inft.awm.response.SimpleResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
/**
 * utils for File
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
public class FileUtils {

    /** Save a file to a specific path
     * @param fileDir
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    public static SimpleResult uploadFile(String fileDir, MultipartFile file, HttpServletRequest request) throws IOException {

        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName= UUID.randomUUID().toString().replaceAll("-", "")+suffix;

        File newFile = new File(fileDir + newFileName);

        try {
            file.transferTo(newFile);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/"  + newFileName;
            return new SimpleResult(url);
        } catch (IOException e) {
            throw e;
        }

    }

}
