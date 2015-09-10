package com.argo.filesytem;

import com.argo.filesytem.task.ImageCropTask;
import com.argo.filesytem.task.ImageThumbTask;
import com.argo.filesytem.task.ToMP3Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yamingd on 9/10/15.
 */
public class FileTaskFactory {

    private List<FileTask> taskList = new ArrayList<FileTask>();

    public FileTaskFactory(){

        try {
            FilesConfig.load();
        } catch (IOException e) {
            throw new RuntimeException("Load filesConfig Error.");
        }

        this.addTask(new ImageCropTask());
        this.addTask(new ImageThumbTask());
        this.addTask(new ToMP3Task());

    }

    public void addTask(FileTask task){
        taskList.add(task);
    }

    /**
     * 启动task
     */
    public void start(){

        List<String> cfgTasks = FilesConfig.instance.getTasks();
        if (cfgTasks ==null ||
                cfgTasks.size() == 0){

            return;
        }

        for (FileTask task : taskList){
            if (cfgTasks.contains(task.getTag())){
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.setName("FileTask-" + task.getTag());
                thread.start();
            }
        }

    }

    /**
     * set
     * @param taskTag
     * @param callback
     */
    public FileTaskFactory setCallback(String taskTag, FileTaskCallback callback){

        for (FileTask task : taskList){
            if (task.getTag().equals(taskTag)){
                task.setCallback(callback);
                break;
            }
        }

        return this;
    }

    public FileTaskFactory setTaskSource(String taskTag, FileTaskQueue queue){

        for (FileTask task : taskList){
            if (task.getTag().equals(taskTag)){
                task.setTaskSource(queue);
                break;
            }
        }

        return this;
    }

    /**
     * 停止所有task
     */
    public void stop(){

        for (FileTask task : taskList){
            try {
                task.close();
            } catch (IOException e) {

            }
        }

    }

}