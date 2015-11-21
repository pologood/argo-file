package com.argo.filesystem;

import com.argo.filesystem.exception.FileReadException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileRequestHandler {

    /**
     * 名称
     * @return String
     */
    String getTag();

    /**
     * 保存
     * @param request 文件处理请求
     * @return FileResponse 处理结果
     * @throws IOException 抛出文件异常
     */
    FileResponse save(FileRequest request) throws IOException;

    /**
     * 读取
     * @param request 文件处理请求
     * @param outputStream 文件输出流
     * @return int 读取的字节数
     * @throws FileReadException 文件读取异常
     */
    int read(FileRequest request, OutputStream outputStream) throws FileReadException;

    /**
     *
     * @param request 文件处理请求
     * @return File 返回读取的文件
     * @throws FileReadException 文件读取异常
     */
    File read(FileRequest request) throws FileReadException;
    /**
     *
     * @param request 文件处理请求
     * @return boolean 处理结果
     */
    boolean remove(FileRequest request);

    /**
     *
     * @param request 文件处理请求
     * @return String 文件HASH值
     * @throws IOException 文件异常
     */
    String hash(FileRequest request) throws IOException;

}
