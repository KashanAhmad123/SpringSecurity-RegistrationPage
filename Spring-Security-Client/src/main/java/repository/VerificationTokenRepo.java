package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.VerificationToken;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {

	static VerificationToken findByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
