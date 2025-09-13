package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "文件上传相关接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {

        log.info("文件上传");
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        // 截取原始文件名的后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //构建新文件名
        String objectName = UUID.randomUUID().toString() + extension;

        //文件请求路径
        String filePath = null;
        try {
            filePath = aliOssUtil.upload(file.getBytes(),objectName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(filePath);
    }
}
