package com.argo.filesytem;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileTaskCallback {

    /**
     * 成功时触发
     * @param event 文件处理结果事件
     */
    void onFileTaskSuccess(FileTaskResultEvent event);

    /**
     * 失败时触发
     * @param event 文件处理结果事件
     */
    void onFileTaskFailure(FileTaskResultEvent event);
}
