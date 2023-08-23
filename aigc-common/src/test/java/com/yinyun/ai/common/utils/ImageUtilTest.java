package com.yinyun.ai.common.utils;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;

import static com.yinyun.ai.common.utils.ImageUtil.getQRCResult;

class ImageUtilTest {

    public static void main(String[] args) throws NotFoundException, IOException, WriterException {
        File file = new File("aigc-common/src/test/resources/img.png");
        File file1 = ImageUtil.recognizeAndGenerateQRC(file, 768, 768, "png");
        Result qrcResult = getQRCResult(file1);
        System.out.println(qrcResult.getText());
    }
}