package com.alfatecsistemas.ms.blockchain.feign;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alfatecsistemas.ms.blockchain.dto.EncryptionDto;
import com.alfatecsistemas.ms.blockchain.dto.SignDocumentDto;
import com.alfatecsistemas.ms.blockchain.dto.ValidateSignatureDto;

@FeignClient("signer-service")
@EnableAutoConfiguration
public interface SignerFeign {

  @GetMapping(value = "/signer/specs")
  EncryptionDto getEncryptionSpecs();

  @PutMapping(value = "/signer/sign")
  byte[] signDocument(final @RequestBody SignDocumentDto signDocumentDto);

  @PutMapping(value = "/signer/validate")
  boolean validateDocumentSignature(final @RequestBody ValidateSignatureDto validateSignatureDto);
}
