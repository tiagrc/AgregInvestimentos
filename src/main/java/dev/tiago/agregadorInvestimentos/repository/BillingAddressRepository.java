package dev.tiago.agregadorInvestimentos.repository;


import dev.tiago.agregadorInvestimentos.entity.BillingAddress;
import dev.tiago.agregadorInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
