package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.Favourites;
import jun.dev.yourbooks.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepo extends JpaRepository<Favourites, Long> {
    Optional<Favourites> findByBook_Id(Long book_id);
    List<Favourites> findAllByUser(User user);
}
