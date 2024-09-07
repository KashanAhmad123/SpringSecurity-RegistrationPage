package controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import AllEvents.RegistrationCompleteEvent;
import entity.User;
import entity.VerificationToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import model.Passwordmodel;
import model.UserModel;
import service.UserService;

@RestController
@Slf4j
public class RegistrationController {
@Autowired
private UserService userService;

@Autowired
private ApplicationEventPublisher publisher;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
User user=userService.registerUser(userModel);
publisher.publishEvent(new RegistrationCompleteEvent(
		user,
		applicationUrl(request)
		));
return "Successfully Register";	
}
    @GetMapping("/verifyRegistration")
	public String verifyRegistration(@RequestParam("token")String token) {
	 String result = userService.validateVerificationToken(token);
	 if(result.equalsIgnoreCase("vaslid")) {
		 return "user verified now";
	 }
	 
	 return " bad request";
		
	}
	@GetMapping("/resendverifyToken")
    public String resendVerifyToken(@RequestParam("token")String oldToken, HttpServletRequest request) {
		VerificationToken verificationToken= userService.generateNewVtoken(oldToken);
		 User user=VerificationToken.getUser();
		 
		resendVerifytokenMail(user,applicationUrl(request), verificationToken);
		
		 return"Verification Link Sent";
		
	}
    
	private void resendVerifytokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
		// TODO Auto-generated method stub
		String url =
	               applicationUrl
	                        + "/verifyRegistration?token="
	                       + verificationToken.getToken();
		
		// sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
               url);

	}
	
	@PostMapping("/resetPassword")
	public String resetpassword(@RequestBody Passwordmodel passwordmodel, HttpServletRequest request ) {
	User user= UserService.findUserByEmail(passwordmodel.getEmail());
	String url= "";
	if(user!=null) {
		String token= UUID.randomUUID().toString();
		UserService.createPasswordResetTokenForUser(user,token);
		
		url= passwordResettokenmail(user, applicationUrl(request),token)
	}
	}
	
	
	private String passwordResettokenmail(User user, String applicationUrl, String token) {
		String url =
	               applicationUrl
	                        + "/SavePassword?token="
	                       + token;
		
		// sendVerificationEmail()
     log.info("Click the link to reset the account: {}",
            url);

		return url;
	}
	private String applicationUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() ;
	}

	
}
