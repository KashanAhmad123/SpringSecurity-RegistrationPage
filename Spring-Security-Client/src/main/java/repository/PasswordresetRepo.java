package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Passwordreset;

@Repository
public interface PasswordresetRepo extends JpaRepository<Passwordreset,Long> {

}
