package com.alfatecsistemas.ms.blockchain.dto;

public class EncryptionDto {

  private String publicKey;
  private String publicKeyAlgorithm;
  private String encryptionAlgorithm;

  public EncryptionDto(final String publicKey, final String publicKeyAlgorithm, final String encryptionAlgorithm) {
    this.publicKey = publicKey;
    this.publicKeyAlgorithm = publicKeyAlgorithm;
    this.encryptionAlgorithm = encryptionAlgorithm;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(final String publicKey) {
    this.publicKey = publicKey;
  }

  public String getPublicKeyAlgorithm() {
    return publicKeyAlgorithm;
  }

  public void setPublicKeyAlgorithm(final String publicKeyAlgorithm) {
    this.publicKeyAlgorithm = publicKeyAlgorithm;
  }

  public String getEncryptionAlgorithm() {
    return encryptionAlgorithm;
  }

  public void setEncryptionAlgorithm(final String encryptionAlgorithm) {
    this.encryptionAlgorithm = encryptionAlgorithm;
  }
}
