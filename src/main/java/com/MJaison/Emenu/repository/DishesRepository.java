package com.MJaison.Emenu.repository;


import com.MJaison.Emenu.dtos.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishesRepository  extends JpaRepository<Dish, Long> {
}
