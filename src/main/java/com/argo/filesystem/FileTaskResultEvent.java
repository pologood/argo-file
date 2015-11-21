package com.argo.filesystem;

/**
 *
 * 传递处理结果
 * Created by yamingd on 9/10/15.
 */
public class FileTaskResultEvent {

    private String taskName;
    private FileTaskInfo taskInfo;

    public FileTaskResultEvent(String taskName, FileTaskInfo taskInfo) {
        this.taskName = taskName;
        this.taskInfo = taskInfo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public FileTaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(FileTaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
