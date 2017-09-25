package com.shgupta.stickymarker.service.urlnote;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.shgupta.stickymarker.domain.UrlInput;
import com.shgupta.stickymarker.domain.UrlMetadata;
import com.shgupta.stickymarker.domain.error.DuplicateUrlExist;

@RestController
@RequestMapping("metadata")
public class UrlNoteResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlNoteResource.class);

	@Autowired
	DynamoDBMapper dynamoDB;
	

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "pong ==> succeeded";
	}

	@RequestMapping(value = "/url/{username}/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createUrlNote(@RequestBody UrlInput url, @PathVariable("username") final String username)
			throws IOException, DuplicateUrlExist {
		LOGGER.info("createUrlNote {}", url);
		Document document = Jsoup.connect(url.getUrl()).get();
		String description = "-";
		try {
			description = document.select("meta[name=description]").first().attr("content");
		} catch (Exception e) {
		}

		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#createdBy", "createdBy");
		expressionAttributesNames.put("#url", "url");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":createdByValue", new AttributeValue().withS(username));
		expressionAttributeValues.put(":urlByValue", new AttributeValue().withS(url.getUrl()));

		DynamoDBQueryExpression<UrlMetadata> expression = new DynamoDBQueryExpression<UrlMetadata>()
				.withKeyConditionExpression("#createdBy = :createdByValue").withFilterExpression("#url = :urlByValue")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues);

		List<UrlMetadata> ret = dynamoDB.query(UrlMetadata.class, expression);
		if (ret != null && ret.size() > 0) {
			throw new DuplicateUrlExist("Url " + url.getUrl() + " already exists");
		}

		UrlMetadata metadata = new UrlMetadata(url.getUrl(), document.title(), description, username);
		dynamoDB.save(metadata);
		LOGGER.info("Document Save Successfully => {}", metadata.getUrl());
	}

	@RequestMapping(value = "/url/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<UrlMetadata> findUrlNoteByName(@PathVariable("username") final String username) throws IOException {
		LOGGER.info("findUrlNoteByName {}", username);
		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#createdBy", "createdBy");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":createdByValue", new AttributeValue().withS(username));

		DynamoDBQueryExpression<UrlMetadata> expression = new DynamoDBQueryExpression<UrlMetadata>()
				.withKeyConditionExpression("#createdBy = :createdByValue")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues);

		List<UrlMetadata> ret = dynamoDB.query(UrlMetadata.class, expression);
		LOGGER.info("findUrlNoteByName size returned => {}", ret.size());
		return ret;

	}
}
