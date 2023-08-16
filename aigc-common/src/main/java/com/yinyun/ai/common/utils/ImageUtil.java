package com.yinyun.ai.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

//Class ImageUtil
public class ImageUtil {

    //Method imageBase64Encode
    public static String imageBase64Encode(String imagePath) throws IOException {
        //Read the file content from the given image path
        byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath));
        //Encode the file content into a Base64 string
        return Base64.getEncoder().encodeToString(fileContent);
    }

    //Method imageBase64Decode
    public static void imageBase64Decode(File imageFile, String base64Content) throws IOException {
        //Decode the Base64 string into a byte array
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content);
        //Write the decoded byte array to the given image file
        FileUtils.writeByteArrayToFile(imageFile, decodedBytes);
    }

    //Method downloadImage2File
    public static void downloadImage2File(String url, File imageFile) throws IOException {
        //Copy the given URL to the given image file
        FileUtils.copyURLToFile(new URL(url), imageFile);
    }
}