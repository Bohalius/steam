package com.example.epicgames.repositories;

import com.example.epicgames.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUserId(Integer id);

    Account findByClientId(Integer id);
}
