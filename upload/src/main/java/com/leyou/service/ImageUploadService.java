package com.leyou.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class ImageUploadService {

    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private UploadProperties properties;
    public  String uploadImage(MultipartFile file) {
        String type = file.getContentType();
        // 图片上传类型
        if (!properties.getAllowType().contains(type)) {
            log.error("文件类型不匹配");
            throw new LyException(ExceptionEnum.FILE_TYPE_ERROR);
        }
        // 校验文件内容
        try {
            BufferedImage read = ImageIO.read(file.getInputStream());
            if (read == null) {
                throw new LyException(ExceptionEnum.FILE_TYPE_ERROR);
            }
            String extension= StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            return  properties.getBaseUpLoad() + storePath.getFullPath();
        } catch (IOException e) {
           throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }

}
