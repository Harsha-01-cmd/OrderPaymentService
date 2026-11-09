package in.HarshaRashmi;

import in.HarshaRashmi.payment.CardPayment;
import in.HarshaRashmi.payment.PaymentService;
import in.HarshaRashmi.payment.UpiPayment;
import in.strikes.CartService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("in.HarshaRashmi")
public class AppConfig {

    @Bean
    public User createUser() {
        return new User("Harsha", 21);
    }

    @Bean
    public CartService createCartService() {
        return new CartService();
    }

    @Bean
    @Qualifier("cp")
    public PaymentService createCardPayment() {
        return new CardPayment();
    }

    @Bean
    @Qualifier("upi")
    public PaymentService createUpiPayment() {
        return new UpiPayment();
    }

    // Method 1: Constructor injection using @Qualifier parameter
    @Bean
    public OrderService createOrderService(@Qualifier("cp") PaymentService paymentService) {
        return new OrderService(paymentService);
    }

    // Method 2: Constructor injection when relying on primary/single bean
    // @Bean
    // public OrderService createOrderService(PaymentService paymentService) {
    //     return new OrderService(paymentService);
    // }

    // Method 3: Direct @Bean method call + Setter injection
    // @Bean
    // public OrderService createOrderService() {
    //     PaymentService payment = createCardPayment();
    //     OrderService order = new OrderService();
    //     order.setPaymentService(payment);
    //     return order;
    // }
}
