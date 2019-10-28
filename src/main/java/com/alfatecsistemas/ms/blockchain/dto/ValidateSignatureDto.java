package com.alfatecsistemas.ms.blockchain.dto;

import java.math.BigInteger;

public class ValidateSignatureDto {

  private byte[] originalDocument;
  private byte[] signedDocument;
  private BigInteger[] coordsPublicKey;

  public ValidateSignatureDto(final byte[] originalDocument, final byte[] signedDocument,
      final BigInteger[] coordsPublicKey) {
    this.originalDocument = originalDocument;
    this.signedDocument = signedDocument;
    this.coordsPublicKey = coordsPublicKey;
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

  public BigInteger[] getCoordsPublicKey() {
    return coordsPublicKey;
  }

  public void setCoordsPublicKey(final BigInteger[] coordsPublicKey) {
    this.coordsPublicKey = coordsPublicKey;
  }
}
