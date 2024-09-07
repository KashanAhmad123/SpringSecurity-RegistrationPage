package service;

import entity.User;
import entity.VerificationToken;
import model.UserModel;

public interface UserService {

	User registerUser(UserModel userModel);

	void saveVerificationTokenForUser(String token, User user);

	String validateVerificationToken(String token);

	VerificationToken generateNewVtoken(String oldToken);

	static User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	 static void createPasswordResetTokenForUser(User user, String token) {
		// TODO Auto-generated method stub
		
	}

}
