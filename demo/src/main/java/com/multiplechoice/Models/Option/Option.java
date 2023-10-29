package com.multiplechoice.Models.Option;

public class Option {
    private String option_id;
    private String title;
    private boolean isCorrect;


    public String getId() {
        return this.option_id;
    }
    public void setId(String id) {
        this.option_id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean getIsCorrect() {
        return this.isCorrect;
    }
}
