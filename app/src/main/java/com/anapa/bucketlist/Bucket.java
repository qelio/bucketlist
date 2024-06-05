package com.anapa.bucketlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bucket implements Serializable {
    public String Name;
    public String Description;
    public Boolean Achieved;
    public Date DeadLine;
    public Category Category;
    public ArrayList<Task> Tasks;

    public Bucket() {}

    public Bucket(String name, String description) {
        Name = name;
        Description = description;
        Achieved = false;
        DeadLine = new Date();
        Tasks = new ArrayList<Task>();
        Category = new Category();
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
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

    public Date getDeadLine() {
        return DeadLine;
    }
    public void setDeadLine(Date deadLine) {
        DeadLine = deadLine;
    }

    public ArrayList<Task> getTasks() {
        return Tasks;
    }
    public void setTasks(ArrayList<Task> tasks) {
        Tasks = tasks;
    }

    public Category getCategory() {
        return Category;
    }
    public void setCategory(Category category) {
        Category = category;
    }
}