package com.alfatecsistemas.ms.blockchain.service;

import java.math.BigInteger;

public interface PatientService {

  String getCurrentBalance();

  String getPrivateKey();

  String getPublicKey();

  String createPatient();

  BigInteger getPatientInfo(String patientAddress);
}
