package com.alfatecsistemas.ms.blockchain.dto;

public class KeyDto {

  private String key;
  private String algorithm;
  private String encryption;
  private String curveName;

  public KeyDto() {
    // Empty constructor
  }

  public KeyDto(final String key, final String algorithm) {
    this(key, algorithm, null, null);
  }

  public KeyDto(final String key, final String algorithm, final String encryption, final String curveName) {
    this.key = key;
    this.algorithm = algorithm;
    this.encryption = encryption;
    this.curveName = curveName;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(final String algorithm) {
    this.algorithm = algorithm;
  }

  public String getEncryption() {
    return encryption;
  }

  public void setEncryption(final String encryption) {
    this.encryption = encryption;
  }

  public String getCurveName() {
    return curveName;
  }

  public void setCurveName(final String curveName) {
    this.curveName = curveName;
  }
}
