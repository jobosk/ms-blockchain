package com.alfatecsistemas.ms.blockchain.dto;

public class EncryptionDto {

  private byte[] publicKey;
  private String publicKeyAlgorithm;
  private String encryptionAlgorithm;

  public byte[] getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(final byte[] publicKey) {
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
