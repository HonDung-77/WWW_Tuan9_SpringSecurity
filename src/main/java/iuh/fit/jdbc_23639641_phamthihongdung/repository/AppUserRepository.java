package iuh.fit.jdbc_23639641_phamthihongdung.repository;

import iuh.fit.jdbc_23639641_phamthihongdung.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findByUsername(String username);
}