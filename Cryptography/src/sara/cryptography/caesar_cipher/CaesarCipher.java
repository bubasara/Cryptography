package sara.cryptography.caesar_cipher;

import java.util.Scanner;

public class CaesarCipher {

	public static void main(String[] args) {
		String result = "";
		Scanner in = new Scanner(System.in);
		System.out.println("Type 1 if you want to encode OR 2 if you want to decode a text:");
		Integer choice = in.nextInt();
		while (choice != 1 && choice !=2) {
			System.out.println("Please type only 1 or 2: ");
			choice = in.nextInt();
		}
		if(choice == 1) {
			System.out.println("Write a sentence you want to be encoded: ");
			String open_text = in.next().toString();
			System.out.println("and a key: ");
			int key = in.nextInt();
			while (key < 0) {
				System.out.println("Key cannot be < 0. Key: ");
				key = in.nextInt();
			}
			result = encode(key, open_text);
		} else if(choice == 2){
			System.out.println("Write a sentence you want to be decoded: ");
			String chiper = in.next().toString();
			System.out.println("and a key: ");
			int key = in.nextInt();
			while (key < 0) {
				System.out.println("Key cannot be < 0. Key: ");
				key = in.nextInt();
			}
			result = decode(26 - key, chiper);
		}
		System.out.println("The result is: " + result);
		in.close();
		
	}

	public static String encode(int key, String open_text) {
		String cipher = "";
		char[] open_text_chars = open_text.toCharArray();
		key = key % 26; //to prevent looping more than once
		for (char ch : open_text_chars) {
			ch = Character.toLowerCase(ch);
			//finding current position of ch: ch - 'a'
			//position after shifting (ch - 'a + key) % 26
			//new ch is: 'a' + the result
			cipher += (char)('a' + (ch - 'a' + key) % 26);
		}
		return cipher;
	}
	
	public static String decode(int key, String cipher) {
		String open_text = "";
		char[] cipher_chars = cipher.toCharArray();
		for (char ch : cipher_chars) {
			int shift = ch - key;
			if(shift < 0)
				shift -= 26;
			open_text += (char)('a' + (ch - 'a' + key) % 26);
		}		
		return open_text;
	}

}
