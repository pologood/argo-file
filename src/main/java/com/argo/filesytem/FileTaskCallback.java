package com.argo.filesytem;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileTaskCallback {

    /**
     * 成功时触发
     * @param event
     */
    void onFileTaskSuccess(FileTaskResultEvent event);

    /**
     * 失败时触发
     * @param event
     */
    void onFileTaskFailure(FileTaskResultEvent event);
}
