package com.yinyun.ai.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yinyun.ai.common.constance.ServiceConstance;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//Class ImageUtil
public class ImageUtil {
    public static File recognizeAndGenerateQRC(File rawFile, int width, int height, String fileFormat)
            throws IOException, NotFoundException, WriterException {
        Result result = getQRCResult(rawFile);
        return getGenQRCFile(width, height, fileFormat, result);
    }

    private static File getGenQRCFile(int width, int height, String fileFormat, Result result) throws WriterException, IOException {
        Map<EncodeHintType, Object> encodeHintTypeObjectMap = new HashMap<>();
        //内容编码格式
        encodeHintTypeObjectMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        encodeHintTypeObjectMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码边的空度，非负数
        encodeHintTypeObjectMap.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(result.getText(), BarcodeFormat.QR_CODE, width, height, encodeHintTypeObjectMap);
//        File tempFile = new File("./test.png");
        File tempFile = File.createTempFile("./tmp-qrc", ".png");
        Path path = tempFile.toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, fileFormat, path);// 输出原图片
        return tempFile;
    }

    static Result getQRCResult(File rawFile) throws IOException, NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        //读取指定的二维码文件
        BufferedImage bufferedImage = ImageIO.read(rawFile);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        //定义二维码参数
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Result result = formatReader.decode(binaryBitmap, hints);
        bufferedImage.flush();
        return result;
    }

    public static String imageUrlB64Str(String url) throws Exception {
        File b64 = File.createTempFile("b64", ".tmp");
        FileUtils.copyURLToFile(new URL(url), b64, 2000, 10 * 1000);
        // 识别并生成新的qrc
        File generateQRC = recognizeAndGenerateQRC(b64,
                ServiceConstance.QRCParams.QRC_WIDTH,
                ServiceConstance.QRCParams.QRC_HEIGHT,
                ServiceConstance.QRCParams.QRC_TYPE);
        byte[] fileContent = FileUtils.readFileToByteArray(generateQRC);
        //Encode the file content into a Base64 string
        return Base64.getEncoder().encodeToString(fileContent);
    }

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