package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	// 指定された年と月の範囲で決済回数をカウントするメソッド
	@Query("SELECT COUNT(p) FROM Payment p WHERE YEAR(p.paymentDate) = :year AND MONTH(p.paymentDate) BETWEEN :startMonth AND :endMonth")
    int countPaymentsForPeriod(@Param("year") int year, @Param("startMonth") int startMonth, @Param("endMonth") int endMonth);
}
