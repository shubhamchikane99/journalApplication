package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

}
