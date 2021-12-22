package bubasara.cryptography.polybius_square_cipher;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PolybiusSquareCipher {
	char[][] square;
	public PolybiusSquareCipher(String keyword) {
		square = new char[5][5];
		//lets first make an alphabet
		//set is used because only unique letters are allowed
		Set<Character> alphabet = new LinkedHashSet<Character>(26);
		//adding a secret keyword to the front of the alphabet
		char[] keyword_chars = keyword.toLowerCase().toCharArray();
		for (Character ch : keyword_chars) {
			//validation: only letters a-z allowed
			//ignore other characters
			if(ch<97 || ch>122)
				continue;
			//alphabet has 26 letters, but Polybius square has 25
			//'i' & 'j' share a cell in matrix
			if(ch=='j')
				ch = 'i';
			alphabet.add(ch);
		}
		//adding other letters to the alphabet
		for(char ch='a'; ch<='z'; ++ch ) {
			if(ch=='j')
				alphabet.add('i');
			alphabet.add(ch);
		}
		//making a list out of this set, because we need indexes
		List<Character> cipher_alphabet = new ArrayList<Character>(alphabet);
		int counter = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				square[i][j] = cipher_alphabet.get(counter++);
			}
		}
	}
	
	//print matrix
	public void print() {
		for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                System.out.print(square[i][j] + " ");
            System.out.println();
		}
	}

	public static void main(String[] args) {
		PolybiusSquareCipher cipher = new PolybiusSquareCipher("kvadrat");
		cipher.print();

	}

}
