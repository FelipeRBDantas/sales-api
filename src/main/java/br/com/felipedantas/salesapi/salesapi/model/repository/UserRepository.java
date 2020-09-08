package br.com.felipedantas.salesapi.salesapi.model.repository;

import br.com.felipedantas.salesapi.salesapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserRepository, Long> {
    User getUserByUsername( String username );
}
