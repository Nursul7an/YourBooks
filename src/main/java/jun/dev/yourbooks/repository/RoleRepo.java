package jun.dev.yourbooks.repository;

import jun.dev.yourbooks.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    Optional<Role> findRoleByName(String name);
}
