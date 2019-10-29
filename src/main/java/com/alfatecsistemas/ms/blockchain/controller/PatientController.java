package com.alfatecsistemas.ms.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfatecsistemas.ms.blockchain.dto.EncryptionDto;
import com.alfatecsistemas.ms.blockchain.dto.SignDocumentDto;
import com.alfatecsistemas.ms.blockchain.dto.ValidateSignatureDto;
import com.alfatecsistemas.ms.blockchain.feign.SignerFeign;
import com.alfatecsistemas.ms.blockchain.service.PatientService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

  @GetMapping(value = "/sign/{message}")
  public String signMessage(final @PathVariable("message") String message) {

    final byte[] document = formatDocument(message);

    final EncryptionDto encryptionDto = signerClient.getEncryptionSpecs();

    final byte[] signerPublicKeyDecoded = decodeBase64(encryptionDto.getPublicKey());
    final PublicKey signerPublicKey = buildPublicKey(signerPublicKeyDecoded, encryptionDto.getPublicKeyAlgorithm());

    final byte[] senderPrivateKeyDecoded = patientService.getPrivateKey();
    final byte[] senderPrivateKeyEncrypted =
        encrypt(senderPrivateKeyDecoded, signerPublicKey, encryptionDto.getEncryptionAlgorithm());
    final String senderPrivateKeyEncryptedEncoded = encodeBase64(senderPrivateKeyEncrypted);

    final byte[] signature =
        signerClient.signDocument(new SignDocumentDto(document, senderPrivateKeyEncryptedEncoded, "EC"));

    return encodeBase64(signature);
  }

  @GetMapping(value = "/verify/{message}/{signature}")
  public boolean verifySignatureMessage(final @PathVariable("message") String message,
      final @PathVariable("signature") String signature) {

    final byte[] document = formatDocument(message);

    final byte[] signatureDecoded = decodeBase64(signature);

    final byte[] senderPublicKeyDecoded = patientService.getPublicKey();
    final String senderPublicKeyEncoded = encodeBase64(senderPublicKeyDecoded);

    return signerClient
        .validateDocumentSignature(new ValidateSignatureDto(document, signatureDecoded, senderPublicKeyEncoded, "EC"));
  }

  private static byte[] formatDocument(final String document) {
    byte[] result;
    try {
      result = document.getBytes(StandardCharsets.UTF_8);
    } catch (final Exception e) {
      result = new byte[0];
    }
    return result;
  }

  private static byte[] decodeBase64(final String encoded) {
    return Base64.getDecoder().decode(encoded);
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

  private static String encodeBase64(final byte[] decoded) {
    return Base64.getEncoder().encodeToString(decoded);
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
