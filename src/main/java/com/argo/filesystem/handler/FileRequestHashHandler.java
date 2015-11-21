package com.argo.filesystem.handler;

import com.argo.filesystem.FilePathInfo;
import com.argo.filesystem.FileRequest;
import com.argo.filesystem.FilesConfig;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.io.File;

/**
 * Created by yamingd on 9/10/15.
 */
public class FileRequestHashHandler extends AbstractFileRequestLocalHandler {

    public static final String TAG = "hash";

    @Override
    public String getTag() {
        return TAG;
    }


    @Override
    protected FilePathInfo generateFolder(FileRequest request){

        String fileName = String.format("file-%s-%s", request.getFileCategory(), request.getFileId()).toLowerCase();

        String hex = md5(fileName);
        String rootFolder = FilesConfig.instance.getFolder();
        String path = String.format("/%s/%s/%s/%s/%s/",
                                    request.getFileCategory(),
                                    hex.substring(0, 3),
                                    hex.substring(3, 6),
                                    hex.substring(6, 9),
                                    hex.substring(9, 12));

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

    public static String md5(String value){
        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher().putString(value, Charsets.UTF_8).hash();
        return hc.toString();
    }

}
