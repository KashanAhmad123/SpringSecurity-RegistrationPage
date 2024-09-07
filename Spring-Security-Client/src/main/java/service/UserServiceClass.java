package service;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import entity.Passwordreset;
import entity.User;
import entity.VerificationToken;
import model.UserModel;
import repository.PasswordresetRepo;
import repository.UserRepository;
import repository.VerificationTokenRepo;

@Service
public class UserServiceClass implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordresetRepo passwordrstRepo;
	
	 @Autowired
	    private VerificationTokenRepo verificationTokenRepo;
	
	@Override
	public User registerUser(UserModel userModel) {
		User user= new User();
		user.setEmail(userModel.getEmail());
		user.setFirstName(userModel.getFirstNmae());
		user.setLastNmae(userModel.getLastName());
		user.setRole("USER");
		user.setPassword(userModel.getPassword());
		userRepository.save(user);
		return user;
		
	}

	@Override
	public void saveVerificationTokenForUser(String token, User user) {
		VerificationToken verificationToken
        = new VerificationToken(user, token);

verificationTokenRepo.save(verificationToken);
		
	}

	@Override
	public String validateVerificationToken(String token) {
	  VerificationToken verificationToken= VerificationTokenRepo.findByToken(token);
		if(verificationToken==null) {
			return "invalid";
		}
		
	  User user = verificationToken.getUser();
	  Calendar cal= Calendar.getInstance();
	 if((verificationToken.getExpirationTime().getTime-cal.getTime().getTime()) <=0) {
		 return "Expired";
	 }
	  user.setEnabled(true);
	  userRepository.save(user);
	  return "Valid";
	}

	@Override
	public VerificationToken generateNewVtoken(String oldToken) {
		VerificationToken verificationToken= VerificationTokenRepo.findByToken(oldToken);
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationTokenRepo.save(verificationToken);
		
		return verificationToken;
		
		
		
	}

	public User findUserByEmail(String email) {
		return UserRepository.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
	
		Passwordreset passwordrstToken= new Passwordreset(user,token);
		
		passwordrstRepo.save(passwordrstToken);
		
		
	}
	
	
	

}
