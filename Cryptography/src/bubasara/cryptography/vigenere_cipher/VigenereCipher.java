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
			System.out.println("ch: " + ch + ", ot_num: " + ot_num);
			if(counter >= keyword_chars.size())
				counter = 0;
			System.out.println("counter: " + counter);
			key_num = alphabet.indexOf(keyword_chars.get(counter++));
			System.out.println("key_num: " + key_num);
			ciph_num = (ot_num + key_num) % 26;
			cipher += alphabet.get(ciph_num);
			System.out.println("ciph_num: " + ciph_num + ", alphabet.get(ciph_num) " + alphabet.get(ciph_num));
		}
		return this.cipher;
	}

	public static void main(String[] args) {
		VigenereCipher vigenere_cipher = new VigenereCipher();
		String result = "";
		Scanner in = new Scanner(System.in);
		System.out.println("Write a text you want to be encoded\n(only letters will be taken into account): ");
		String open_text = in.nextLine();
		System.out.println("Secret keyword: ");
		String keyword = in.nextLine();
		result = vigenere_cipher.encode(open_text, keyword);
		System.out.println("The result is: " + result);
		in.close();
	}

}
