package com.fs.dishes.module.res.service;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.base.utils.IdGen;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by liuwu on 2018/4/11 0011.
 */
@Service
public class FileUploadService extends BaseService{

    @Value("${file.upload.max-size}")
    private String maxSize;

    @Value("${file.upload.path}")
    private String path;

    public ResResult uploadImg(MultipartFile imgfile, String rootPath) {
        if (imgfile == null) {
            return ResResult.error(300, "上传失败：文件为空");
        }
        if (imgfile.getSize() > Long.valueOf(maxSize)) {
            return ResResult.error(300, "上传失败：文件大小不能超过10M");
        }
        String origFile = imgfile.getOriginalFilename();
        String suffix = ".png";
        if (StringUtils.isNotBlank(origFile)) {
            suffix = origFile.substring(origFile.lastIndexOf("."));
        }
        String uploadPath = rootPath;
        String relativePath = File.separator + path + File.separator + IdGen.uuid() + suffix;
        String filePath = uploadPath + relativePath;
        logger.info("图片上传目录：{}，上传地址：{}", rootPath, filePath);
        try {
            File file = new File(filePath);
            imgfile.transferTo(file);
            return ResResult.ok().withData(relativePath);
        } catch (Exception e) {
            logger.error("图片上传失败", e);
            return ResResult.error(300, "文件上传失败，请重新上传！");
        }
    }
}
