package com.example.project.repository;

import com.example.project.domain.Goods;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface GoodsRepository extends CrudRepository<Goods, Long> {
    List<Goods> findByTitle(String title);
}
