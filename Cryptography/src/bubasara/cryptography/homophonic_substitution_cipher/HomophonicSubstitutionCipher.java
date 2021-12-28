package bubasara.cryptography.homophonic_substitution_cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomophonicSubstitutionCipher {
	String cipher = ""; //result to be returned from encoding
	String open_text = ""; //result to be returned from decoding
	String keyword = ""; //keyword used for encoding/decoding
	Map<Character, List<Character>> alphabet; //cipher alphabet
	
	public HomophonicSubstitutionCipher() {
		this.alphabet = new HashMap<Character, List<Character>>();
	}
	
	//predefined randomly chosen alphabet
	public void createAlphabet() {
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
		//wip
		//input - only unique values
		//use set for validation
		HomophonicSubstitutionCipher hsp = new HomophonicSubstitutionCipher();
		hsp.createAlphabet();
		String result = hsp.encode("ono sto je bitno");
		System.out.println("encode result: " + result);
		// TODO custom alphabet with a keyword
		result = hsp.decode("truy0tkcej0ru");
		System.out.println("decode result: " + result);
	}

}
