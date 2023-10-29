package com.multiplechoice.Models.Question;

import java.util.ArrayList;

import com.multiplechoice.Models.Chapter.Chapter;
import com.multiplechoice.Models.Exam.Exam;
import com.multiplechoice.Models.Option.Option;
import javafx.scene.control.ToggleGroup;

public class Question {
    private String Id;
    private String Title;
    private Option[] Options = new Option[4];
    private ArrayList<Option> optionList;
    private String Image;
    private ToggleGroup optionGroup;
    private Chapter chapter;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    private Exam exam;

    public Question(Exam exam, String id, String Title, Option[] options, String image) {
        this.exam = exam;
        this.Id = id;
        this.Title = Title;
        this.Options = options;
        this.Image = image;
    }

    public ArrayList<Option> getOptionList() {
    	return this.optionList;
    }
    
    public void setOptionList(ArrayList<Option> optionList) {
    	this.optionList = optionList;
    }
    
    public Question() {
        this.Title = "";
        this.Image = "";
        this.optionGroup = new ToggleGroup();
    }

    public ToggleGroup getOptionGroup() {
        return this.optionGroup;
    }

    public String getImage() {
        return this.Image;
    }
    public void setImage(String image) {
        this.Image = image;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public Option[] getOptions() {
        return Options;
    }

    public void setOptions(Option[] options) {
        Options = options;
    }
    public Option getOptionAt(int index) {
        return Options[index];
    }
    public void setOptionAt(int index, Option newValue) {
        Options[index] = newValue;
    }

    @Override
    public String toString() {
        return this.Title;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}

