package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    Page<News> findAllByOrderByCreatedTimeDesc(Pageable pageable);
}
