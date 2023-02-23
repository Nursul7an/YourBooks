package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.Blog;
import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface BlogRepo extends JpaRepository<Blog, Long> {
    List<Blog> findAllByBook(Book book);
    List<Blog> findBlogsByPublisher(User user);
    List<Blog> findAllByTag(Tag tag);
}
