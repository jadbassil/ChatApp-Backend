package com.ChatApp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ChatApp.models.Chat;
import com.ChatApp.models.DeletedChats;
import com.ChatApp.models.User;
import com.ChatApp.repositories.ChatRepository;
import com.ChatApp.repositories.DeletedChatsRepository;
import com.ChatApp.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeletedChatsRepository deletedChatsRepository;
	@Autowired
	private ChatRepository chatRepository;
	
	@GetMapping("/search")
	public Map<String, Object> searchUsers(@RequestParam(value="search", required=false) String search, @RequestParam(name="userid") String userid) {
		Map<String, Object> response = new HashMap<String, Object>();
		User user = userRepository.findById(Integer.parseInt(userid)).orElse(null);
		System.out.println(search);
		if(user == null) {
			response.put("success", "false");
			response.put("message", "Invalid user");
			return response;
		}
			
		if(search == null) {
			response.put("success", "false");
			response.put("message", "Please enter a search string");
			return response;
		}

		if(search == "") {
			List<User> users = new ArrayList<User>();
			Iterable<User> usersIt = userRepository.findAll();
			for (User u : usersIt) {
				if(u.getId() != user.getId())
					users.add(u);
				for(Chat c : user.getChats()) {
					if(c.getUsers().contains(u))
						users.remove(u);
					for(DeletedChats dc: deletedChatsRepository.findAll()) {
						if(dc.getChatId() == c.getChatId() && dc.getUserId() == user.getId()) {
							Chat c1 = chatRepository.findById(dc.getChatId()).orElse(null);
							if(c1 != null) {
								List<User> c1Users = c1.getUsers();
								c1Users.remove(user);
								if(!users.containsAll(c1Users))
									users.addAll(c1Users);
							}
						}
					}
				}
			}
			if(users.isEmpty()) {
				response.put("success", "false");
				response.put("message", "No users found!");
				return response;
			}
//			List<Chat> groupChats = new ArrayList<>();
//			for(Chat c: user.getChats())
//				if(c.getType() == 1 && c.getAdmin() == user.getId())
//					groupChats.add(c);
			response.put("success", "true");
			response.put("users", users);
			//response.put("groupChats", groupChats);
			return response;
		}
		if(search.contains("@")) {
			User userBymail  = userRepository.findByEmail(search);
			if(userBymail == null || userBymail.getId() == user.getId()) {
				response.put("success", "false");
				response.put("message", "No users found!");
				return response;
			}
			response.put("success", "true");
			response.put("users", userBymail);
			return response;
		}
		if(search != ""){
			User userByUsername = userRepository.findByUsername(search);
			if(userByUsername == null || userByUsername.getId() == user.getId()) {
				response.put("success", "false");
				response.put("message", "No users found!");
				return response;
			}
			response.put("success", "true");
			response.put("users", userByUsername);
			return response;
		}
		return response;
	}
	
}

