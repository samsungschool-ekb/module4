package ru.samsung.tcpbasics.controller.model;

import java.io.Serializable;

public class Message implements Serializable {

    private String id;

    private String name;

    private String text;

    public Message() {}

    public Message(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
