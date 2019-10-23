package com.alfatecsistemas.ms.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfatecsistemas.ms.blockchain.dto.SignDocumentDto;
import com.alfatecsistemas.ms.blockchain.dto.ValidateSignatureDto;
import com.alfatecsistemas.ms.blockchain.feign.SignerFeign;
import com.alfatecsistemas.ms.blockchain.service.PatientService;

import java.math.BigInteger;
import java.util.Base64;

@RestController
@RequestMapping("/blockchain")
public class PatientController {

  @Autowired
  PatientService patientService;

  @Autowired
  private SignerFeign signerClient;

  @PostMapping(value = "/patient")
  public String createPatient() {
    return patientService.createPatient();
  }

  @GetMapping(value = "/patient/{address}")
  public BigInteger getPatientInfo(final @PathVariable("address") String address) {
    return patientService.getPatientInfo(address);
  }

  @GetMapping(value = "/signature/test/{message}")
  public boolean testSignature(final @PathVariable("message") String message) {
    final byte[] document = Base64.getDecoder().decode(message);
    final SignDocumentDto signDocumentDto = new SignDocumentDto(document, patientService.getPrivateKey());
    final byte[] signedDocument = signerClient.signDocument(signDocumentDto);
    final ValidateSignatureDto validateSignatureDto =
        new ValidateSignatureDto(signedDocument, patientService.getPublicKey());
    return signerClient.validateDocumentSignature(validateSignatureDto);
  }
}
