package com.argo.filesystem.handler;

import com.argo.filesystem.*;
import com.argo.filesystem.exception.FileReadException;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 本地文件系统处理
 * Created by yamingd on 9/10/15.
 */
public abstract class AbstractFileRequestLocalHandler implements FileRequestHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public FileResponse save(FileRequest request) throws IOException {

        String fileName;
        if (null != request.getFileId()) {
            if (request.isNameWithTs()) {
                fileName = String.format("%s-%s.%s", request.getFileId(), System.currentTimeMillis(), request.getFileExt());
            } else {
                fileName = String.format("%s.%s", request.getFileId(), request.getFileExt());
            }
        }else{
            fileName = String.format("%s.%s", System.currentTimeMillis(), request.getFileExt());
        }

        FilePathInfo filePathInfo = this.generateFolder(request);
        if (null == filePathInfo){
            throw new IOException("Can't create folder at " + FilesConfig.instance.getFolder());
        }

        File destFile = new File(filePathInfo.getFullPath(), fileName);
        if(destFile.exists()){
            destFile.delete();
        }

        Files.copy(request.getFile(), destFile);
        request.getFile().delete();

        return new FileResponse(destFile, filePathInfo.getFullPath(), filePathInfo.getPath() + fileName);

    }

    /**
     * 生成文件夹
     * @param request 文件请求
     * @return FilePathInfo 文件路径
     */
    protected abstract FilePathInfo generateFolder(FileRequest request);

    @Override
    public int read(FileRequest request, OutputStream outputStream) throws FileReadException {

        Preconditions.checkNotNull(request.getPath());
        File file = new File(FilesConfig.instance.getFolder(), request.getPath());
        if(!file.exists()){
            throw new FileReadException(file.getAbsolutePath());
        }

        return writeFile(file, outputStream);

    }

    @Override
    public File read(FileRequest request) throws FileReadException {

        Preconditions.checkNotNull(request.getPath());
        File file = new File(FilesConfig.instance.getFolder(), request.getPath());
        if(!file.exists()){
            throw new FileReadException(file.getAbsolutePath());
        }

        return file;
    }

    private int writeFile(File file, OutputStream outputStream) throws FileReadException {
        int total = 0;
        BufferedInputStream fin = null;
        byte[] buffer = new byte[1024];
        try {
            fin = new BufferedInputStream(new FileInputStream(file));
            int n = fin.read(buffer, 0, 1024);
            while (n >= 0) {
                total += n;
                outputStream.write(buffer);
                n = fin.read(buffer, 0, 1024);
            }
            outputStream.flush();
            return total;
        }catch (IOException ex){
            logger.error(ex.getMessage(), ex);
            throw new FileReadException(ex);
        }
        finally { // always close input stream
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    public boolean remove(FileRequest request) {

        Preconditions.checkNotNull(request.getPath());
        File file = new File(FilesConfig.instance.getFolder(), request.getPath());
        if(!file.exists()){
            return true;
        }

        return file.delete();

    }

    @Override
    public String hash(FileRequest request) throws IOException {

        Preconditions.checkNotNull(request.getPath());
        File file = new File(FilesConfig.instance.getFolder(), request.getPath());
        if(!file.exists()){
            return null;
        }

        HashCode hc = Files.hash(file, Hashing.sha1());
        return hc.toString();

    }

}
