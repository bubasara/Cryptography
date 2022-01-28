package bubasara.cryptography.columnar_transposition_cipher;


public class ColumnarTranspositionCipher {
	private Integer[] key;			//permutation of numbers
	private String open_text;		//result to be returned from decoding
	private String cipher;			//result to be returned from encoding
	private Character[][] matrix;	//matrix used for encryption
	private int n;
	private int m;
	
	public ColumnarTranspositionCipher(String open_text, Integer[] key) {
		this.key = key;
		this.cipher = new String();
		this.open_text = new String(open_text.replaceAll(" ",""));
		char[] open_text_chars = this.open_text.toLowerCase().toCharArray();
		int counter = 0;
		//number of columns is the length of a key
		//number of rows we get by dividing length of an open text with length of a key
		this.m = this.key.length;
		this.n = 1 + (int)Math.ceil((double)this.open_text.length()/(double)this.m);
		//if matrix is not completely filled with letters, we will add 'x' at the end
		this.matrix = new Character[this.n][this.m];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				if (i == 0) {
					//converting int to char, 10 is for decimal nums
					this.matrix[i][j] = Character.forDigit(this.key[j], 10);
				} else if (counter < open_text_chars.length){
					this.matrix[i][j] = open_text_chars[counter++];
				} else {
					this.matrix[i][j] = 'x';
				}
			}
		}
	}
	
	public void printMatrix() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				System.out.print(this.matrix[i][j]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Integer[] key = {4,3,1,2,5,7,6};
		ColumnarTranspositionCipher cipher = new ColumnarTranspositionCipher("kriptografija i kriptoanaliza", key);
		cipher.printMatrix();
	}

}
