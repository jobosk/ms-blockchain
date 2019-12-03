package com.alfatecsistemas.ms.blockchain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import com.alfatecsistemas.ms.blockchain.contract.Patient;
import com.alfatecsistemas.ms.blockchain.feign.SignerFeign;
import com.alfatecsistemas.ms.common.Constants.Algorithm;
import com.alfatecsistemas.ms.common.dto.KeyDto;
import com.alfatecsistemas.ms.common.util.CryptoUtil;
import com.alfatecsistemas.ms.common.util.EncodingUtil;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Arrays;

@Service
public class PatientServiceImpl implements PatientService {

  private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

  @Autowired
  private BlockchainService blockchainService;

  @Autowired
  private SignerFeign signerClient;

  // Ubicacion de la wallet fisica
  private static final String walletFilePath =
      "./src/main/resources/UTC--2019-02-01T17-01-57.104Z--ab67dc2a9e0467131ae92562f0fd21e7c85dac05";

  // Contraseña de la wallet fisica
  private static final String walletPassword = "prueba123";

  // Direccion de la wallet fisica
  private static final String walletAddress = "0xab67dc2a9e0467131ae92562f0fd21e7c85dac05";

  public String getCurrentBalance() {
    String balance;
    try {
      final EthGetBalance ethGetBalance =
          blockchainService.getWeb3j(false).ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync()
              .get();
      balance = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER).toString();
    } catch (final Exception e) {
      log.error("", e);
      balance = null;
    }
    return balance;
  }

  private static ECKeyPair getKeyPair() {
    ECKeyPair result;
    try {
      final Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletFilePath);
      result = credentials.getEcKeyPair();
    } catch (final Exception e) {
      log.error("", e);
      result = null;
    }
    return result;
  }

  private static byte[] encryptPrivateKey(final byte[] privateKey, final KeyDto signerPublicKeyDto,
      final String algorithm) {
    byte[] result;
    try {
      final PublicKey signerPublicKey = CryptoUtil.buildPublicKey(
          EncodingUtil.decodeBase64(signerPublicKeyDto.getKey())
          , signerPublicKeyDto.getAlgorithm()
      );
      result = CryptoUtil.encrypt(
          privateKey
          , signerPublicKey
          , algorithm
      );
    } catch (final Exception e) {
      log.error("", e);
      result = null;
    }
    return result;
  }

  public KeyDto getPrivateKey() {
    return new KeyDto(
        EncodingUtil.encodeBase64(
            encryptPrivateKey(
                getKeyPair().getPrivateKey().toByteArray()
                , signerClient.getPublicKey()
                , Algorithm.ENCRYPT
            )
        )
        , Algorithm.EC
        , Algorithm.ENCRYPT
        , Algorithm.EC_CURVE_NAME
    );
  }

  public KeyDto getPublicKey() {
    return new KeyDto(
        EncodingUtil.encodeBase64(getKeyPair().getPublicKey().toByteArray())
        , Algorithm.EC
        , null
        , Algorithm.EC_CURVE_NAME
    );
  }

  private Patient getPatient(final String contractAddress) {
    Patient contract;
    try {
      final Web3j web3j = blockchainService.getWeb3j(false);
      final Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletFilePath);
      if (contractAddress != null) {
        contract = Patient.load(contractAddress, web3j, credentials, new DefaultGasProvider());
      } else {
        contract = Patient.deploy(web3j, credentials, new DefaultGasProvider()).send();
        log.info("Smart contract deployed to address " + contract.getContractAddress());
        log.info("View contract at https://rinkeby.etherscan.io/address/<contract_address>");
      }
    } catch (final Exception e) {
      log.error("", e);
      contract = null;
    }
    return contract;
  }

  public String createPatient() {
    final Patient patient = getPatient(null);
    return patient != null ? patient.getContractAddress() : null;
  }

  /*
  public BigInteger getPatientInfo(final String patientAddress) {
    final Patient patient = getPatient(patientAddress);
    return patient != null ? getValue(patient.getPatientId()) : null;
  }
  */

  public BigInteger getPatientInfo(final String patientAddress) {
    return blockchainService.executeGetMethod(
        walletAddress
        , patientAddress
        , "getPatientId"
        , Arrays.asList()
        , Arrays.asList(blockchainService.<Uint256>getReturnType())
        , BigInteger.class
    );
  }
}
