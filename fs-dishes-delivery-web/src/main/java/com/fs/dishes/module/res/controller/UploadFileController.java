package com.fs.dishes.module.res.controller;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.module.res.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by liuwu on 2018/4/11 0011.
 */
@RestController
@RequestMapping("/api/upload")
public class UploadFileController extends AbstractController {


    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/img", method = RequestMethod.POST)
    public ResResult uploadImg(@RequestParam("imgFile") MultipartFile imgFile) {
        String rootUrl = this.getClass().getClassLoader().getResource("static").getPath();
        return fileUploadService.uploadImg(imgFile, rootUrl);
    }
}