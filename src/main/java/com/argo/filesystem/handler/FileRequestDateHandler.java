package com.argo.filesystem.handler;

import com.argo.filesystem.FilePathInfo;
import com.argo.filesystem.FileRequest;
import com.argo.filesystem.FilesConfig;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.util.Date;

/**
 * Created by yamingd on 9/10/15.
 */
public class FileRequestDateHandler extends AbstractFileRequestLocalHandler {

    public static final String YYYY_MM_DD = "yyyy/mm/dd";

    public static final String TAG = "date";

    @Override
    public String getTag() {
        return TAG;
    }


    @Override
    protected FilePathInfo generateFolder(FileRequest request){

        String fileName = DateFormatUtils.format(new Date(), YYYY_MM_DD);
        String rootFolder = FilesConfig.instance.getFolder();
        String path = String.format("/%s/%s", request.getFileCategory(), fileName);
        File folder = new File(rootFolder, path);
        if(!folder.exists()){
            boolean ret = folder.mkdirs();
            if (!ret){
                return null;
            }
        }

        FilePathInfo filePathInfo = new FilePathInfo(folder.getAbsolutePath(), path, FilesConfig.instance.getFolder());

        return filePathInfo;
    }


}
