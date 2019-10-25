package com.alfatecsistemas.ms.blockchain.dto;

public class ValidateSignatureDto {

  private byte[] originalDocument;
  private byte[] signedDocument;
  private byte[] publicKey;

  public ValidateSignatureDto(final byte[] originalDocument, final byte[] signedDocument, final byte[] publicKey) {
    this.originalDocument = originalDocument;
    this.signedDocument = signedDocument;
    this.publicKey = publicKey;
  }

  public byte[] getOriginalDocument() {
    return originalDocument;
  }

  public void setOriginalDocument(final byte[] originalDocument) {
    this.originalDocument = originalDocument;
  }

  public byte[] getSignedDocument() {
    return signedDocument;
  }

  public void setSignedDocument(final byte[] signedDocument) {
    this.signedDocument = signedDocument;
  }

  public byte[] getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(final byte[] publicKey) {
    this.publicKey = publicKey;
  }
}
