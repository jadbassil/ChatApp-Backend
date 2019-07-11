package com.ChatApp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ChatApp.models.ChatUserKey;
import com.ChatApp.models.DeletedChats;

public interface DeletedChatsRepository extends CrudRepository<DeletedChats, ChatUserKey> {

}
