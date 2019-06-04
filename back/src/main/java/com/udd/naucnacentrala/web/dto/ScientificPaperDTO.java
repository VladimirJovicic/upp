package com.udd.naucnacentrala.web.dto;

public class ScientificPaperDTO {
	private String title;
	private String abstractDesctiption;
	private String scientificArea;
	private String keyWords;
	private String coAuthors;
	private String pdfText;
	
	public ScientificPaperDTO()
	{}
	
	public ScientificPaperDTO(String title, String abstractDesctiption, String scientificArea, String keyWords,
			String coAuthors, String pdfText) {
		super();
		this.title = title;
		this.abstractDesctiption = abstractDesctiption;
		this.scientificArea = scientificArea;
		this.keyWords = keyWords;
		this.coAuthors = coAuthors;
		this.pdfText = pdfText;
	}

	@Override
	public String toString() {
		return "ScientificPaperDTO [title=" + title + ", abstractDesctiption=" + abstractDesctiption
				+ ", scientificArea=" + scientificArea + ", keyWords=" + keyWords + ", coAuthors=" + coAuthors
				+ ", pdfText=" + pdfText + "]";
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstractDesctiption() {
		return abstractDesctiption;
	}

	public void setAbstractDesctiption(String abstractDesctiption) {
		this.abstractDesctiption = abstractDesctiption;
	}

	public String getScientificArea() {
		return scientificArea;
	}

	public void setScientificArea(String scientificArea) {
		this.scientificArea = scientificArea;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getCoAuthors() {
		return coAuthors;
	}

	public void setCoAuthors(String coAuthors) {
		this.coAuthors = coAuthors;
	}


	public String getPdfText() {
		return pdfText;
	}


	public void setPdfText(String pdfText) {
		this.pdfText = pdfText;
	}

}
