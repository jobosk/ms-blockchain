package com.alfatecsistemas.ms.blockchain.service;

import com.alfatecsistemas.ms.common.dto.KeyDto;

import java.math.BigInteger;

public interface PatientService {

  String getCurrentBalance();

  KeyDto getPrivateKey();

  KeyDto getPublicKey();

  String createPatient();

  BigInteger getPatientInfo(String patientAddress);
}
