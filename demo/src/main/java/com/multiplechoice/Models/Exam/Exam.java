package com.multiplechoice.Models.Exam;

import com.multiplechoice.Models.Chapter.Chapter;
import com.multiplechoice.Models.Question.Question;

import java.util.ArrayList;
import java.util.Map;

public class Exam {
    private String idexam;
    private String nameexam;
    private String quantity;
    private String time;
    private String datecreate;
    private String idSemester;

    private ArrayList<Question> questionList;

    private String idSubExam;

    private Map<Chapter, ArrayList<Question>> examMap;
//    private ArrayList<Chapter> listChapters=new ArrayList<Chapter>();
    public Exam(String idexam, String nameexam, String quantity, String time, String datecreate, String idSemester) {
        super();
        this.idexam = idexam;
        this.nameexam = nameexam;
        this.quantity = quantity;
        this.time = time;
        this.datecreate = datecreate;
        this.idSemester = idSemester;
    }

    public Exam(String idexam) {
        super();
        this.idexam = idexam;
    }
    public Exam() {}

    public String getIdSubExam() {
        return idSubExam;
    }

    public void setIdSubExam(String idSubExam) {
        this.idSubExam = idSubExam;
    }

    public Map<Chapter, ArrayList<Question>> getExamMap() {
        return examMap;
    }

    public void setExamMap(Map<Chapter, ArrayList<Question>> examMap) {
        this.examMap = examMap;
    }

    public String getIdexam() {
        return idexam;
    }
    public void setIdexam(String idexam) {
        this.idexam = idexam;
    }
    public String getNameexam() {
        return nameexam;
    }
    public void setNameexam(String nameexam) {
        this.nameexam = nameexam;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getDatecreate() {
        return datecreate;
    }
    public void setDatecreate(String datecreate) {
        this.datecreate = datecreate;
    }
    public String getIdSemester() {
        return idSemester;
    }
    public void setIdSemester(String idSemester) {
        this.idSemester = idSemester;
    }
//    public ArrayList<Chapter> getListChapters() {
//        return listChapters;
//    }
//    public void setListChapters(ArrayList<Chapter> listChapters) {
//        this.listChapters = listChapters;
//    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }
}
