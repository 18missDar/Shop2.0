package com.example.project.repository;

import com.example.project.domain.Usercarts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPayedGoods  extends JpaRepository<Usercarts, Long> {
    List<Usercarts> findByUsername(String username);
}
