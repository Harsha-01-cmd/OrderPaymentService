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
        return new User("Aditya", 28);
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

    @Bean
    public OrderService createOrderService(@Qualifier("cp") PaymentService paymentService) {
        return new OrderService(paymentService);
    }
}
