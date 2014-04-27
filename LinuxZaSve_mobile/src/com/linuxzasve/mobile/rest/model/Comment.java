package com.linuxzasve.mobile.rest.model;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private int id;
    private String name;
    private String url;
    private String date;
    private String content;
    private String parent;
    private List<Comment> children;
    private int depth;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(final String parent) {
        this.parent = parent;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(final List<Comment> children) {
        this.children = children;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(final int depth) {
        this.depth = depth;
    }

    public Comment() {
    }

    public void addChild(final Comment comment) {
        if (children == null) {
            children = new ArrayList<Comment>();
        }

        children.add(comment);
    }


}
