package com.rebindtech.blog;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

public interface BlogService {

	List<Blog> getBlogs();

	Optional<Blog> getBlog(String id);

	Optional<Blog> create(@Valid Blog blog);

	Optional<Blog> replace(Blog withId);

	Optional<Blog> update(Blog withId);

	boolean delete(String id);

}
