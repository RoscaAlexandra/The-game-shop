package com.proiecte.GamesStore.repositories;

import com.proiecte.GamesStore.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
   Category findByName(String name);
   Category findById(long a);

    @Query("select c from Category c")
    Page<Category> findAllPage(Pageable pageable);
}
