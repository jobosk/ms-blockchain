package com.alfatecsistemas.ms.blockchain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.exceptions.ContractCallException;

import com.alfatecsistemas.ms.common.Constants;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class BlockchainServiceImpl implements BlockchainService {

  private static final Logger log = LoggerFactory.getLogger(BlockchainServiceImpl.class);

  private static final String blockchainNetwork = "rinkeby.infura.io";
  private static final String blockchainProject = "d434e755cd164e8f8c4fca9545e63178";

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

  public Web3j getWeb3j(final boolean ws) {
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

  /*
  public String executeUpdateTransaction(final String hexValue) {
    String result;
    try {
      result = sendAsyncTransaction(hexValue);
    } catch (final Exception e) {
      log.error("", e);
      result = null;
    }
    return result;
  }

  private String sendAsyncTransaction(final String hexValue) throws IOException {
    return getWeb3j(false).ethSendRawTransaction(hexValue).send().getTransactionHash();
  }
  */

  public <T extends Type, R> R executeGetMethod(final String from, final String to, final String methodName,
      final List<Type> inputParameters, final List<Class<T>> outputParameters, final Class<R> returnType) {
    final Function function = new Function(methodName, inputParameters, formatReturnTypes(outputParameters));
    return callMethod(() -> getFunctionResult(from, to, function, returnType));
  }

  private static <T extends Type> List<TypeReference<?>> formatReturnTypes(final List<Class<T>> list) {
    return list.stream().map(TypeReference::create).collect(Collectors.toList());
  }

  private static <R> R callMethod(final Callable<R> callable) {
    R value;
    try {
      value = new RemoteCall<>(callable).send();
    } catch (final Exception e) {
      log.error("", e);
      value = null;
    }
    return value;
  }

  private <R> R getFunctionResult(final String from, final String to, final Function function,
      final Class<R> returnType) {
    final Type result = getFirstElement(executeFunction(from, to, function));
    if (result == null) {
      throw new ContractCallException("Empty value (0x) returned from contract");
    }
    final Object value = result.getValue();
    if (returnType.isAssignableFrom(value.getClass())) {
      return (R) value;
    } else if (result.getClass().equals(Address.class) && returnType.equals(String.class)) {
      return (R) result.toString();
    } else {
      throw new ContractCallException("Cannot convert response: " + value + " to type: " + returnType.getSimpleName());
    }
  }

  private static Type getFirstElement(final List<Type> list) {
    return list != null && !list.isEmpty() ? list.get(0) : null;
  }

  private List<Type> executeFunction(final String from, final String to, final Function function) {
    final String encodedFunction = FunctionEncoder.encode(function);
    final Transaction transaction = Transaction.createEthCallTransaction(from, to, encodedFunction);
    return executeTransaction(transaction, function.getOutputParameters(), DefaultBlockParameterName.LATEST);
  }

  private List<Type> executeTransaction(final Transaction transaction, final List<TypeReference<Type>> outputParameters,
      final DefaultBlockParameter defaultBlockParameter) {
    final Web3j web3j = getWeb3j(false);
    String result;
    try {
      final EthCall ethCall = web3j.ethCall(transaction, defaultBlockParameter).send();
      result = ethCall.getValue();
    } catch (final IOException ioe) {
      log.error("", ioe);
      result = null;
    }
    return FunctionReturnDecoder.decode(result, outputParameters);
  }
}
