package com.asa.engage;

public class AttractionItem {

	String attractionName;
	String detailId;
	String lat;
	String lng;
	String createdAt;
	String Description ;
	String tags;
	String photoId;
	String typeId;
	
	public AttractionItem(String attractionName, String detailId, String lat,
			String lng, String createdAt, String description, String tags,
			String photoId, String typeId) {
		super();
		this.attractionName = attractionName;
		this.detailId = detailId;
		this.lat = lat;
		this.lng = lng;
		this.createdAt = createdAt;
		Description = description;
		this.tags = tags;
		this.photoId = photoId;
		this.typeId = typeId;
	}
	
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
