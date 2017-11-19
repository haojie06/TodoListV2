package com.example.haojie06.todolist;

import java.io.Serializable;

/**
 * Created by haojie06 on 2017/11/18.
 */

public class Things implements Serializable {
    private String title,content,time;
    private String color;
    public Things(String title, String content, String time,String color) {// int color
        this.title = title;
        this.content = content;
        this.time = time;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getColor() {
        return color;
    }
}
