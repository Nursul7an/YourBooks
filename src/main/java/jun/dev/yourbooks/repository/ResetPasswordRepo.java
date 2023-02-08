package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordRepo extends JpaRepository<ResetPassword, Long> {
    Optional<ResetPassword> findResetPasswordByLink(String link);
}
