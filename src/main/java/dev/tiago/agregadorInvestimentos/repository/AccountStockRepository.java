package dev.tiago.agregadorInvestimentos.repository;


import dev.tiago.agregadorInvestimentos.entity.AccountStock;
import dev.tiago.agregadorInvestimentos.entity.AccountStockId;
import dev.tiago.agregadorInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
