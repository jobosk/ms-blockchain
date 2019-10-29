package com.alfatecsistemas.ms.blockchain.dto;

public class SignDocumentDto {

  private byte[] document;
  private String encryptedPrivateKey;

  public SignDocumentDto(final byte[] document, final String encryptedPrivateKey) {
    this.document = document;
    this.encryptedPrivateKey = encryptedPrivateKey;
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
}
