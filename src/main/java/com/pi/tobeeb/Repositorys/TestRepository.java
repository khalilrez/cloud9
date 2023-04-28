package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test,Long> {
}
