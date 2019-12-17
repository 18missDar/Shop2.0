package com.example.project.repository;

import com.example.project.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findByTitle(String title);
    List<Goods> findByCategory(String category);

    @Query(value = "select * from goods where active = true;", nativeQuery = true)
    List<Goods> findAll();

}
