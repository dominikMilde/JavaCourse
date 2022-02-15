package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.oprpp1.hw05.crypto.Util;


/**
 * @author Dominik
 * Implementation of own sha and en/decoding object
 *
 */
public class Crypto {
	/**
	 * buffer size in kB
	 */
	public static final int SIZE = 4;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		if(args.length < 2) {
			System.out.println("At least two arguments must be provided!");
		}
		
		if(args[0].equals("checksha")) {
			if(args.length > 2) {
				System.out.println("EXCATLY two parameters must be provided for checksha action!");
				System.exit(0);
			}
			System.out.println("Please provide expected sha-256 digest for hw05test.bin:\n> ");
			String expected = scanner.nextLine();
			try {
				checksha(args[1], expected);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if(args[0].equals("encrypt") || args[0].equals("decrypt")) {
			if(args.length != 3) {
				System.out.println("EXCATLY two parameters must be provided for this action!");
				System.exit(0);
			}
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
			String password = scanner.nextLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
			String vector = scanner.nextLine();
			boolean encryptTrue = (args[0].equals("encrypt") ? true : false);
			
			try {
				doCryption(password, vector, args[1], args[2], encryptTrue);
			} 
			catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
			
		}
		else {
			System.out.println("greska u ulazu");
		}
	}

	
	/**
	 * Method that provides encryption and decryption
	 * @param password password for algorithm
	 * @param vector init vector for algorithm
	 * @param src source file
	 * @param dest destination file
	 * @param encryptTrue if true do encryption else do decryption
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void doCryption(String password, String vector, String src, String dest, boolean encryptTrue) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
		//Path source = Paths.get(src);
		//Path destination = Paths.get(dest);
		
		byte[] buffer = new byte[SIZE*1024];
		InputStream is = new FileInputStream(src);
		OutputStream os = new FileOutputStream(dest);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		SecretKeySpec key = new SecretKeySpec(Util.hextobyte(password), "AES");
		AlgorithmParameterSpec aps = new IvParameterSpec(Util.hextobyte(vector));
		
		cipher.init(encryptTrue ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key, aps);
		
		try(BufferedInputStream bis = new BufferedInputStream(is);
				BufferedOutputStream bos = new BufferedOutputStream(os)){
			
			while (true) {
				int len = bis.read(buffer);
				
				if(len<1) {
					bos.write(cipher.doFinal());
					break;
				};
				
				bos.write(cipher.update(buffer, 0, len));
			}
			System.out.format("%s completed. Generated file %s based on file %s.", (encryptTrue ? "Encryption" : "Decryption"), dest, src);
 		}	
	}

	/**
	 * method that provides calculation of 
	 * @param stringPath path to file we want to calculate sha for
	 * @param expectedValue value user inputs
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static void checksha(String stringPath, String expectedValue) throws NoSuchAlgorithmException, IOException {
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		
		InputStream is = new FileInputStream(stringPath);
		byte[] buffer = new byte[SIZE*1024];
		
		try(BufferedInputStream bis = new BufferedInputStream(is)){
			while(true) {
				int len = bis.read(buffer);
				if(len < 1) break;
				digest.update(buffer, 0, len);
			}
			byte[] checksumInBytes = digest.digest();
			String calculatedChecksum = Util.bytetohex(checksumInBytes);
			
			if(expectedValue.equals(calculatedChecksum)) {
				System.out.println("Digesting completed. Digest of hw05test.bin matches expected digest.");
			}
			else {
				System.out.format("Digesting completed. Digest of hw05test.bin does not match the expected digest. Digest was: %s", calculatedChecksum);
			}
 		}
	}
}
