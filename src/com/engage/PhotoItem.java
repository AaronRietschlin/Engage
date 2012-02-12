package com.asa.engage;

public class PhotoItem {

	String createdAt;
	String url;
	String filePath;
	String attractionId;
	String userId;

	public PhotoItem(String createdAt, String url, String filePath,
			String attractionId, String userId) {
		super();
		this.createdAt = createdAt;
		this.url = url;
		this.filePath = filePath;
		this.attractionId = attractionId;
		this.userId = userId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(String attractionId) {
		this.attractionId = attractionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
