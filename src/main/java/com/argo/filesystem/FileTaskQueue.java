package com.argo.filesystem;

import java.util.List;

/**
 * Created by yamingd on 9/10/15.
 */
public interface FileTaskQueue {

    /**
     *
     * @param tag 文件标签
     * @param limit 每次处理任务个数
     * @return List 待处理的文件任务
     */
    List<FileTaskInfo> lrange(String tag, int limit);

    /**
     * 删除
     * @param taskInfo 删除文件信息
     */
    void rem(FileTaskInfo taskInfo);

    /**
     *
     * @param taskInfo 加入队列的文件信息
     */
    void rpush(FileTaskInfo taskInfo);
}
