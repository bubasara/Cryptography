package bubasara.cryptography.polybius_square_cipher;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PolybiusSquareCipher {
	String cipher = ""; //result to be returned from encoding
	String open_text = ""; //result to be returned from decoding
	String keyword = ""; //keyword used for encoding/decoding
	char[][] square; //polybius square
	public PolybiusSquareCipher(String keyword) {
		this.keyword = keyword;
		this.square = new char[6][6];
		//lets first make an alphabet
		//set is used because only unique letters are allowed
		Set<Character> alphabet = new LinkedHashSet<Character>(26);
		//adding a secret keyword to the front of the alphabet
		char[] keyword_chars = this.keyword.toLowerCase().toCharArray();
		for (Character ch : keyword_chars) {
			//validation: only letters a-z allowed
			//ignore other characters
			if(ch<97 || ch>122)
				continue;
			//alphabet has 26 letters, but Polybius square has 25
			//'i' & 'j' share a cell in matrix
			if(ch=='j') {
				alphabet.add('i');
			} else {
				alphabet.add(ch);
			}
		}
		//adding other letters to the alphabet
		for(char ch='a'; ch<='z'; ++ch ) {
			if(ch=='j') {
				alphabet.add('i');
			} else {
				alphabet.add(ch);
			}
		}
		//making a list out of this set, because we need indexes
		List<Character> cipher_alphabet = new ArrayList<Character>(alphabet);
		int counter = 0;
		for (int i = 1; i < 6; i++) {
			for (int j = 1; j < 6; j++) {
				this.square[i][j] = cipher_alphabet.get(counter++);
			}
		}
	}
	
	//print matrix
	public void print() {
		System.out.println("Polybius Sqare:\n");
		for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                System.out.print(square[i][j] + " ");
            }
            System.out.println();
		}
		System.out.println();
	}
	
	public String encode(String open_text) {
		char[] open_text_chars = open_text.toLowerCase().toCharArray();
		for (char ch : open_text_chars) {
			if(ch=='j')
				ch='i';
			for (int i = 1; i < 6; i++) {
	            for (int j = 1; j < 6; j++) {
	            	if (this.square[i][j] == ch) {
	            		this.cipher += i;
	            		this.cipher += j;
	            		break;
	            	}
	            }
			}
		}
		return this.cipher;
	}
	
	public void decode(String cipher) {
		//todo
	}
	
	public static void main(String[] args) {
		PolybiusSquareCipher cipher = new PolybiusSquareCipher("");
		cipher.print();
		System.out.println("Cipher: " + cipher.encode("kriptografija"));
	}
}
