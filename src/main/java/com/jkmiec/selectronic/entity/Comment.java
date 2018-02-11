package com.jkmiec.selectronic.entity;


import java.io.Serializable;

public class Comment implements Serializable {

    private String content;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                '}';
    }
}
