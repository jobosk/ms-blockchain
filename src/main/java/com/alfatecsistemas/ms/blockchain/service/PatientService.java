package com.alfatecsistemas.ms.blockchain.service;

import java.math.BigInteger;

public interface PatientService {

  String getCurrentBalance();

  String getHexPrivateKey();

  String getHexPublicKey();

  String createPatient();

  BigInteger getPatientInfo(String patientAddress);
}
