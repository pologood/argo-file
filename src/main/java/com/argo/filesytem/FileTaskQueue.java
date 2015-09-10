package com.argo.filesytem;

import java.util.List;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileTaskQueue {

    /**
     *
     * @param tag
     * @param limit
     * @return
     */
    List<FileTaskInfo> lrange(String tag, int limit);

    /**
     * 删除
     * @param taskInfo
     */
    void rem(FileTaskInfo taskInfo);

    /**
     *
     * @param taskInfo
     */
    void rpush(FileTaskInfo taskInfo);
}
