package com.alfatecsistemas.ms.blockchain.dto;

public class SignDocumentDto {

  private byte[] document;
  private String encryptedPrivateKey;
  private String privateKeyAlgorithm;

  public SignDocumentDto(final byte[] document, final String encryptedPrivateKey, final String privateKeyAlgorithm) {
    this.document = document;
    this.encryptedPrivateKey = encryptedPrivateKey;
    this.privateKeyAlgorithm = privateKeyAlgorithm;
  }

  public byte[] getDocument() {
    return document;
  }

  public void setDocument(final byte[] document) {
    this.document = document;
  }

  public String getEncryptedPrivateKey() {
    return encryptedPrivateKey;
  }

  public void setEncryptedPrivateKey(final String encryptedPrivateKey) {
    this.encryptedPrivateKey = encryptedPrivateKey;
  }

  public String getPrivateKeyAlgorithm() {
    return privateKeyAlgorithm;
  }

  public void setPrivateKeyAlgorithm(final String privateKeyAlgorithm) {
    this.privateKeyAlgorithm = privateKeyAlgorithm;
  }
}
