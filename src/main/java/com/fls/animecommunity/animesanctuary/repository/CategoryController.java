package com.fls.animecommunity.animesanctuary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fls.animecommunity.animesanctuary.model.category.Category;

@Repository
public interface CategoryController extends JpaRepository<Category, Long>{

}
