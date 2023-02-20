package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Style;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    Optional<Book> findBookById(Long id);
    List<Book> findAllByOrderByCreatedTimeDesc();
    @Query("SELECT b FROM Book b WHERE b.name LIKE %:value% OR b.author LIKE %:value% ORDER BY b.name")
    List<Book> findByNameAndAuthor(String value);
    List<Book> findAllByStyle(Style style);
    List<Book> findAllByPublisher(User publisher);
}
