package com.argo.filesystem.task;

import com.argo.filesystem.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by yamingd on 9/10/15.
 */
public abstract class AbstractFileTask implements FileTask {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected FileTaskCallback callback;
    protected FileTaskQueue queue;
    protected int limitSize = 10;

    protected volatile boolean stopped = false;

    @Override
    public void setCallback(FileTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    public void setTaskSource(FileTaskQueue queue) {
        this.queue = queue;
    }

    @Override
    public void close() throws IOException {
        stopped = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (!(obj instanceof FileTask)){
            return false;
        }
        FileTask task = (FileTask)obj;
        return task.getTag().equalsIgnoreCase(this.getTag());
    }

    @Override
    public int hashCode() {
        return 31 * this.getTag().hashCode();
    }

    @Override
    public void run() {

        try {
            Thread.sleep(FilesConfig.instance.getTaskwait());
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("start job: " + this.getTag());

        while (!this.stopped){

            List<FileTaskInfo> list = this.queue.lrange(this.getTag(), limitSize);

            for (int i = 0; i < list.size(); i++) {
                FileTaskInfo taskInfo = list.get(i);
                try {
                    boolean result = this.execute(taskInfo);
                    this.queue.rem(taskInfo);
                    if (!result){
                        this.callback.onFileTaskFailure(new FileTaskResultEvent(this.getTag(), taskInfo));
                    }else{
                        this.callback.onFileTaskSuccess(new FileTaskResultEvent(this.getTag(), taskInfo));
                    }
                }catch (Exception e){
                    logger.error(e.getMessage(), e);
                    this.queue.rem(taskInfo);
                    this.callback.onFileTaskFailure(new FileTaskResultEvent(this.getTag(), taskInfo));
                }
            }

            try {
                Thread.sleep(FilesConfig.instance.getTasksleep());
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                break;
            }

        }

    }
}
