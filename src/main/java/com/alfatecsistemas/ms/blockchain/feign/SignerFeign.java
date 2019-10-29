package com.alfatecsistemas.ms.blockchain.feign;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.alfatecsistemas.ms.blockchain.dto.EncryptionDto;

@FeignClient("signer-service")
@EnableAutoConfiguration
public interface SignerFeign {

  @GetMapping(value = "/signer/specs")
  EncryptionDto getEncryptionSpecs();
}
