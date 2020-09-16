package com.inft.awm.controller;


import com.inft.awm.response.SimpleResult;
import com.inft.awm.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/awm_server/file")
public class FileController {

    @Value("${file-save-path}")
    private String fileSavePath;

    @PostMapping(value = "/upload")
    public SimpleResult register(MultipartFile file, HttpServletRequest request) throws IOException {
        SimpleResult sr = FileUtils.uploadFile(fileSavePath,file,request);
        return sr;
    }

}
