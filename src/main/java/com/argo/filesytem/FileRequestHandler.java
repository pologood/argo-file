package com.argo.filesytem;

import com.argo.filesytem.exception.FileReadException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileRequestHandler {

    /**
     * 名称
     * @return
     */
    String getTag();

    /**
     * 保存
     * @param request
     * @return
     */
    FileResponse save(FileRequest request) throws IOException;

    /**
     * 读取
     * @param request
     * @return
     */
    int read(FileRequest request, OutputStream outputStream) throws FileReadException;

    /**
     *
     * @param request
     * @return
     */
    File read(FileRequest request) throws FileReadException;
    /**
     *
     * @param request
     * @return
     */
    boolean remove(FileRequest request);

    /**
     *
     * @param request
     * @return
     */
    String hash(FileRequest request) throws IOException;

}
