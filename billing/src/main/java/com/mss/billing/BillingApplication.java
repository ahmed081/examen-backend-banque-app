package com.mss.billing;

import com.mss.billing.entities.Client;
import com.mss.billing.entities.Compte;
import com.mss.billing.entities.Operation;
import com.mss.billing.fiegn.ClientRestClient;
import com.mss.billing.rep.CompteRepository;
import com.mss.billing.rep.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
            CompteRepository compteRepository,
            OperationRepository operationRepository,
            ClientRestClient clientRestClient
    ){
        return args -> {

            clientRestClient.pageClient(0,20).forEach(client->{
                Compte compte = new Compte();
                compte.setSolde(2000.);
                compte.setEtat("COURANT");
                compte.setType("ACTIVE");
                compte.setData_creation(new Date());
                compte.setClientId(client.getCode());
                compteRepository.save(compte);
                System.out.println(client.getCode());
            });
            compteRepository.findAll().forEach(compte -> {
                Operation operation = new Operation();
                operation.setDate(new Date());
                operation.setType("DEBIT");
                operation.setCompte(compte);
                operation.setMontant(200.);
                compte.setSolde(compte.getSolde()-200);
                compteRepository.save(compte);
                operationRepository.save(operation);
                System.out.println(compte.getId());
            });
        };
    }
}
