package com.rebindtech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.rebindtech.blog.Blog;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;

@Component
public class BlogRepository {

	@Autowired
	private DynamoDBMapper dbMapper;

	public List<Blog> readAll() {
		PaginatedList<Blog> results = dbMapper.scan(Blog.class, new DynamoDBScanExpression());
		results.loadAllResults();
		return results;
	}

	public Optional<Blog> read(String id) {
		return Optional.ofNullable(dbMapper.load(Blog.class, id));
	}

	public void save(Blog blog) {
		dbMapper.save(blog);
	}

	public void delete(String id) {
		dbMapper.delete(new Blog().withId(id), new DynamoDBMapperConfig(SaveBehavior.CLOBBER));
	}
}