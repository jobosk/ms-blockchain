package com.alfatecsistemas.ms.blockchain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.alfatecsistemas.ms.blockchain.Constants;
import com.alfatecsistemas.ms.blockchain.contract.Patient;

import java.math.BigInteger;
import java.net.ConnectException;
import java.util.Arrays;

@Service
public class PatientServiceImpl implements PatientService {

  private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

  private static final String blockchainNetwork = "rinkeby.infura.io";
  private static final String blockchainProject = "d434e755cd164e8f8c4fca9545e63178";

  // Ubicacion de la wallet fisica
  private static final String walletFilePath =
      "./src/main/resources/UTC--2019-02-01T17-01-57.104Z--ab67dc2a9e0467131ae92562f0fd21e7c85dac05";

  // Contraseña de la wallet fisica
  private static final String walletPassword = "prueba123";

  // Direccion de la wallet fisica
  private static final String walletAddress = "0xab67dc2a9e0467131ae92562f0fd21e7c85dac05";

  /**
   * Recupera la URL al nodo de la blockchain, mediante el
   * protocolo especificado.
   * Ejemplos:
   * https://rinkeby.infura.io/v3/d434e755cd164e8f8c4fca9545e63178
   * wss://rinkeby.infura.io/ws/v3/d434e755cd164e8f8c4fca9545e63178
   *
   * @param ws
   * @return
   */
  private String getNodeUrl(final boolean ws) {
    final StringBuffer buffer = new StringBuffer();
    buffer.append(ws ? Constants.Url.PREFIX_WS : Constants.Url.PREFIX_HTTP);
    buffer.append(blockchainNetwork);
    if (ws) {
      buffer.append("/ws");
    }
    buffer.append("/v3/");
    buffer.append(blockchainProject);
    return buffer.toString();
  }

  private Web3j getWeb3j(final boolean ws) {
    Web3j web3j = ws ? getWeb3jWs() : null;
    if (web3j == null) {
      web3j = getWeb3jHttp();
    }
    return web3j;
  }

  private Web3j getWeb3jHttp() {
    return Web3j.build(new HttpService(getNodeUrl(false)));
  }

  private Web3j getWeb3jWs() {
    Web3j web3j;
    try {
      final WebSocketService web3jService = new WebSocketService(getNodeUrl(true), true);
      web3jService.connect();
      web3j = Web3j.build(web3jService);
    } catch (final ConnectException ce) {
      log.error("", ce);
      web3j = null;
    }
    return web3j;
  }

  public String getCurrentBalance() {
    String balance;
    try {
      final EthGetBalance ethGetBalance =
          getWeb3j(false).ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
      balance = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER).toString();
    } catch (final Exception e) {
      log.error("", e);
      balance = null;
    }
    return balance;
  }

  public String getHexPrivateKey() {
    String result;
    try {
      final Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletFilePath);
      result = credentials.getEcKeyPair().getPrivateKey().toString(16);
    } catch (final Exception e) {
      log.error("", e);
      result = null;
    }
    return result;
  }

  public BigInteger[] getCoordsPublicKey() {
    BigInteger[] result;
    try {
      final Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletFilePath);
      result = createECPoint(credentials.getEcKeyPair().getPublicKey());
    } catch (final Exception e) {
      log.error("", e);
      result = null;
    }
    return result;
  }

  private static BigInteger[] createECPoint(final BigInteger publicKey) {
    final byte[] bytes = Numeric.toBytesPadded(publicKey, 64);
    final BigInteger x = Numeric.toBigInt(Arrays.copyOfRange(bytes, 0, 32));
    final BigInteger y = Numeric.toBigInt(Arrays.copyOfRange(bytes, 32, 64));
    return new BigInteger[] {x, y};
  }

  private Patient getPatient(final String contractAddress) {
    Patient contract;
    try {
      final Web3j web3j = getWeb3j(false);
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

  private static BigInteger getValue(final RemoteCall<BigInteger> remoteCall) {
    BigInteger value;
    try {
      value = remoteCall.send();
    } catch (final Exception e) {
      log.error("", e);
      value = null;
    }
    return value;
  }

  public BigInteger getPatientInfo(final String patientAddress) {
    final Patient patient = getPatient(patientAddress);
    return patient != null ? getValue(patient.getPatientId()) : null;
  }
}
