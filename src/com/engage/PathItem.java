package com.asa.engage;

public class PathItem {

	String createdAt;
	String userId;
	String attractions;
	String comment;
	String share;

	public PathItem(String createdAt, String userId, String attractions,
			String comment, String share) {
		super();
		this.createdAt = createdAt;
		this.userId = userId;
		this.attractions = attractions;
		this.comment = comment;
		this.share = share;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAttractions() {
		return attractions;
	}

	public void setAttractions(String attractions) {
		this.attractions = attractions;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

}
