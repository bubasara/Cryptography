package bubasara.cryptography.hill_cipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HillCipher {
	List<Character>alphabet; //regular alphabet A B C ... Z
	String cipher; //result to be returned from encoding
	String open_text; //result to be returned from decoding
	int m; //dimension of matrix m x m
	Integer[][] matrix; //matrix used for Hill cipher
	
	//what we need:
	//-is matrix invertible? -> Matrix.isInvertible()
	//-determinant -> Matrix.determinantOfMatrix()
	//-vector x matrix
	
	public HillCipher(int m) {
		this.alphabet = new ArrayList<Character>(26);
		for(char ch='a'; ch<='z'; ++ch ) {
			this.alphabet.add(ch);
		}
		this.m = m;
		Matrix.N = m;
		this.matrix = new Integer[this.m][this.m];
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
	
	public void setOpenText(String open_text) {
		this.open_text = open_text;
	}
	
	void inputMatrix() {
		System.out.println("Please input nums to form a matrix " + this.m + "x" + this.m + ":");
		Scanner in = new Scanner(System.in);
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.m; j++) {
				matrix[i][j] = in.nextInt();
			}
		}
		in.close();
	}
	
	void printMatrix() {
		System.out.println("\nMatrix:");
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.m; j++) {
				System.out.print(this.matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public String encode() {
		if(!Matrix.isInvertible(this.matrix, this.m)) {
			return "Matrix is not invertible. Please input another matrix.";
		} else {
			//block length is this.m
			//int blocks_num = (int)Math.ceil((double)this.open_text.length()/(double)this.m);
			
			//if the last block is not completed, we will add random letter at the end
			while(this.open_text.length() % this.m != 0) {
				Random rand = new Random();
				this.open_text += this.alphabet.indexOf(rand.nextInt(25));
			}
			
			//init
			int start = 0;
			int end = start + this.m;
			while(start <= this.open_text.length()-this.m) {
				int[] sum = new int[this.m];
				//splits open text into blocks
				//length of each block = matrix dimension
				char[] block_chars = this.open_text.substring(start, end).toCharArray();
				//then we transform each block of chars to their numeric equivalents
				int[] block_int = new int[this.m];
				for (int i = 0; i < this.m; i++) {
					block_int[i] = this.alphabet.indexOf(block_chars[i]);
				}
				//multiplying vector and matrix
				//calculating sum for each matrix column
				//result is vector
				for (int i = 0; i < this.m; i++) {
					for (int j = 0; j < this.m; j++) {
						sum[j] += block_int[i] * this.matrix[i][j];
					}
				}
				
				//transforming numeric equivalent to a char
				//and adding it to a result cipher
				for(int i = 0; i < this.m; i++) {
					while (sum[i] < 0) {
						sum[i] += 26;
					}
					this.cipher += this.alphabet.get(sum[i]%26);
				}
				
				//reinit
				start = end;
				end += this.m;
				block_int = new int[this.m];
			}
		}
		return this.cipher;
	}
	
	public static void main(String[] args) {
		HillCipher hillCipher = null;
		
		Scanner in = new Scanner(System.in);
		System.out.println("Firstly, please input text you want to encode: ");
		String open_text = in.next();
		
		System.out.println("Now, we need matrix. Do you want to use default one (1) or to input yours (2) ?");
		Integer choice = in.nextInt();
		while (choice != 1 && choice !=2) {
			System.out.println("Please type 1 or 2 only: ");
			choice = in.nextInt();
		}
		if(choice == 1) {
			hillCipher = new HillCipher(3);
			hillCipher.matrix = new Integer[][]{{5, 8, 22}, {2, 5, 24 }, {10, 20, 17}};
		} else if(choice == 2) {
			System.out.println("Dimension of the matrix:");
			int dim = in.nextInt();
			hillCipher = new HillCipher(dim);
			hillCipher.inputMatrix();
		}

		hillCipher.printMatrix();		
		hillCipher.setOpenText(open_text);
		System.out.println("\nEncoded: " + hillCipher.encode());
		in.close();
	}

}
