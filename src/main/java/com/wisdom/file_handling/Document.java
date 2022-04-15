package com.wisdom.file_handling;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 512, nullable = false, unique = true)
    private String name;
    private long size;

    @Column(name = "upload_time")
    private Date uploadTime;
    private byte[] content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Document(long id, String name, long size, Date uploadTime, byte[] content) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.uploadTime = uploadTime;
        this.content = content;
    }

    public Document(long id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Document() {
    }
}
