package com.alfatecsistemas.ms.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfatecsistemas.ms.blockchain.service.PatientService;

import java.math.BigInteger;

@RestController
@RequestMapping("/blockchain")
public class PatientController {

  @Autowired
  PatientService patientService;

  @PostMapping(value = "/patient")
  public String createPatient() {
    return patientService.createPatient();
  }

  @GetMapping(value = "/patient/{address}")
  public BigInteger getPatientInfo(final @PathVariable("address") String address) {
    return patientService.getPatientInfo(address);
  }
}
