package in.HarshaRashmi.payment;

public class UpiPayment implements PaymentService {

    @Override
    public void pay() {
        System.out.println("Paying by UPI");
    }
}
