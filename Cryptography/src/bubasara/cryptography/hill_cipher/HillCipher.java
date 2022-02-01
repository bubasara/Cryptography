package bubasara.cryptography.hill_cipher;

import java.util.ArrayList;
import java.util.List;

public class HillCipher {
	List<Character>alphabet; //regular alphabet A B C ... Z
	String cipher; //result to be returned from encoding
	String open_text; //result to be returned from decoding
	Integer m; //dimension of matrix m x m
	//Integer matrix[][]; //matrix used for Hill cipher
	
	//what we need:
	//-is matrix invertibile? -> Matrix.isInvertible()
	//-determinant -> Matrix.determinantOfMatrix()
	//-vector x matrix
	
	public HillCipher() {
		this.alphabet = new ArrayList<Character>(26);
		for(char ch='a'; ch<='z'; ++ch ) {
			this.alphabet.add(ch);
		}
		
		this.cipher = new String();
		this.open_text = new String();
	}
	
	public void setDimM(int m) {
		this.m = m;
		Matrix.N = m;
	}
	
	public Integer getDimM() {
		return this.m;
	}
	
	public String encode() {
		// TODO
		return this.cipher;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
