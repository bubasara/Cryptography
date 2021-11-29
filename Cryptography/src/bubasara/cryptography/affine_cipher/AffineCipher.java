package bubasara.cryptography.affine_cipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AffineCipher {
	Integer key_a, key_b;
	String open_text, cipher;
	List<Character>alphabet; //regular alphabet A B C ... Z

	public AffineCipher(Integer a, Integer b, String open_text) {
		this.key_a = a;
		this.key_b = b;
		this.open_text = open_text;
		this.open_text = new String();
		this.cipher = new String();
		
		this.alphabet = new ArrayList<Character>(26);
		for(char ch='a'; ch<='z'; ++ch ) {
			this.alphabet.add(ch);
		}
	}

	public static void main(String[] args) {
		String result = "";
		Boolean a_valid = false;
		Boolean b_valid = false;
		AffineCipher affine_cipher = null;
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
		String input = in.next().toString();
		while (!a_valid || !b_valid) {
			System.out.println("What's the key? First num: ");
			Integer a = in.nextInt();
			System.out.println("What's the key? Second num: ");
			Integer b = in.nextInt();
			if (gcd(a, 26) == 1 && gcd(a, b) == 1 && 0 < b && b < 26) {
				affine_cipher = new AffineCipher(a, b, input);
				a_valid = true;
				b_valid = true;
			} else {
				System.out.println("Key is not valid, try again");
			}
		}
		in.close();
		if(choice == 1)
			result = affine_cipher.encode(affine_cipher, input);
		else if(choice == 2)
			result = affine_cipher.decode(affine_cipher, input);
		System.out.println("The result is: " + result);
	}
	
	//Greatest Common Divisor - Euclid's Algorithm
	public static int gcd(int num1, int num2) {
	    if (num2 == 0) {
	        return num1;
	    }
	    return gcd(num2, num1 % num2);
	}
	
	//multiplicative inverse of a
	static int inverse(int num, int modulo) 
    { 
		num = num % modulo; 
        for (int i = 1; i < modulo; i++) 
           if ((num * i) % modulo == 1) 
              return i; 
        return 1; 
    } 
	
	public String encode(AffineCipher affine_cipher, String open_text) {
		char[] open_text_chars = open_text.toLowerCase().toCharArray();
		for (char ch : open_text_chars) {
			//x = alphabet.indexOf(ch) - find numeric equivalent of a character
			//apply formula y = (a*x + b) % 26
			int y = (affine_cipher.key_a * alphabet.indexOf(ch) + affine_cipher.key_b) % 26;
			//System.out.println("index: " + alphabet.indexOf(ch)+"\tch_num: " + ch_num);
			//find character of that index in alphabet
			//and add it to the result
			this.cipher += alphabet.get(y);
		}
		return this.cipher;
	}
	
	public String decode(AffineCipher affine_cipher, String cipher) {
		char[] cipher_chars = cipher.toLowerCase().toCharArray();
		for (char ch : cipher_chars) {
			//find numeric equivalent of a character
			int y = alphabet.indexOf(ch);
			//check if y-b < 0
			int temp = y - affine_cipher.key_b;
			if(temp < 0) {
				//if yes, add 26 and make a cycle
				temp += 26;
			}
			//apply formula x = (a^-1 * (y - b)) % 26
			int x = (inverse(affine_cipher.key_a, 26) * temp) % 26;
			//System.out.println("ch: " + ch + " temp: " + temp + " y: " + y + " x: " + x);
			//find character of that index in alphabet
			//and add it to the result
			this.open_text += alphabet.get(x);
		}
		return this.open_text;
	}

}
