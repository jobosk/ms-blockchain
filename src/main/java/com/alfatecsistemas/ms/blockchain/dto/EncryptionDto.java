package com.alfatecsistemas.ms.blockchain.dto;

public class EncryptionDto {

  private String hexPublicKey;
  private String publicKeyAlgorithm;
  private String encryptionAlgorithm;

  public EncryptionDto(final String hexPublicKey, final String publicKeyAlgorithm, final String encryptionAlgorithm) {
    this.hexPublicKey = hexPublicKey;
    this.publicKeyAlgorithm = publicKeyAlgorithm;
    this.encryptionAlgorithm = encryptionAlgorithm;
  }

  public String getHexPublicKey() {
    return hexPublicKey;
  }

  public void setHexPublicKey(final String hexPublicKey) {
    this.hexPublicKey = hexPublicKey;
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
