package hr.fer.oprpp1.hw05.crypto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Dominik
 * Class that helps with conversions
 */
public class Util {

	/**
	 * Converts array of bytes to string representation
	 * @param bytes array of bytes we want to convert to String value
	 * @return string representation
	 */
	public static String bytetohex(byte[] bytes) {
		String out = "";
		for(byte bajt : bytes) {
			out += String.format("%02x", bajt);
		}
		return out;
	}

	/**
	 * Converts string representation to byte array
	 * @param key string representation
	 * @return byte array
	 */
	public static byte[] hextobyte(String key) {
		if(key.length() % 2 == 1) throw new IllegalArgumentException("Key must be of even size!");
		
		Collection<Character> valid = new ArrayList<>();
		
		valid.add('0');
		valid.add('1');
		valid.add('2');
		valid.add('3');
		valid.add('4');
		valid.add('5');
		valid.add('6');
		valid.add('7');
		valid.add('8');
		valid.add('9');
		valid.add('a');
		valid.add('b');
		valid.add('c');
		valid.add('d');
		valid.add('e');
		valid.add('f');
		valid.add('A');
		valid.add('B');
		valid.add('C');
		valid.add('D');
		valid.add('E');
		valid.add('F');
		
		for(int i=0; i<key.length(); i++) {
			if(!valid.contains(key.charAt(i))) {
				throw new IllegalArgumentException("");
			}
		}
		if (key.length() == 0) {
			return new byte[0];
		} 
		
		byte[] out = new byte[key.length() / 2];
		
		for(int i=0; i<key.length(); i=i+2) {
			byte value =  (byte) ((Character.digit(key.charAt(i), 16) * 16));
			value += (byte) (Character.digit(key.charAt(i+1), 16));
			
			out[i/2] = value; 
		}
		return out;
	}

}
