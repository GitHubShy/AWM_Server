package com.inft.awm.controller;


import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.response.ResponseAircraft;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.utils.FileUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
/**
 * Receive request about upload
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@RestController
@RequestMapping("/awm_server/file")
@Api(tags = "Interfaces for files")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@NeedToken
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
    @CrossOrigin
    public SimpleResult upload(MultipartFile file, HttpServletRequest request) throws IOException {
        SimpleResult sr = FileUtils.uploadFile(fileSavePath,file,request);
        return sr;
    }

}
