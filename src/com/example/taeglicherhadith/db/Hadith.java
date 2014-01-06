package com.example.taeglicherhadith.db;

public class Hadith {

	private long id;
	private String title; 
	private String hadith;
	
	public Hadith(String title, String hadith) {
		this.title = title;
		this.hadith = hadith;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHadith() {
		return hadith;
	}

	public void setHadith(String hadith) {
		this.hadith = hadith;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
