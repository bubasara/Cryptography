package bubasara.cryptography.vigenere_cipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VigenereCipher {

	List<Character>alphabet; //regular alphabet A B C ... Z
	String cipher; //result to be returned from encoding
	String open_text; //result to be returned from decoding
	String keyword; //keyword used for encoding/decoding
	
	public VigenereCipher() {
		this.alphabet = new ArrayList<Character>(26);
		for(char ch='a'; ch<='z'; ++ch ) {
			this.alphabet.add(ch);
		}
		
		this.cipher = new String();
		this.open_text = new String();
		this.keyword = new String();
	}
	
	public String encode(String open_text, String keyword) {
		char[] open_text_chars = open_text.toLowerCase().toCharArray();
		ArrayList<Character> keyword_chars = new ArrayList<Character>();
		for (char c : keyword.toLowerCase().toCharArray()) {
			keyword_chars.add(c);
		}
		int ot_num; //open_text char numeric equivalent
		int key_num; //keyword char numeric equivalent
		int counter = 0; //position of current char in the keyword
		//Vigenere cipher algorithm: (ot_num + key_num) % 26
		int ciph_num = 0; //cipher char numeric equivalent
		for (char ch : open_text_chars) {
			ot_num = alphabet.indexOf(ch);
			if(counter >= keyword_chars.size())
				counter = 0;
			key_num = alphabet.indexOf(keyword_chars.get(counter++));
			ciph_num = (ot_num + key_num) % 26;
			cipher += alphabet.get(ciph_num);
		}
		return this.cipher;
	}
	
	public String decode(String cipher, String keyword) {
		char[] cipher_chars = cipher.toLowerCase().toCharArray();
		ArrayList<Character> keyword_chars = new ArrayList<Character>();
		for (char c : keyword.toLowerCase().toCharArray()) {
			keyword_chars.add(c);
		}
		int ot_num; //open_text char numeric equivalent
		int key_num; //keyword char numeric equivalent
		int counter = 0; //position of current char in the keyword
		int ciph_num = 0; //cipher char numeric equivalent
		for (char ch : cipher_chars) {
			ciph_num = alphabet.indexOf(ch);
			if(counter >= keyword_chars.size())
				counter = 0;
			key_num = alphabet.indexOf(keyword_chars.get(counter++));
			//if cipher char numeric equivalent - keyword char numeric equivalent < 0
			//that means modulo has been applied, so we need to add 26
			ot_num = (ciph_num - key_num) < 0 ? ciph_num - key_num + 26 : ciph_num - key_num;
			open_text += alphabet.get(ot_num);
		}
		return this.open_text;
	}

	public static void main(String[] args) {
		VigenereCipher vigenere_cipher = new VigenereCipher();
		String result = "";
		Scanner in = new Scanner(System.in);
		System.out.println("Type 1 if you want to encode OR 2 if you want to decode a text:");
		Integer choice = in.nextInt();
		while (choice != 1 && choice !=2) {
			System.out.println("Please type only 1 or 2: ");
			choice = in.nextInt();
		}
		if(choice == 1) {
			System.out.println("Write a text you want to be encoded\n(only letters will be taken into account): ");
		} else if(choice == 2){
			System.out.println("Write a text you want to be decoded\n(only letters will be taken into account): ");
		}
		String text = in.next().toString();
		System.out.println("Secret keyword: ");
		String keyword = in.next().toString();
		if(choice == 1)
			result = vigenere_cipher.encode(text, keyword);
		else if(choice == 2)
			result = vigenere_cipher.decode(text, keyword);
		System.out.println("The result is: " + result);
		in.close();
	}
}
