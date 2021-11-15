package sara.cryptography.caesar_cipher;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class KeyedCaesarCipher {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Write a text you want to be encoded\n(only letters will be taken into account): ");
		String open_text = in.nextLine();
		System.out.println("Secret keyword: ");
		String keyword = in.nextLine();
		System.out.println("Key: ");
		Integer key = 0;
		String input = in.next();
		//validation
		Boolean valid = false;
		while (!valid){
			try {
				key = Integer.parseInt(input);
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Key must be an integer. Try again:");
				input = in.next();
			}
		}
		in.close();
		
		encode(open_text, keyword, key);
	}
	
	public static void encode(String open_text,String keyword, int key) {
		String cipher = new String();
		
		//lets first make an alphabet
		//set is used because only unique letters are allowed
		LinkedHashSet<Character>alphabet = new LinkedHashSet<Character>(26);

		//adding a secret keyword to the front of the alphabet
		char[] keyword_chars = keyword.toLowerCase().toCharArray();
		for (char ch : keyword_chars) {
			//validation: only letters a-z allowed
			//ignore other characters
			if(ch<97 || ch>122)
				continue;
			alphabet.add(ch);
		}
		
		//used only for sysout
		keyword = "";
		for(char ch : alphabet) {
			keyword += ch;
		}
		//end used only for sysout
		
		//adding other letters to the alphabet
		for(char ch='a'; ch<='z'; ++ch ) {
			alphabet.add(ch);
		}
		
		//making a list out of this set, because we need indexes
		List<Character> cipher_alphabet = new ArrayList<Character>(alphabet);
		System.out.println("_______________________________________________________________________________________________________");
		System.out.println("Cipher alphabet:\t" + cipher_alphabet);

		char[] open_text_chars = open_text.toLowerCase().toCharArray();
		//shifting
		key = key % 26; //to prevent looping more than once
		//when key is less than 0
		//instead of shifting left for x positions, we shift right for 26-x positions,
		//where x = key
		if (key < 0)
			key += 26;
		for (char ch : open_text_chars) {
			//finding current position of ch
			int position = cipher_alphabet.indexOf(ch);
			//validation: if character is not a letter, ignore it
			if(position==-1)
				continue;
			//position after shifting (position + key) % 26
			//look up that position in cipher alphabet to find new ch
			cipher += cipher_alphabet.get((position + key) % 26);
		}
		
		System.out.println("Open text:\t\t" + open_text + "\nKeyword:\t\t" + keyword +
							"\nKey:\t\t\t" + key + "\nCipher:\t\t\t" + cipher);
		System.out.println("_______________________________________________________________________________________________________");
	}

}
