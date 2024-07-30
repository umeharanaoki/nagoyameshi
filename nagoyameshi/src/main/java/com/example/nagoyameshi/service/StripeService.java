package com.example.nagoyameshi.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	// 扱う金額は1種類なので定数で定義
	private static final long subscriptionAmount = 300L;
	
	private final SubscriptionService subscriptionService;
	private final UserService userService;
	
	public StripeService(SubscriptionService subscriptionService, UserService userService) {
		this.subscriptionService = subscriptionService;
		this.userService = userService;
	}
	
	public String createStripeSession(HttpServletRequest httpServletRequest, Integer userId) {
		Stripe.apiKey = stripeApiKey;
		// StringBuffer→String型に変換
		String requestURL = httpServletRequest.getRequestURL().toString();
		
		// 設計書の作成
		SessionCreateParams params =
			SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.addLineItem(
					SessionCreateParams.LineItem.builder()
						.setPriceData(
							SessionCreateParams.LineItem.PriceData.builder()
								.setProductData(
									SessionCreateParams.LineItem.PriceData.ProductData.builder()
									.setName("NAGOYAMESHI プレミアムプラン")
									.build()
								)
								.setUnitAmount(subscriptionAmount)  // 料金
								.setCurrency("JPY")  // 通貨
								.setRecurring(
									SessionCreateParams.LineItem.PriceData.Recurring.builder()
									.setInterval(SessionCreateParams.LineItem.PriceData.Recurring.Interval.MONTH)
									.build()
								)
								.build()
						)
						.setQuantity(1L)
						.build()
				)
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)  // 支払方法をサブスクリプションに指定
				.setSuccessUrl(requestURL + "/premium-redirect")  // プレミアムプラン紹介ページからトップページにリダイレクト
				.setCancelUrl(requestURL)  // リクエストURL（プレミアム紹介ページ）に戻す
				.setClientReferenceId(userId.toString())
			.build();
		try {
			Session session = Session.create(params);
			return session.getId();
		} catch(StripeException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// セッションから情報を取得し、DBに保存する
	public void processSessionCompleted(Event event) {
		// Event→Optional<StripeObject>
		Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
		optionalStripeObject.ifPresentOrElse(stripeObject -> {
			// オブジェクトが存在する場合の処理
			if (stripeObject instanceof Session) {
				// StripeObject→各種オブジェクト
				Session session = (Session)stripeObject;
				SessionRetrieveParams params = SessionRetrieveParams.builder()
					.addExpand("invoice")  // 支払い詳細
					.addExpand("subscription")  // サブスクリプション情報
					.build();
				
				try {
					session = Session.retrieve(session.getId(), params, null);
					// ユーザーid
					String clientReferenceId = session.getClientReferenceId();
					Integer userId = null;
					if(clientReferenceId != null && !clientReferenceId.isEmpty()) {
						try {
							userId = Integer.parseInt(clientReferenceId);
						} catch(NumberFormatException e) {
							// 数値以外の文字列が含まれていた場合のエラー処理
							System.err.println("数値変換エラー: " + e.getMessage());
						}
					}
					// Invoiceオブジェクト
					Invoice invoiceObject = session.getInvoiceObject();
					// 支払日・加入日
					Long paidAtTimeStamp = invoiceObject.getStatusTransitions().getPaidAt();
					LocalDate paymentDate = Instant.ofEpochSecond(paidAtTimeStamp)  // UNIX時間→日付（LocalDate）に変換
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
					// 支払額
					Integer amountOfPayment = Math.toIntExact(invoiceObject.getAmountPaid());  // Long→Integer
					// 支払い状況
					String paymentStatus = invoiceObject.getStatus();
					// 次回請求日
					Long nextBillingTimeStamp = session.getSubscriptionObject().getCurrentPeriodEnd();
					LocalDate nextBillingDate = Instant.ofEpochSecond(nextBillingTimeStamp)
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
					
					// DBへの登録処理
					subscriptionService.createPayment(userId, paymentDate, amountOfPayment, paymentStatus);
					subscriptionService.createSubscription(userId, paymentDate, nextBillingDate);
					userService.joinPremium(userId);
					
				} catch(StripeException e) {
					e.printStackTrace();
				}
				System.out.println("サブスクリプション内容の登録処理が成功しました。");
		        System.out.println("Stripe API Version: " + event.getApiVersion());
		        System.out.println("stripe-java Version: " + Stripe.VERSION);
	        
			} else {
				System.err.println("Received StripeObject is not of type Session.");
			}
		},
		() -> {
			// オブジェクトのが存在しない場合の処理
			System.out.println("サブスクリプション内容の登録処理が失敗しました。");
	        System.out.println("Stripe API Version: " + event.getApiVersion());
	        System.out.println("stripe-java Version: " + Stripe.VERSION);
		});
	}
}
