package com.ChatApp.controllers;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ChatApp.models.User;
import com.ChatApp.repositories.UserRepository;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
		
	@PostMapping("/signup")
	public Map<String, String> signupSubmit(@RequestParam Map<String, String> req) {

		HashMap<String, String> response = new HashMap<String, String>();

		if(userRepository.findByUsername(req.get("username")) != null) {
			response.put("success", "false");
        	response.put("message", "Username already exists");
			return response;
		}
		
		if(userRepository.findByEmail(req.get("email")) != null) {
			response.put("success", "false");
	    	response.put("message", "E-mail already exists");
			return response;
		}
		User user = new User();
		user.setUsername(req.get("username"));
		user.setEmail(req.get("email"));
		String password = req.get("password");
		password = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(password);
		try {
			userRepository.save(user);
			//MailController.sendMail(user.getEmail(), user.getUsername());
		}catch (Exception e) {
			System.out.println(e.getMessage());
			response.put("success", "false");
        	response.put("message", "Error saving your data. Please try again");
           	return response;
		}
		response.put("success", "true");
    	response.put("message", "You successfully registered");
       	return response;
	}
	
	@PostMapping(path="/login")
	public Map<String, Object> loginCheck(@RequestParam Map<String, String> req) {
		User user = userRepository.findByUsername(req.get("username"));
		HashMap<String, Object> response = new HashMap<String, Object>();
		if(user == null) {
			response.put("success", "false");
        	response.put("message", "Invalid username");
			return response;
		}else if(!BCrypt.checkpw(req.get("password"), user.getPassword())){
			response.put("success", "false");
        	response.put("message", "Invalid password");
			return response;
		}else {
			response.put("success", "true");
        	response.put("message", "login successful");
        	response.put("username", req.get("username"));
        	response.put("password", req.get("password"));
        	response.put("id", user.getId());
			return response;
		}		
	}
	
}

