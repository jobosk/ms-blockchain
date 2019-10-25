package com.alfatecsistemas.ms.blockchain.dto;

public class SignDocumentDto {

  private byte[] document;
  private String encryptedHexPrivateKey;

  public SignDocumentDto(final byte[] document, final String encryptedHexPrivateKey) {
    this.document = document;
    this.encryptedHexPrivateKey = encryptedHexPrivateKey;
  }

  public byte[] getDocument() {
    return document;
  }

  public void setDocument(final byte[] document) {
    this.document = document;
  }

  public String getEncryptedHexPrivateKey() {
    return encryptedHexPrivateKey;
  }

  public void setEncryptedHexPrivateKey(final String encryptedHexPrivateKey) {
    this.encryptedHexPrivateKey = encryptedHexPrivateKey;
  }
}
