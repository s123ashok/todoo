package com.example.sashok.todoo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by asmurthy on 11/13/15.
 */
@Table(name = "Items")
public class Item extends Model {
    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    // This is a regular field
    @Column(name = "Name")
    public String name;
    @Column(name = "Priority")
    public String priority;


    static long id = 1;
    // Make sure to have a default constructor for every ActiveAndroid model
    public Item(){
        super();
    }

    public Item(long remoteId, String name, String priority){
        super();
        addItem(remoteId, name, priority);
    }

    public Item(long remoteId, String name){
        super();
        addItem(remoteId, name, "Low");
    }

    public Item(String name){
        super();
        addItem(id, name, "Low");
        id++;
    }

    private void addItem(long remoteId, String name, String priority) {
        this.remoteId = remoteId;
        this.name = name;
        this.priority = priority;
        this.save();
    }

}