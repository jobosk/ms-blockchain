package com.alfatecsistemas.ms.blockchain.service;

import java.math.BigInteger;

public interface PatientService {

  String getCurrentBalance();

  String getHexPrivateKey();

  BigInteger[] getCoordsPublicKey();

  String createPatient();

  BigInteger getPatientInfo(String patientAddress);
}
