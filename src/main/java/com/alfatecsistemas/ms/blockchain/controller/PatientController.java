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
    final PublicKey signerPublicKey = buildPublicKey(signerClient.getPublicKey(), "RSA");
    final byte[] encryptedPrivateKey = encrypt(patientService.getPrivateKey(), signerPublicKey, "RSA/ECB/PKCS1Padding");
    final byte[] signedDocument = signerClient.signDocument(new SignDocumentDto(document, encryptedPrivateKey));
    final byte[] publicKey = patientService.getPublicKey();
    return signerClient.validateDocumentSignature(new ValidateSignatureDto(signedDocument, publicKey));
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
}
