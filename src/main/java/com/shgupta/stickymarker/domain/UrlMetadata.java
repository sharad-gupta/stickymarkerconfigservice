package com.shgupta.stickymarker.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "url_metadata")
public class UrlMetadata {

	String url;
	String title;
	String description;
	String createdDate;
	String createdBy;

	public UrlMetadata() {
	}

	public UrlMetadata(String url, String title, String description, String createdBy) {
		this.url = url;
		this.title = title;
		this.description = description;
		this.createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.zzz").format(new Date());
		this.createdBy = createdBy;
	}

	@DynamoDBAttribute
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@DynamoDBAttribute
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@DynamoDBAttribute
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@DynamoDBRangeKey(attributeName = "createdDate")
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@DynamoDBHashKey(attributeName = "createdBy")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
