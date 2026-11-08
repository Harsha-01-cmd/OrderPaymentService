# OrderPaymentService 💳📦

A modular order and payment processing backend service built with **Spring Core**, demonstrating **Inversion of Control (IoC)**, **Dependency Injection (DI)**, **Loose Coupling**, and the **Strategy Pattern** for pluggable payment methods.

---

## 🚀 Key Highlights & Architectural Concepts

- **Inversion of Control (IoC)**: Object lifecycle and dependency management is fully delegated to the Spring IoC Container (`AnnotationConfigApplicationContext`).
- **Dependency Injection (DI)**: Constructor-based injection ensures that `OrderService` is immutable and always valid at runtime.
- **Loose Coupling & Strategy Pattern**: `OrderService` depends exclusively on the `PaymentService` abstraction. Concrete payment strategies (`CardPayment`, `UpiPayment`) can be swapped seamlessly without modifying business logic.
- **Ambiguity Resolution (`@Qualifier`)**: Solves Spring's `NoUniqueBeanDefinitionException` when multiple candidate beans implement the same interface.
- **Integration of 3rd-Party Classes (`@Bean`)**: Demonstrates how external classes (`CartService` from non-scanned packages) can be instantiated and managed as Spring beans using Java configuration.

---

## 🏛️ Architecture & Component Flow

```mermaid
flowchart TD
    subgraph SpringIoCContainer["Spring IoC Container (AnnotationConfigApplicationContext)"]
        direction TB
        AppConfig["AppConfig.java\n(@Configuration, @ComponentScan)"]
        
        subgraph Beans["Registered Beans"]
            UserBean["User Bean\n('Aditya', 28)"]
            CartBean["CartService Bean\n(External Package)"]
            CardBean["CardPayment Bean\n(@Qualifier('cp'))"]
            UpiBean["UpiPayment Bean\n(@Qualifier('upi'))"]
            OrderBean["OrderService Bean"]
        end
        
        AppConfig -->|Defines & Registers| UserBean
        AppConfig -->|Defines & Registers| CartBean
        AppConfig -->|Defines & Registers| CardBean
        AppConfig -->|Defines & Registers| UpiBean
        AppConfig -->|Wires with @Qualifier('cp')| OrderBean
        CardBean -.->|Injected as PaymentService| OrderBean
    end

    Main["Main.java\n(context.getBean)"] -->|Retrieves| OrderBean
    Main -->|Retrieves| CartBean
    OrderBean -->|Calls pay() & places order| Output["Console Output:\n'Paying via card'\n'Order placed'"]
```

---

## 📁 Project Structure

```text
OrderPaymentService/
├── pom.xml                                      # Maven configuration & Spring Context dependency
├── .gitignore                                   # Standard ignore rules
├── README.md                                    # Documentation & architecture guide
└── src/
    └── main/
        └── java/
            ├── in/
            │   ├── HarshaRashmi/
            │   │   ├── AppConfig.java           # Java-based Spring container configuration
            │   │   ├── Main.java                # Application bootstrap entry point
            │   │   ├── OrderService.java        # Core order domain service
            │   │   ├── User.java                # Domain model POJO
            │   │   └── payment/
            │   │       ├── PaymentService.java  # Payment interface abstraction
            │   │       ├── CardPayment.java     # Credit/Debit Card implementation
            │   │       └── UpiPayment.java      # UPI payment implementation
            │   └── strikes/
            │       └── CartService.java         # External library simulation
```

---

## 🔍 Core Component Breakdown

### 1. Payment Abstraction & Strategies
```java
public interface PaymentService {
    void pay();
}
```
Implemented by `CardPayment` (`"Paying via card"`) and `UpiPayment` (`"Paying by UPI"`).

### 2. Constructor Dependency Injection (`OrderService`)
```java
public class OrderService {
    private PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.pay();
        System.out.println("Order placed");
    }
}
```

### 3. Container Configuration & Disambiguation (`AppConfig`)
```java
@Configuration
@ComponentScan("in.HarshaRashmi")
public class AppConfig {

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
```

---

## 🛠️ Getting Started

### Prerequisites
- **JDK 17** or higher (JDK 21 LTS recommended)
- **Apache Maven 3.8+**

### Build and Run
Clone the repository and run the application:

```bash
git clone https://github.com/Harsha-01-cmd/OrderPaymentService.git
cd OrderPaymentService
mvn compile exec:java -Dexec.mainClass="in.HarshaRashmi.Main"
```

### Expected Output
```text
Paying via card
Order placed
Added to cart
```

---

## 📜 License
Distributed under the MIT License.
