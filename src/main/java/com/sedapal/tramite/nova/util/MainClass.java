package com.sedapal.tramite.nova.util;

import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainClass {
  public static void main(String args[]) throws Exception {
	  String claro = "12345678";
	  
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    //SecretKey key = KeyGenerator.getInstance("DES").generateKey();
    byte keyCte[] = "abcdEFGH".getBytes(); 
    SecretKeySpec key = new SecretKeySpec(keyCte,"DES");

    // for CBC; must be 8 bytes
    byte[] initVector = new byte[] { 0x10, 0x10, 0x01, 0x04, 0x01, 0x01, 0x01, 0x02 };

    AlgorithmParameterSpec algParamSpec = new IvParameterSpec(initVector);
    Cipher m_encrypter = Cipher.getInstance("DES/CBC/PKCS5Padding");
    Cipher m_decrypter = Cipher.getInstance("DES/CBC/PKCS5Padding");

    m_encrypter.init(Cipher.ENCRYPT_MODE, key, algParamSpec);
    m_decrypter.init(Cipher.DECRYPT_MODE, key, algParamSpec);

    byte[] clearText = claro.getBytes();

    byte[] encryptedText = m_encrypter.doFinal(clearText);

    byte[] decryptedText = m_decrypter.doFinal(encryptedText);
    
    String sClearText = new String(clearText);
    String sEncryptedText = new String(encryptedText);
    String sDecryptedText = new String(decryptedText);

    System.out.println(sClearText);
    System.out.println(sEncryptedText);
    System.out.println(sDecryptedText);
    
  }

}