package com.alfatecsistemas.ms.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfatecsistemas.ms.blockchain.dto.EncryptionDto;
import com.alfatecsistemas.ms.blockchain.feign.SignerFeign;
import com.alfatecsistemas.ms.blockchain.service.PatientService;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

@RestController
@RequestMapping("/blockchain")
public class PatientController {

  @Autowired
  PatientService patientService;

  @Autowired
  private SignerFeign signerClient;

  private byte[] encryptPrivateKey(final byte[] privateKey) {
    final EncryptionDto encryptionDto = signerClient.getEncryptionSpecs();
    final PublicKey signerPublicKey = buildPublicKey(
        Base64.getDecoder().decode(encryptionDto.getPublicKey())
        , encryptionDto.getPublicKeyAlgorithm()
    );
    return encrypt(privateKey, signerPublicKey, encryptionDto.getEncryptionAlgorithm());
  }

  private static PublicKey buildPublicKey(final byte[] key, final String algorithm) {
    PublicKey result;
    try {
      final EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(key);
      final KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
      result = keyFactory.generatePublic(encodedKeySpec);
    } catch (final Exception e) {
      result = null;
    }
    return result;
  }

  private static byte[] encrypt(final byte[] data, final Key key, final String algorithm) {
    byte[] result;
    try {
      final Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      result = cipher.doFinal(data);
    } catch (final Exception e) {
      result = new byte[0];
    }
    return result;
  }

  @GetMapping(value = "/key/private")
  public String getPrivateKey() {
    return Base64.getEncoder().encodeToString(encryptPrivateKey(patientService.getPrivateKey()));
  }

  @GetMapping(value = "/key/public")
  public String getPublicKey() {
    return Base64.getEncoder().encodeToString(patientService.getPublicKey());
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
