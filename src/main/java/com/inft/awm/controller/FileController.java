package com.inft.awm.controller;


import com.inft.awm.response.SimpleResult;
import com.inft.awm.utils.FileUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/awm_server/file")
@Api(tags = "Interfaces for files")
public class FileController {

    @Value("${file-save-path}")
    private String fileSavePath;

    @PostMapping(value = "/upload")
    @ApiOperation(value = "Upload a file", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="body",name="file",required=true,value="The file you want upload",dataTypeClass = File.class),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success",response = SimpleResult.class),
            @ApiResponse(code = 1, message = "failed reason is shown in message",response = SimpleResult.class),
    })
    public SimpleResult register(MultipartFile file, HttpServletRequest request) throws IOException {
        SimpleResult sr = FileUtils.uploadFile(fileSavePath,file,request);
        return sr;
    }

}
