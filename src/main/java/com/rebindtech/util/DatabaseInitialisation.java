package com.rebindtech.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.rebindtech.blog.Blog;

@Component
public class DatabaseInitialisation implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private DynamoDBMapper dbMapper;

	@Autowired
	private AmazonDynamoDB dynamoDB;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		CreateTableRequest request = dbMapper.generateCreateTableRequest(Blog.class)
				.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		try {
			DescribeTableResult result = dynamoDB.describeTable(request.getTableName());
		} catch (ResourceNotFoundException expectedException) {
			CreateTableResult result = dynamoDB.createTable(request);
		}
	}

}