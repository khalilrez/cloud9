package com.pi.tobeeb.Repositorys;


import com.pi.tobeeb.Entities.Message;
import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
	@Query (value= "SELECT * FROM `messages` where `conversation_id` = :idconver order by date ASC", nativeQuery =true)
	 List<Message> getMessageByConvId(@Param("idconver")Long idconver);
}