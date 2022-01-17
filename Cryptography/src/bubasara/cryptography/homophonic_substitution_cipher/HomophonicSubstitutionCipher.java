package bubasara.cryptography.homophonic_substitution_cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class HomophonicSubstitutionCipher {
	String cipher = ""; //result to be returned from encoding
	String open_text = ""; //result to be returned from decoding
	String keyword = ""; //keyword used for encoding/decoding
	Map<Character, List<Character>> alphabet; //cipher alphabet
	
	public HomophonicSubstitutionCipher() {
		this.alphabet = new HashMap<Character, List<Character>>();
	}
	
	//predefined randomly chosen alphabet
	public void createPredefinedAlphabet() {
		this.alphabet.put('a',new ArrayList<Character>(Arrays.asList('4', 's')));
		this.alphabet.put('b',new ArrayList<Character>(Arrays.asList('e')));
		this.alphabet.put('c',new ArrayList<Character>(Arrays.asList('a')));
		this.alphabet.put('d',new ArrayList<Character>(Arrays.asList('o')));
		this.alphabet.put('e',new ArrayList<Character>(Arrays.asList('n', 'b', 'c', 'd')));
		this.alphabet.put('f',new ArrayList<Character>(Arrays.asList('f')));
		this.alphabet.put('g',new ArrayList<Character>(Arrays.asList('g')));
		this.alphabet.put('h',new ArrayList<Character>(Arrays.asList('h')));
		this.alphabet.put('i',new ArrayList<Character>(Arrays.asList('i', 'j')));
		this.alphabet.put('j',new ArrayList<Character>(Arrays.asList('k')));
		this.alphabet.put('k',new ArrayList<Character>(Arrays.asList('l')));
		this.alphabet.put('l',new ArrayList<Character>(Arrays.asList('m')));
		this.alphabet.put('m',new ArrayList<Character>(Arrays.asList('p')));
		this.alphabet.put('n',new ArrayList<Character>(Arrays.asList('q', 'r')));
		this.alphabet.put('o',new ArrayList<Character>(Arrays.asList('t', 'u')));
		this.alphabet.put('p',new ArrayList<Character>(Arrays.asList('v')));
		this.alphabet.put('q',new ArrayList<Character>(Arrays.asList('w')));
		this.alphabet.put('r',new ArrayList<Character>(Arrays.asList('x')));
		this.alphabet.put('s',new ArrayList<Character>(Arrays.asList('y')));
		this.alphabet.put('t',new ArrayList<Character>(Arrays.asList('0', '1', '2')));
		this.alphabet.put('u',new ArrayList<Character>(Arrays.asList('3')));
		this.alphabet.put('v',new ArrayList<Character>(Arrays.asList('5')));
		this.alphabet.put('w',new ArrayList<Character>(Arrays.asList('6')));
		this.alphabet.put('x',new ArrayList<Character>(Arrays.asList('7')));
		this.alphabet.put('y',new ArrayList<Character>(Arrays.asList('8')));
		this.alphabet.put('z',new ArrayList<Character>(Arrays.asList('9')));
	}
	
	public void createCustomAlphabet() {
		System.out.println("For each letter type possible cipher characters. To finish press ~."
				+ "\nCharacters must be unique. If repeated, character will be ignored.");
		//Boolean finished = false;
		Scanner in = new Scanner(System.in);
		Character letter = 'a';
		ArrayList<Character> list;
		Set<Character> set = new HashSet<Character>();
		while(letter != '{') {
			list = new ArrayList<Character>();
			Character ch = '0';
			System.out.println(letter + ": ");
			while((int)ch != 126) {	//character ~
				ch = in.next().charAt(0);
				if(!set.contains(ch)){
					set.add(ch);
					list.add(ch);
				}
				this.alphabet.put(letter, list);
			}
			letter++;
		}
		in.close();
	}
	
	public String encode(String open_text) {
		char[] open_text_chars = open_text.toLowerCase().toCharArray();
		Random rand = new Random();
		for (char ch : open_text_chars) {
			if (ch == ' ') continue;
			this.cipher += this.alphabet.get(ch).get(rand.nextInt(alphabet.get(ch).size()));
		}
		return this.cipher;
	}

	public String decode(String cipher) {
		char[] cipher_chars = cipher.toLowerCase().toCharArray();
		for (char ch : cipher_chars) {
			for(Character key : this.alphabet.keySet()) {
				if(this.alphabet.get(key).contains(ch))
					this.open_text += key;
			}
		}
		return this.open_text;
	}
	
	public static void main(String[] args) {
		HomophonicSubstitutionCipher hsp = new HomophonicSubstitutionCipher();
		String result = "";
		
		//example
		/*
		hsp.createPredefinedAlphabet();
		result = hsp.encode("ono sto je bitno");
		System.out.println("encode result: " + result);
		result = hsp.decode("truy0tkcej0ru");
		System.out.println("decode result: " + result);
		*/
		
		Scanner in = new Scanner(System.in);
		System.out.println("Type 1 if you want to encode OR 2 if you want to decode a text:");
		Integer choice1 = in.nextInt();
		while (choice1 != 1 && choice1 !=2) {
			System.out.println("Please type only 1 or 2: ");
			choice1 = in.nextInt();
		}
		if(choice1 == 1) {
			System.out.println("Write a text you want to be encoded\n(only letters will be taken into account): ");
		} else if(choice1 == 2){
			System.out.println("Write a text you want to be decoded: ");
		}
		String text = in.next().toString();
		
		System.out.println("Type 1 if you want to use predefined alphabet OR 2 if you want to define a custom alphabet:");
		Integer choice2 = in.nextInt();
		while (choice2 != 1 && choice2 !=2) {
			System.out.println("Please type only 1 or 2: ");
			choice2 = in.nextInt();
		}
		if (choice2 == 1) {
			hsp.createPredefinedAlphabet();
			System.out.println("Predefined alphabet is ready to be used.");
		}
		else if(choice2 == 2) {
			System.out.println("\nDefining new alphabet. ");
			hsp.createCustomAlphabet();
			System.out.println("Custom alphabet is ready to be used. ");
		}

		if(choice1 == 1) {
			result = hsp.encode(text);
		} else if (choice1 == 2) {
			result = hsp.decode(text);
		}
		System.out.println("result: " + result);
		in.close();
	}

}
