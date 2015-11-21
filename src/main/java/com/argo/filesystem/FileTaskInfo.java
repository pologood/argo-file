package com.argo.filesystem;

import com.google.common.base.Preconditions;
import org.msgpack.annotation.MessagePackMessage;

import java.util.Map;

/**
 * Created by yamingd on 9/10/15.
 */
@MessagePackMessage
public class FileTaskInfo {

    private Integer fileId;
    private String filePath;
    private String tag;

    private Map<String, String> params;

    public FileTaskInfo(String tag, String filePath) {
        Preconditions.checkNotNull(filePath);
        Preconditions.checkNotNull(tag);
        this.filePath = filePath;
        this.tag = tag;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileTaskInfo{");
        sb.append("fileId=").append(fileId);
        sb.append(", filePath='").append(filePath).append('\'');
        sb.append(", tag='").append(tag).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (!(obj instanceof FileTaskInfo)){
            return false;
        }
        FileTaskInfo info = (FileTaskInfo)obj;
        if (info.getFilePath().equalsIgnoreCase(this.getFilePath())
                && info.getTag().equalsIgnoreCase(this.getTag())){

            return true;

        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * filePath.hashCode() * tag.hashCode();
    }
}
