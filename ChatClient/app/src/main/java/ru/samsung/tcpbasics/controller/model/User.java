package ru.samsung.tcpbasics.controller.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by USER on 19.02.2019.
 */
public class User implements Serializable {

    private String id;

    private String name;

    private List<Message> messageList;

    public User() {
    }

    public User(String id, String name, List<Message> messageList) {
        this.id = id;
        this.name = name;
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", messageList=" + messageList +
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

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
