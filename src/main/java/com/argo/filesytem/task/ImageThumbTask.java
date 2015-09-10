package com.argo.filesytem.task;

import com.argo.filesytem.FileTaskInfo;
import com.argo.filesytem.FilesConfig;
import com.google.common.io.Files;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by yamingd on 9/10/15.
 */
public class ImageThumbTask extends AbstractFileTask {

    public static final String tag = "thumb";

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public boolean execute(FileTaskInfo fileTaskInfo) throws Exception {

        String filePath = fileTaskInfo.getFilePath();
        BufferedImage originalImage = null;
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            originalImage = ImageIO.read(is);
        } catch (FileNotFoundException e) {
            logger.error("File not found. file=" + filePath);
            throw e;
        } catch (IOException e) {
            logger.error("File read Error. file=" + filePath, e);
            throw e;
        }

        String ext = Files.getFileExtension(filePath);
        int count = 0;
        for (Integer width : FilesConfig.instance.getThumbs()){

            String newFilename = String.format("%s.%s.%s", filePath, width, ext);

            try {

                BufferedImage thumbnail = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
                        width, Scalr.OP_ANTIALIAS);

                if (!ImageIO.write(thumbnail, "JPEG", new File(newFilename))) {
                    logger.error("File write failed. file=" + newFilename);
                    return false;
                }else{
                    count ++ ;
                }

                thumbnail.flush();

            } catch (Exception e) {
                logger.error("File write Error. file=" + newFilename, e);
            }
        }

        if (originalImage != null) {
            originalImage.flush();
        }

        return count > 0;

    }

}
