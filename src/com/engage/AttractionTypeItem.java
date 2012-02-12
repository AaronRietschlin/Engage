package com.engage;

public class AttractionTypeItem {
	
	String attractionName;
	String attractionType;

	public AttractionTypeItem(String attractionName, String attractionType) {
		super();
		this.attractionName = attractionName;
		this.attractionType = attractionType;
	}

	public String getAttractionName() {
		return attractionName;
	}

	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public String getAttractionType() {
		return attractionType;
	}

	public void setAttractionType(String attractionType) {
		this.attractionType = attractionType;
	}

}
