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
    public List<Task> Tasks;

    public Bucket() {}

    public Bucket(String name, String description) {
        Name = name;
        Description = description;
        Achieved = false;
        DeadLine = new Date();
        Tasks = new ArrayList<Task>();
    }
}