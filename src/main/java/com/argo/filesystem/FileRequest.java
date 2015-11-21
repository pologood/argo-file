package com.argo.filesystem;

import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yamingd on 9/10/15.
 */
public class FileRequest {

    private MultipartFile file;
    private Integer fileId;
    private String fileCategory;
    private String fileExt;
    private boolean nameWithTs = true;

    private String path;

    public FileRequest(MultipartFile file, Integer fileId, String fileCategory, String fileExt) {
        this.file = file;
        this.fileId = fileId;
        this.fileCategory = fileCategory;
        this.fileExt = fileExt;
    }

    public FileRequest(String path) {
        this.path = path;
    }

    public FileRequest(MultipartFile file, Integer fileId, String fileCategory) {
        this.file = file;
        this.fileId = fileId;
        this.fileCategory = fileCategory;
        this.fileExt = Files.getFileExtension(file.getName());
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getFileExt() {
        if (fileExt == null){
            fileExt = Files.getFileExtension(file.getName());
        }
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public boolean isNameWithTs() {
        return nameWithTs;
    }

    public void setNameWithTs(boolean nameWithTs) {
        this.nameWithTs = nameWithTs;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileRequest{");
        sb.append("fileId=").append(fileId);
        sb.append(", fileCategory='").append(fileCategory).append('\'');
        sb.append(", fileExt='").append(fileExt).append('\'');
        sb.append(", nameWithTs=").append(nameWithTs);
        sb.append(", file=").append(file);
        sb.append('}');
        return sb.toString();
    }
}
