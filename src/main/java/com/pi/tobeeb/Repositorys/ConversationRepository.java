package com.pi.tobeeb.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pi.tobeeb.Entities.Conversation;
import com.pi.tobeeb.Entities.Message;

public interface ConversationRepository extends JpaRepository<Conversation,Long>{
	 @Query(value="SELECT * FROM conversation u WHERE u.user1_id = :user1Id AND u.user2_id = :user2Id", nativeQuery =true)
	    Conversation findByUserId(@Param("user1Id") Long user1Id,@Param("user2Id") Long user2Id);
	 
	 @Query(value="SELECT * FROM conversation u WHERE u.user1_id = :idconver OR u.user2_id = :idconver", nativeQuery =true)
	 List<Conversation> getConverByUser(@Param("idconver") Long idconver);
	 @Query(value="SELECT * FROM conversation u WHERE ( u.user1_id = :user1Id AND u.user2_id = :user2Id ) OR ( u.user1_id = :user2Id AND u.user2_id = :user1Id )", nativeQuery =true)
	 Conversation getConverBy2User(@Param("user1Id") Long user1Id,@Param("user2Id") Long user2Id);
}
