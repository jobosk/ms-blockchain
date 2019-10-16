package com.alfatecsistemas.ms.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsBlockchainApplication {

  public static void main(final String[] args) {
    SpringApplication.run(MsBlockchainApplication.class, args);
  }

}
