package com.engage;

public class AttractionDetailsItem {

	String travelerComment;
	String localUpside;
	String address;
	String upVotes;
	String downVotes;
	String totalVotes;
	String contactInfo;
	String attractionTypeDetails;

	public AttractionDetailsItem(String travelerComment, String localUpside,
			String address, String upVotes, String downVotes,
			String totalVotes, String contactInfo, String attractionTypeDetails) {
		super();
		this.travelerComment = travelerComment;
		this.localUpside = localUpside;
		this.address = address;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
		this.totalVotes = totalVotes;
		this.contactInfo = contactInfo;
		this.attractionTypeDetails = attractionTypeDetails;
	}

	public String getTravelerComment() {
		return travelerComment;
	}

	public void setTravelerComment(String travelerComment) {
		this.travelerComment = travelerComment;
	}

	public String getLocalUpside() {
		return localUpside;
	}

	public void setLocalUpside(String localUpside) {
		this.localUpside = localUpside;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(String upVotes) {
		this.upVotes = upVotes;
	}

	public String getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(String downVotes) {
		this.downVotes = downVotes;
	}

	public String getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(String totalVotes) {
		this.totalVotes = totalVotes;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getAttractionTypeDetails() {
		return attractionTypeDetails;
	}

	public void setAttractionTypeDetails(String attractionTypeDetails) {
		this.attractionTypeDetails = attractionTypeDetails;
	}

}
