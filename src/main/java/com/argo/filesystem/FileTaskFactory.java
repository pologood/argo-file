package com.argo.filesystem;

import com.argo.filesystem.task.ImageCropTask;
import com.argo.filesystem.task.ImageThumbTask;
import com.argo.filesystem.task.ToMP3Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yamingd on 9/10/15.
 */
public class FileTaskFactory implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<FileTask> taskList = new ArrayList<FileTask>();

    public FileTaskFactory(){

        try {
            FilesConfig.load();
        } catch (IOException e) {
            logger.error("Load filesConfig Error. Please check your " + FilesConfig.confName);
            return;
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
        if (FilesConfig.instance == null){
            logger.error("Load filesConfig Error. Please check your " + FilesConfig.confName);
            return;
        }

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
     * @param taskTag 任务标签
     * @param callback 文件处理回调
     * @return FileTaskFactory 返回本实例
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

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }
}
