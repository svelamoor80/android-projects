package com.example.todoapp;


public class TodoDataModel {
    private String todoItemTitle;
    private String dueDate;
    private String priority;
    private boolean isCompleted;

    public TodoDataModel(String title, String dueDate, String priority, boolean isCompleted) {
        this.todoItemTitle = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;

    }

    public String getTodoItemTitle() {
        return todoItemTitle;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public boolean getIsCompleted(){
        return isCompleted;
    }

    public void setTodoItemTitle(String title){
        this.todoItemTitle = title;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setIsCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }
}
