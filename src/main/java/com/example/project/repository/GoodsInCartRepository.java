package com.example.project.repository;


import com.example.project.domain.GoodsInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsInCartRepository extends JpaRepository<GoodsInCart, Long> {
}
