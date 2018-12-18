package com.rebindtech.blog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rebindtech.repository.BlogRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository repository;

	@Override
	public List<Blog> getBlogs() {
		log.trace("Entering list()");
		return repository.readAll();
	}

	@Override
	public Optional<Blog> getBlog(String id) {
		log.trace("Entering read() with {}", id);
		return repository.read(id);
	}

	@Override
	public Optional<Blog> create(@Valid Blog blog) {

		log.trace("Entering create() with {}", blog);
		if (!StringUtils.isEmpty(blog.getId()) && repository.read(blog.getId()).isPresent()) {
			log.warn("Blog {} not found", blog.getId());
			return Optional.empty();
		}
		blog.setCreatedDateTime(LocalDateTime.now());
		repository.save(blog);
		return Optional.of(blog);
	}

	@Override
	public Optional<Blog> replace(Blog newBlogData) {
		log.trace("Entering replace() with {}", newBlogData);
		Optional<Blog> existingBlog = repository.read(newBlogData.getId());
		if (!existingBlog.isPresent()) {
			log.warn("Blog {} not found", newBlogData.getId());
			return Optional.empty();
		}
		Blog blog = existingBlog.get();
		blog.setTitle(newBlogData.getTitle());
		blog.setBody(newBlogData.getBody());
		repository.save(blog);
		return Optional.of(blog);
	}

	@Override
	public Optional<Blog> update(Blog newBlogData) {
		log.trace("Entering update() with {}", newBlogData);
		Optional<Blog> existingBlog = repository.read(newBlogData.getId());
		if (!existingBlog.isPresent()) {
			log.warn("Blog {} not found", newBlogData.getId());
			return Optional.empty();
		}
		Blog blog = existingBlog.get();
		if (!StringUtils.isEmpty(blog.getTitle()))
			blog.setTitle(blog.getTitle());
		if (!StringUtils.isEmpty(blog.getBody()))
			blog.setBody(blog.getBody());
		repository.save(blog);
		return Optional.of(blog);
	}

	@Override
	public boolean delete(String id) {
		log.trace("Entering delete() with {}", id);
		if (!repository.read(id).isPresent()) {
			log.warn("Blog {} not found", id);
			return false;
		}
		repository.delete(id);
		return true;
	}

}
