package com.alfatecsistemas.ms.blockchain.service;

import java.math.BigInteger;

public interface PatientService {

  String getCurrentBalance();

  byte[] getPrivateKey();

  byte[] getPublicKey();

  String createPatient();

  BigInteger getPatientInfo(String patientAddress);
}
