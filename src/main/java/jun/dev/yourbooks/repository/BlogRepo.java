package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.Blog;
import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface BlogRepo extends JpaRepository<Blog, Long> {
    List<Blog> findAllByBook(Book book);
    List<Blog> findBlogsByPublisher(User user);
}
