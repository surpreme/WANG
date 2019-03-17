package com.liziyang.dall.getMusic;

import java.io.Serializable;

public class MusicResult implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 需要实现序列化接口
     */
    //歌名和演唱者
    private String music_name_String;
    private String music_author_String;
    //路径
    private String music_path_String;
    //音乐时长 为long类型
    private long duration;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMusic_name_String() {
        return music_name_String;
    }

    public void setMusic_name_String(String music_name_String) {
        this.music_name_String = music_name_String;
    }

    public String getMusic_author_String() {
        return music_author_String;
    }

    public void setMusic_author_String(String music_author_String) {
        this.music_author_String = music_author_String;
    }

    public String getMusic_path_String() {
        return music_path_String;
    }

    public void setMusic_path_String(String music_path_String) {
        this.music_path_String = music_path_String;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }




}
