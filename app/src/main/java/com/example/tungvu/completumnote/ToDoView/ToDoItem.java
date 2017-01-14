package com.example.tungvu.completumnote.ToDoView;

/**
 * Created by Tung Vu Hoang Minh on 1/14/2017.
 */

public class ToDoItem {
    private String _toDoItemName;
    private int _id;

    public ToDoItem(){}

    // Constructor
    public ToDoItem(String toDoItemName){
        this._toDoItemName = toDoItemName;
    }

    // Get id of ToDoItem
    public int getId() {
        return _id;
    }

    // Set id of ToDoItem
    public void setId(int id) {
        this._id = id;
    }

    // Set name of ToDoItem
    public void setToDoItemName(String toDoItemName) {
        this._toDoItemName = toDoItemName;
    }

    // Get name of ToDoItem
    public String getToDoItemName(){
        return _toDoItemName;
    }

    @Override
    public String toString() {

        return "ToDoItem [id=" + _id + ", toDoItemName=" + _toDoItemName + "]";

    }
}
