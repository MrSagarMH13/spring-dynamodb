package com.rebindtech.blog;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController {

	@Autowired
	private BlogService service;

	@GetMapping
	public ResponseEntity<List<Blog>> getBlogs() {

		log.trace("Entering list()");
		List<Blog> blogs = service.getBlogs();
		if (blogs.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(blogs, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Blog> getBlog(@PathVariable String id) {

		log.trace("Entering read() with {}", id);
		return service.getBlog(id).map(blog -> new ResponseEntity<>(blog, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<Blog> create(@RequestBody @Valid Blog blog) {

		log.trace("Entering create() with {}", blog);
		return service.create(blog).map(newBlogData -> new ResponseEntity<>(newBlogData, HttpStatus.CREATED))
				.orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Blog> put(@PathVariable String id, @RequestBody Blog blog) {

		log.trace("Entering put() with {}, {}", id, blog);
		return service.replace(blog.withId(id)).map(newBlogData -> new ResponseEntity<>(newBlogData, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Blog> patch(@PathVariable String id, @RequestBody Blog blog) {
		log.trace("Entering patch() with {}, {}", id, blog);
		return service.update(blog.withId(id)).map(newBlogData -> new ResponseEntity<>(newBlogData, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {

		log.trace("Entering delete() with {}", id);
		return service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}