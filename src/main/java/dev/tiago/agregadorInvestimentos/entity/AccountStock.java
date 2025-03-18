package dev.tiago.agregadorInvestimentos.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_accounts_stock")
public class AccountStock {

    @EmbeddedId
    private AccountStockId id;


}
