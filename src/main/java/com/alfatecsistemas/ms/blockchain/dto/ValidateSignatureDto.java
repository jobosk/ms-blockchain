package com.alfatecsistemas.ms.blockchain.dto;

public class ValidateSignatureDto {

  private byte[] originalDocument;
  private byte[] signedDocument;
  private String hexPublicKey;

  public ValidateSignatureDto(final byte[] originalDocument, final byte[] signedDocument, final String hexPublicKey) {
    this.originalDocument = originalDocument;
    this.signedDocument = signedDocument;
    this.hexPublicKey = hexPublicKey;
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

  public String getHexPublicKey() {
    return hexPublicKey;
  }

  public void setHexPublicKey(final String hexPublicKey) {
    this.hexPublicKey = hexPublicKey;
  }
}
