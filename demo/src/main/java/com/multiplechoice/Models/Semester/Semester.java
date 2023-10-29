package com.multiplechoice.Models.Semester;

public class Semester {
    private int semester_id;
    private String name;

    public void setId(int id) {
        this.semester_id = id;
    }

    public int getId() {
        return this.semester_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

