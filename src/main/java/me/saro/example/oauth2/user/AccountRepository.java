package me.saro.example.oauth2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @EntityGraph("roles")
    @Query("SELECT a FROM account a WHERE account = :account")
    Optional<Account> findByAccount(@Param("account") String account);
}