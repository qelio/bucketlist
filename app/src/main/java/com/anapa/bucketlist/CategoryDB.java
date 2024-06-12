package com.anapa.bucketlist;

import java.io.Serializable;

public class CategoryDB implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;

    public CategoryDB(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
