package com.alfatecsistemas.ms.blockchain.dto;

public class ValidateSignatureDto {

  private byte[] signedDocument;
  private byte[] publicKey;

  public ValidateSignatureDto(final byte[] signedDocument, final byte[] publicKey) {
    this.signedDocument = signedDocument;
    this.publicKey = publicKey;
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
