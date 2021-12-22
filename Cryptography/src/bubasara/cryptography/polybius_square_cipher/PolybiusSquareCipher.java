package bubasara.cryptography.polybius_square_cipher;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
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
		//each letter is represented by two digits ij
		//in a Polybius Sqare
		//where i = row, j = column
		//of the position of that letter
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
	
	public String decode(String cipher) {
		//each pair of digits represents the position
		//of the letter in a Polybius Sqare
        String[] cipher_two_chars = cipher.split("(?<=\\G.{2})");
        int num;
        int i;
        int j;
        for (String s : cipher_two_chars) {
        	 try{
                 num = Integer.parseInt(s);
                 i = num/10;
                 j = num%10;
                 open_text += this.square[i][j];
             }
             catch (NumberFormatException e){
                 e.printStackTrace();
             }
		}
        return this.open_text;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Secret keyword: ");
		String keyword = in.next().toString();
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
		
		PolybiusSquareCipher polybius = new PolybiusSquareCipher(keyword);
		System.out.println();
		polybius.print();
		if(choice == 1)
			System.out.println("Cipher: " + polybius.encode(text));
		else if(choice == 2)
			System.out.println("Open text: " + polybius.decode(text));
		in.close();
	}
}
