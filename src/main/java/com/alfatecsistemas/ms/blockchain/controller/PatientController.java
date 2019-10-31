package com.alfatecsistemas.ms.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfatecsistemas.ms.blockchain.service.PatientService;
import com.alfatecsistemas.ms.common.dto.KeyDto;

import java.math.BigInteger;

@RestController
@RequestMapping("/blockchain")
public class PatientController {

  @Autowired
  PatientService patientService;

  @GetMapping(value = "/key/private")
  public KeyDto getPrivateKey() {
    return patientService.getPrivateKey();
  }

  @GetMapping(value = "/key/public")
  public KeyDto getPublicKey() {
    return patientService.getPublicKey();
  }

  @PostMapping(value = "/patient")
  public String createPatient() {
    return patientService.createPatient();
  }

  @GetMapping(value = "/patient/{address}")
  public BigInteger getPatientInfo(final @PathVariable("address") String address) {
    return patientService.getPatientInfo(address);
  }
}
