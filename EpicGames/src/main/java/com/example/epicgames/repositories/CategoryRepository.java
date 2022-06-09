package com.example.epicgames.repositories;

import com.example.epicgames.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT * FROM categories WHERE id IN (SELECT category_id FROM game_has_category WHERE game_id = :gameId);", nativeQuery = true)
    Optional<List<Category>> getGameCategoriesNames(Integer gameId);
}
