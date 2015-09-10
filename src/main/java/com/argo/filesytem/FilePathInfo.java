package com.argo.filesytem;

/**
 * Created by yamingd on 9/10/15.
 */
public class FilePathInfo {

    private String fullPath;
    private String path;
    private String root;

    public FilePathInfo(String fullPath, String path, String root) {
        this.fullPath = fullPath;
        this.path = path;
        this.root = root;
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

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
