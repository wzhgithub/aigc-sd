package com.yinyun.ai.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {

    public static String imageBase64Encode(String imagePath) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static void imageBase64Decode(File imageFile, String base64Content) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content);
        FileUtils.writeByteArrayToFile(imageFile, decodedBytes);
    }
}
