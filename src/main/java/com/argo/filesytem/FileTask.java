package com.argo.filesytem;

import java.io.Closeable;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileTask extends Runnable, Closeable {

    /**
     * 设置任务处理结果回调
     * @param callback
     */
    void setCallback(FileTaskCallback callback);

    /**
     * 设置任务队列
     * @param queue
     */
    void setTaskSource(FileTaskQueue queue);

    /**
     * task的名称
     * @return
     */
    String getTag();
    /**
     *
     * @param fileTaskInfo
     */
    boolean execute(FileTaskInfo fileTaskInfo) throws Exception;

}
