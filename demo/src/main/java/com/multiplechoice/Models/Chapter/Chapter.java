package com.multiplechoice.Models.Chapter;

public class Chapter {
	private String idChapter;
	private String nameChapter;
	private String idSemester;
	private int quantityQuestion;
	public Chapter(String idChapter, String nameChapter, String idSemester) {
		super();
		this.idChapter = idChapter;
		this.nameChapter = nameChapter;
		this.idSemester = idSemester;
	}
	public Chapter() {}
	
	public String getIdChapter() {
		return idChapter;
	}
	public void setIdChapter(String idChapter) {
		this.idChapter = idChapter;
	}
	public String getNameChapter() {
		return nameChapter;
	}
	public void setNameChapter(String nameChapter) {
		this.nameChapter = nameChapter;
	}
	public String getIdSemester() {
		return idSemester;
	}
	public void setIdSemester(String idSemester) {
		this.idSemester = idSemester;
	}
	public int getQuantityQuestion() {
		return quantityQuestion;
	}
	public void setQuantityQuestion(int quantityQuestion) {
		this.quantityQuestion = quantityQuestion;
	}
	
	public void printf() {
		System.out.println(this.idChapter +"\t"+this.quantityQuestion);
	}
	
}
