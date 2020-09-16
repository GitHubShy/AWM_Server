package com.inft.awm.utils;

import com.inft.awm.response.SimpleResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

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
            //协议 :// ip地址 ：端口号 / 文件目录(/images/2020/03/15/xxx.jpg)
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/"  + newFileName;
            return new SimpleResult(url);
        } catch (IOException e) {
            throw e;
        }

    }
}
