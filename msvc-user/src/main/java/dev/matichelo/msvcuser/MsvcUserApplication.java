package dev.matichelo.msvcuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Spring cloud es un conjunto de herramientas que nos permiten crear aplicaciones distribuidas y escalables
// @EnableDiscoveryClient sirve para que el servicio se registre en el servidor de descubrimiento de servicios (Eureka)
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MsvcUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcUserApplication.class, args);
    }

}
