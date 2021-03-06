package es.codeurjc.db;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.db.model.Blog;
import es.codeurjc.db.model.Comment;

@RestController
@RequestMapping("/blogs")
public class BlogController {

	@Autowired
	private BlogRepository repository;

	@PostConstruct
	public void init() {

		Blog blog = new Blog("New", "My new product");
		blog.getComments().add(new Comment("Pepe", "Cool"));
		blog.getComments().add(new Comment("Juan", "Very Cool"));

		repository.save(blog);		
		
		Blog blog2 = new Blog("Old", "This product is very old");
		blog2.getComments().add(new Comment("Pepe", "OMG"));
		
		repository.save(blog2);
	}

	@GetMapping("/")
	public List<Blog> getBlogs(@RequestParam(required=false) String author) throws Exception {
		if(author == null){
			return repository.findAll();
		} else {
			return repository.findByCommentsAuthor(author);
		}
	}
}
