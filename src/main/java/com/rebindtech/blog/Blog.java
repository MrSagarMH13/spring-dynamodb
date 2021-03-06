package com.rebindtech.blog;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Blog")
public class Blog {

	private String id;
	private String title;
	private String body;
	private LocalDateTime createdDateTime;

	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute
	@NotNull(message = "Title must not be empty")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@DynamoDBAttribute
	@NotNull(message = "Body must not be empty")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@DynamoDBAttribute
	@DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Blog withId(String id) {
		setId(id);
		return this;
	}

	static public class LocalDateTimeConverter implements DynamoDBMarshaller<LocalDateTime> {

		@Override
		public String marshall(LocalDateTime time) {
			return time.toString();
		}

		@Override
		public LocalDateTime unmarshall(Class<LocalDateTime> dimensionType, String stringValue) {
			return LocalDateTime.parse(stringValue);
		}
	}
}
