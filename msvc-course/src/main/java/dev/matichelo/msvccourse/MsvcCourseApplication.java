package dev.matichelo.msvccourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableDiscoveryClient // EnableDiscoveryClient habilita el registro del servicio en el servidor de descubrimiento de servicios (Eureka)
// EnableFeignClients habilita el uso de Feign en la aplicación Spring Boot que nos permite hacer peticiones HTTP a otros servicios
@EnableFeignClients
@SpringBootApplication
public class MsvcCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcCourseApplication.class, args);
    }

}
