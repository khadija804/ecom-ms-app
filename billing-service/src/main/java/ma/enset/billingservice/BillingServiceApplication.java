package ma.enset.billingservice;

import ma.enset.billingservice.entities.Bill;
import ma.enset.billingservice.entities.ProductItem;
import ma.enset.billingservice.repository.BillRepository;
import ma.enset.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BillRepository billRepository,
                                               ProductItemRepository productItemRepository){
        return args -> {
            List<Long> customersId = List.of(1L,2L,3L);
            List<Long> productsId = List.of(1L,2L);
            customersId.forEach(clientId->{
                Bill bill = new Bill();
                bill.setBillingDate(new Date());
                bill.setCustomerId(clientId);
                billRepository.save(bill);
                productsId.forEach(productId->{
                    ProductItem productItem = new ProductItem();
                    productItem.setPrice(1000*Math.random()*200);
                    productItem.setQuantity(1+new Random().nextInt());
                    productItem.setProductId(productId);
                    productItem.setBill(bill);
                    productItemRepository.save(productItem);
                });
            });
        };
    }

}
