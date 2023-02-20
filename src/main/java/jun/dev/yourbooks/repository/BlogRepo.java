package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface BlogRepo extends JpaRepository<Blog, Long> {
}
