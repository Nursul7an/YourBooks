package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivationTokenRepo extends JpaRepository<ActivationToken, Long> {

    Optional<ActivationToken> findActivationTokenByLink(String link);
}
