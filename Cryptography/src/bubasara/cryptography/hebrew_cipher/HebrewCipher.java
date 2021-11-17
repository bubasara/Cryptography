package bubasara.cryptography.hebrew_cipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HebrewCipher {
	
	List<Character>alphabet; //regular alphabet A B C ... Z
	List<Character> cipher_alphabet; //cipher alphabet Z Y X ... A
	List<Integer> cipher_alphabet_int; //cipher alphabet 25 24 23 ... 0
	String cipher; //result to be returned from encoding
	String open_text; //result to be returned from decoding

	
	public HebrewCipher() {
		this.alphabet = new ArrayList<Character>(26);
		for(char ch='a'; ch<='z'; ++ch ) {
			this.alphabet.add(ch);
		}
		
		this.cipher_alphabet = new ArrayList<Character>(26);
		for(char ch='z'; ch >='a'; --ch ) {
			this.cipher_alphabet.add(ch);
		}
		
		this.cipher_alphabet_int = new ArrayList<Integer>(26);
		for(int i=25; i >=0; --i ) {
			this.cipher_alphabet_int.add(i);
		}
		
		this.cipher = new String();
		this.open_text = new String();
	}

	public static void main(String[] args) {
		HebrewCipher hebrew_cipher = new HebrewCipher();
		String result = "";
		Scanner in = new Scanner(System.in);
		System.out.println("What do you want to do?\n1 - encode\n2 - decode\n3 - encode numeric\n4 - decode numeric");
		Integer choice = in.nextInt();
		while (choice != 1 && choice !=2 && choice !=3 && choice !=4) {
			System.out.println("Please type only 1 or 2 or 3 or 4: ");
			choice = in.nextInt();
		}
		if(choice == 1) {
			System.out.println("Write a sentence you want to be encoded: ");
			String open_text = in.next().toString();
			result = hebrew_cipher.encode(open_text);
		} else if(choice == 2){
			System.out.println("Write a sentence you want to be decoded: ");
			String cipher = in.next().toString();
			result = hebrew_cipher.decode(cipher);
		}
		if(choice == 3) {
			System.out.println("Write a sentence you want to be encoded: ");
			String open_text = in.next().toString();
			result = hebrew_cipher.encodeNumeric(open_text);
		}
		if(choice == 4) {
			System.out.println("Write a sentence you want to be decoded (type any letter to stop input): ");
			ArrayList<Integer> cipher = new ArrayList<Integer>();
			while(in.hasNextInt()) {
				int num = in.nextInt();
				//only numbers 0-25 are binded to Z-A
				if(num<0 || num>25)
					continue;
				cipher.add(num);
			}
			result = hebrew_cipher.decodeNumeric(cipher);
		}
		System.out.println("The result is: " + result);
		in.close();
	}
	
	public String encode(String open_text) {
		char[] open_text_chars = open_text.toLowerCase().toCharArray();
		for (char ch : open_text_chars) {
			//int index = alphabet.indexOf(ch); - finding index of the letter in regular alphabet
			//cipher_alphabet.get(index) - search for letter with that index in cipher alphabet
			this.cipher += cipher_alphabet.get(alphabet.indexOf(ch));
		}
		return this.cipher;
	}
	
	public String decode(String cipher) {
		char[] cipher_chars = cipher.toLowerCase().toCharArray();
		for (char ch : cipher_chars) {
			//vice versa to encoding
			this.open_text  += alphabet.get(cipher_alphabet.indexOf(ch));
		}
		return this.open_text;
	}
	
	public String encodeNumeric(String open_text) {
		char[] open_text_chars = open_text.toLowerCase().toCharArray();
		//numeric encoding is encoding to a numeric equivalent
		//which is index of the letter in regular alphabet
		for (char ch : open_text_chars) {
			this.cipher += 25 - alphabet.indexOf(ch);
			this.cipher += ' ';
		}
		return this.cipher;
	}
	
	public String decodeNumeric(ArrayList<Integer> cipher) {
		//each number is equivalent to a letter in cipher alphabet
		for (Integer i : cipher) {
			this.open_text += this.cipher_alphabet.get(i);
		}
		return this.open_text;
	}
}
