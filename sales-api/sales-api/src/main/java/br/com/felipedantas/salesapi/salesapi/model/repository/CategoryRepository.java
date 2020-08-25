package br.com.felipedantas.salesapi.salesapi.model.repository;

import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName( String name );
}
