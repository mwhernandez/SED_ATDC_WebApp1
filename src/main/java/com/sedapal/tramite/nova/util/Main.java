package com.sedapal.tramite.nova.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		    // Generate a temporary key. In practice, you would save this key.
		    // See also Encrypting with DES Using a Pass Phrase.
		    SecretKey key = KeyGenerator.getInstance("DES").generateKey();

		    // Create encrypter/decrypter class
		    //DesEncrypter encrypter = new DesEncrypter(key);

		    // Encrypt
		     //String encrypted = encrypter.encrypt("120120");
           // System.out.println(encrypted);
		    // Decrypt
		    //String decrypted = encrypter.decrypt(encrypted);
		    //System.out.println(decrypted);
		} catch (Exception e) {
		}


	}

}
