package com.okhttp;

public class DownloadInfo {
    //获取进度失败
    public static final long TOTAL_ERROR = -1;
    private String url;
    private long total;
    private long progress;
    private String fileName;

    public DownloadInfo(String url) {
        this.url=url;

    }
    public String getUrl() {
        return url;
    }

    public void setTotal(long total) {
        this.total=total;


    }

    public void setFileName(String fileName) {
        this.fileName=fileName;

    }

    public String getFileName() {
        return fileName;
    }

    public long getTotal() {
        return total;
    }

    public void setProgress(long progress) {
        this.progress=progress;

    }

    public long getProgress() {
        return progress;
    }

    public static long getTotalError() {
        return TOTAL_ERROR;
    }
}
