package com.teamProject.board.model;

public class BoardDTO {
    private int num;
    private String id;
    private String name;
    private String subject;
    private String content;
    private String regist_day;
    private int hit;
    private String ip;
    private String filename;
    private long filesize;

    public int getNum() {
        return num;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getRegist_day() {
        return regist_day;
    }

    public int getHit() {
        return hit;
    }

    public String getIp() {
        return ip;
    }

    public String getFilename() {
        return filename;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRegist_day(String regist_day) {
        this.regist_day = regist_day;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }
}
