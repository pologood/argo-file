package com.argo.filesystem;

import java.io.Closeable;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileTask extends Runnable, Closeable {

    /**
     * 设置任务处理结果回调
     * @param callback 处理回调
     */
    void setCallback(FileTaskCallback callback);

    /**
     * 设置任务队列
     * @param queue 待处理的队列
     */
    void setTaskSource(FileTaskQueue queue);

    /**
     * task的名称
     * @return String
     */
    String getTag();
    /**
     *
     * @param fileTaskInfo 待处理的文件信息
     * @return boolean
     * @throws Exception 抛出异常
     */
    boolean execute(FileTaskInfo fileTaskInfo) throws Exception;

}
