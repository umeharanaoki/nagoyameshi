package com.example.nagoyameshi.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Payment;
import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.repository.PaymentRepository;
import com.example.nagoyameshi.repository.SubscriptionRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class SubscriptionService {
	private final UserRepository userRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final PaymentRepository paymentRepository;
	
	public SubscriptionService(UserRepository userRepository, SubscriptionRepository subscriptionRepository, PaymentRepository paymentRepository) {
		this.userRepository = userRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.paymentRepository = paymentRepository;
	}
	
	// 支払い履歴を登録
	@Transactional
	public void createPayment(Integer userId, LocalDate paymentDate, Integer amountOfPayment, String paymentStatus) {
		Payment payment = new Payment();
		
		payment.setUser(userRepository.getReferenceById(userId));
		payment.setPaymentDate(paymentDate);
		payment.setAmountOfPayment(amountOfPayment);
		payment.setPaymentStatus(paymentStatus);
		
		paymentRepository.save(payment);
	}
	
	// サブスクリプション情報を登録
	@Transactional
	public void createSubscription(Integer userId, LocalDate paymentDate, LocalDate nextBillingDate) {
		Subscription subscription = new Subscription();
		
		subscription.setUser(userRepository.getReferenceById(userId));
		subscription.setJoinDate(paymentDate);
		subscription.setNextBillingDate(nextBillingDate);
		subscription.setCancellationDate(null);  // 解約日のデフォルトはnull
		
		subscriptionRepository.save(subscription);
	}
	
	// 各時期のサブスクリプション支払い件数
	public Map<Integer, Map<String, Integer>> getSubscriptionCountsForYears(List<Integer> years) {
        Map<Integer, Map<String, Integer>> yearlyCounts = new HashMap<>();
        
        for (Integer year : years) {
            int firstHalfCount = paymentRepository.countPaymentsForPeriod(year, 1, 6);
            int secondHalfCount = paymentRepository.countPaymentsForPeriod(year, 7, 12);
            
            Map<String, Integer> halfYearCounts = new HashMap<>();
            halfYearCounts.put("firstHalf", firstHalfCount);
            halfYearCounts.put("secondHalf", secondHalfCount);
            
            yearlyCounts.put(year, halfYearCounts);
        }

        return yearlyCounts;
    }
}
