package com.tiansen.ordermanager.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

public class ImgUploadUtils {
    public static void uploadImgs(MultipartFile img, String filepath) throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(filepath);
            out.write(img.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
