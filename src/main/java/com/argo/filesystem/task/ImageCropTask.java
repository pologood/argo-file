package com.argo.filesystem.task;

import com.argo.filesystem.FileTaskInfo;
import com.argo.filesystem.FilesConfig;
import com.google.common.io.Files;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by yamingd on 9/10/15.
 */
public class ImageCropTask extends AbstractFileTask {

    public static final String tag = "crop";

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public boolean execute(FileTaskInfo fileTaskInfo) throws Exception {

        String filePath = fileTaskInfo.getFilePath();

        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(filePath));
        } catch (FileNotFoundException e) {
            logger.error("File not found. file=" + filePath);
            return false;
        } catch (IOException e) {
            logger.error("File read Error. file=" + filePath, e);
            return false;
        }

        try {

            int x = Integer.parseInt(fileTaskInfo.getParams().get("x"));
            int y = Integer.parseInt(fileTaskInfo.getParams().get("y"));
            int width = Integer.parseInt(fileTaskInfo.getParams().get("width"));
            int height = Integer.parseInt(fileTaskInfo.getParams().get("height"));

            BufferedImage resultImage = Scalr.crop(originalImage, x, y, width, height);

            if (logger.isDebugEnabled()){
                logger.debug("original, size({}, {})", originalImage.getWidth(), originalImage.getHeight());
                logger.debug("crop, size({}, {})", resultImage.getWidth(), resultImage.getHeight());
            }

            String ext = Files.getFileExtension(filePath);

            for (int w : FilesConfig.instance.getThumbs()) {

                BufferedImage thumbnail = Scalr.resize(resultImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
                        w, w, Scalr.OP_ANTIALIAS);

                String newFilename = String.format("%s.%s.%s", filePath, w, ext);

                if (!ImageIO.write(thumbnail, ext, new File(newFilename))) {
                    logger.error("File write failed. file=" + newFilename);
                    return false;
                }

                thumbnail.flush();
            }

            resultImage.flush();

            return true;

        } catch (Exception e) {
            logger.error("File Crop Error. file=" + filePath, e);
            return false;
        }finally {
            if (originalImage != null){
                originalImage.flush();
            }
        }

    }

}
