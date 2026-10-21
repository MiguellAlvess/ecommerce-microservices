package br.com.miguelalves.order.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "br.com.miguelalves.order.client")
public class ClientsConfig {

}
