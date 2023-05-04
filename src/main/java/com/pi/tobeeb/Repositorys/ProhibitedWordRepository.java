package com.pi.tobeeb.Repositorys;


import com.pi.tobeeb.Entities.ProhibitedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProhibitedWordRepository extends JpaRepository<ProhibitedWord, Long> {
}