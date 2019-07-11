package com.ChatApp.models;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
public class DeletedChats{
	
	@EmbeddedId
	private ChatUserKey chatUserKey = new ChatUserKey();
	
	public DeletedChats() {
		super();
	}

	public DeletedChats(Integer chatId, Integer userId) {
		super();
		this.chatUserKey.setChatId(chatId);
		this.chatUserKey.setUserId(userId);
	}

	public Integer getChatId() {
		return chatUserKey.getChatId();
	}

	public void setChatId(Integer chatId) {
		this.chatUserKey.setChatId(chatId);
	}

	public Integer getUserId() {
		return this.chatUserKey.getUserId();
	}

	public void setUserId(Integer userId) {
		this.chatUserKey.setUserId(userId);
	}
	
}