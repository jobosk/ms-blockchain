package com.alfatecsistemas.ms.blockchain.dto;

public class SignDocumentDto {

  private byte[] document;
  private byte[] privateKey;

  public SignDocumentDto(final byte[] document, final byte[] privateKey) {
    this.document = document;
    this.privateKey = privateKey;
  }

  public byte[] getDocument() {
    return document;
  }

  public void setDocument(final byte[] document) {
    this.document = document;
  }

  public byte[] getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(final byte[] privateKey) {
    this.privateKey = privateKey;
  }
}
