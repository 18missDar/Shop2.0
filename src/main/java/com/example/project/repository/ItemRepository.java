package com.example.project.repository;

import com.example.project.domain.Cart;
import com.example.project.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "select * from item;", nativeQuery = true)
    List<Item> findAll();

    @Query(value = "select * from item where goodID = :idg ; ", nativeQuery = true)
    Optional<Item> find(@Param("idg") Long id);
}
