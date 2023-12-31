package com.yinyun.ai.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yinyun.ai.common.constance.ServiceConstance;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//Class ImageUtil
public class ImageUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * <a href="https://www.cnblogs.com/huanzi-qch/p/10097791.html">...</a>
     * <a href="https://juejin.cn/post/7063457312907722789">...</a>
     */
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
        encodeHintTypeObjectMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //设置二维码边的空度，非负数
        encodeHintTypeObjectMap.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new QRCodeWriter()
                .encode(result.getText(), BarcodeFormat.QR_CODE, width, height, encodeHintTypeObjectMap);
        File tempFile = File.createTempFile("tmp-qrc", ".png");
//        File tempFile = new File("./tmp-qrc.png");
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 按照上面定义好的二维码颜色编码生成二维码
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        ImageIO.write(image, fileFormat, tempFile);
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
    public static String imageBase64Encode(String imagePath) throws Exception {
        //Read the file content from the given image path
        File file = new File(imagePath);
        byte[] fileContent = FileUtils.readFileToByteArray(file);
        //Encode the file content into a Base64 string
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static String imageQRCBase64Encode(String imagePath) throws Exception {
        //Read the file content from the given image path
        File file = new File(imagePath);
        file = recognizeAndGenerateQRC(file,
                ServiceConstance.QRCParams.QRC_WIDTH,
                ServiceConstance.QRCParams.QRC_HEIGHT,
                ServiceConstance.QRCParams.QRC_TYPE);
        byte[] fileContent = FileUtils.readFileToByteArray(file);
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