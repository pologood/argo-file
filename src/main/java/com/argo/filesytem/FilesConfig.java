package com.argo.filesytem;

import com.argo.yaml.YamlTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Created by yamingd on 9/10/15.
 */
public class FilesConfig {

    private static final String confName = "files.yaml";

    public static FilesConfig instance = null;

    /**
     * 加载配置信息
     * @throws IOException 文件读取异常
     */
    public synchronized static void load() throws IOException {
        if (instance != null){
            return;
        }
        FilesConfig.instance = YamlTemplate.load(FilesConfig.class, confName);
    }

    /**
     * 存储根路径
     */
    private String folder;
    /**
     * 配置图片缩略图尺寸
     */
    private List<Integer> thumbs;
    /**
     * 配置路径
     */
    private String ffmpeg;
    /**
     * 配置需要启动的task
     */
    private List<String> tasks;

    /**
     * 执行间隔
     */
    private Integer tasksleep;

    /**
     * 启动延迟
     */
    private Integer taskwait;

    /**
     * 失败尝试次数
     */
    private Integer tasktry;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<Integer> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<Integer> thumbs) {
        this.thumbs = thumbs;
    }

    public String getFfmpeg() {
        return ffmpeg;
    }

    public void setFfmpeg(String ffmpeg) {
        this.ffmpeg = ffmpeg;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public Integer getTasksleep() {
        return tasksleep;
    }

    public void setTasksleep(Integer tasksleep) {
        this.tasksleep = tasksleep;
    }

    public Integer getTaskwait() {
        return taskwait;
    }

    public void setTaskwait(Integer taskwait) {
        this.taskwait = taskwait;
    }

    public Integer getTasktry() {
        return tasktry;
    }

    public void setTasktry(Integer tasktry) {
        this.tasktry = tasktry;
    }
}
