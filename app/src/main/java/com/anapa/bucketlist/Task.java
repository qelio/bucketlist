package com.anapa.bucketlist;

import java.io.Serializable;

public class Task implements Serializable {
    public String Description;
    public Boolean Achieved;

    public Task() {}

    public Task(String description, Boolean achieved) {
        Description = description;
        Achieved = achieved;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getAchieved() {
        return Achieved;
    }
    public void setAchieved(Boolean achieved) {
        Achieved = achieved;
    }
}
