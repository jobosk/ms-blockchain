package com.alfatecsistemas.ms.blockchain.service;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;

import java.util.List;

public interface BlockchainService {

  Web3j getWeb3j(boolean ws);

  //String executeUpdateTransaction(String hexValue) throws IOException;

  //String executeRequestTransaction(String hexValue) throws IOException;

  <T extends Type> TypeReference<T> getReturnType(final Class<T> type);

  <R> R executeGetMethod(final String from, final String to, final String methodName,
      final List<Type> inputParameters, final List<TypeReference<?>> outputParameters, final Class<R> returnType);
}
