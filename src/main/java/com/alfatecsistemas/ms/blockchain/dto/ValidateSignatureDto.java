package com.alfatecsistemas.ms.blockchain.dto;

public class ValidateSignatureDto {

  private byte[] originalDocument;
  private byte[] signedDocument;
  private String publicKey;
  private String publicKeyAlgorithm;

  public ValidateSignatureDto(final byte[] originalDocument, final byte[] signedDocument, final String publicKey,
      final String publicKeyAlgorithm) {
    this.originalDocument = originalDocument;
    this.signedDocument = signedDocument;
    this.publicKey = publicKey;
    this.publicKeyAlgorithm = publicKeyAlgorithm;
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
}
