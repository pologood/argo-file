package com.argo.filesystem;

import java.io.File;

/**
 * Created by yamingd on 9/10/15.
 */
public class FileResponse {

    private File file;
    private String fullPath;
    private String path;
    private String hashCode;

    public FileResponse(File file, String fullPath, String path) {
        this.file = file;
        this.fullPath = fullPath;
        this.path = path;
    }

    public FileResponse(File file) {
        this.file = file;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
}
