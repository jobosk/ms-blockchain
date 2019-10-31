package com.alfatecsistemas.ms.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfatecsistemas.ms.blockchain.feign.SignerFeign;
import com.alfatecsistemas.ms.blockchain.service.PatientService;
import com.alfatecsistemas.ms.common.Constants.Algorithm;
import com.alfatecsistemas.ms.common.dto.KeyDto;
import com.alfatecsistemas.ms.common.util.CryptoUtil;
import com.alfatecsistemas.ms.common.util.EncodingUtil;

import java.math.BigInteger;
import java.security.PublicKey;

@RestController
@RequestMapping("/blockchain")
public class PatientController {

  @Autowired
  PatientService patientService;

  @Autowired
  private SignerFeign signerClient;

  private byte[] encryptPrivateKey(final byte[] privateKey) {
    byte[] result;
    try {
      final KeyDto publicKeyDto = signerClient.getPublicKey();
      final PublicKey signerPublicKey = CryptoUtil.buildPublicKey(
          EncodingUtil.decodeBase64(publicKeyDto.getKey())
          , publicKeyDto.getAlgorithm()
      );
      result = CryptoUtil.encrypt(privateKey, signerPublicKey, Algorithm.ENCRYPTION);
    } catch (final Exception e) {
      result = new byte[0];
    }
    return result;
  }

  @GetMapping(value = "/key/private")
  public KeyDto getPrivateKey() {
    return new KeyDto(
        EncodingUtil.encodeBase64(encryptPrivateKey(patientService.getPrivateKey()))
        , Algorithm.EC
        , Algorithm.ENCRYPTION
        , Algorithm.EC_CURVE_NAME
    );
  }

  @GetMapping(value = "/key/public")
  public KeyDto getPublicKey() {
    return new KeyDto(
        EncodingUtil.encodeBase64(patientService.getPublicKey())
        , Algorithm.EC
        , null
        , Algorithm.EC_CURVE_NAME
    );
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
