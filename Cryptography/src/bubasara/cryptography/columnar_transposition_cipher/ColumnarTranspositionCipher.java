package bubasara.cryptography.columnar_transposition_cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ColumnarTranspositionCipher {
	private Integer[] key;			//permutation of numbers
	private String open_text;		//result to be returned from decoding
	private String cipher;			//result to be returned from encoding
	private Character[][] matrix;	//matrix used for encryption
	private int n;
	private int m;
	Map<Integer, ArrayList<Character>> columns = new HashMap<Integer, ArrayList<Character>>();
	
	//constructor for text we want to encode
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
	
	//constructor for text we want to decode
	public ColumnarTranspositionCipher(Integer[] key, String cipher) {
		this.key = key;
		this.cipher = cipher;
	}
	
	public void printMatrix() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				System.out.print(this.matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	//comparing by value (first element in ArrayList - key in Columnar Transposition Cipher)
	//if cipher key is 4321576 then map values are ArrayLists: [4, ...] [3, ...] [2, ...] ... [6, ...]
	//we compare by values 4, 3, 2, ..., 6
	Comparator<Entry<Integer, ArrayList<Character>>> valueComparator = new Comparator<Entry<Integer,ArrayList<Character>>>() {
		@Override
		public int compare(Entry<Integer, ArrayList<Character>> o1, Entry<Integer, ArrayList<Character>> o2) {
			Character v1 = o1.getValue().get(0);
		    Character v2 = o2.getValue().get(0);
		    return v1.compareTo(v2);
		}
	};
	
	public String encode() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				if (columns.containsKey(j+1)) {
					columns.get(j+1).add(matrix[i][j]);
				}
				else {
					ArrayList<Character> column_cipher = new ArrayList<Character>();
					column_cipher.add(matrix[i][j]);
					columns.put(j+1, column_cipher);
				}
			}
		}
		
		//comparing by value
		//reference: https://www.java67.com/2015/01/how-to-sort-hashmap-in-java-based-on.html
		Set<Entry<Integer, ArrayList<Character>>> entries = columns.entrySet();
		//Sort method needs a List, so let's first convert Set to List in Java
		List<Entry<Integer, ArrayList<Character>>> listOfEntries = new ArrayList<Entry<Integer, ArrayList<Character>>>(entries);
		//sorting HashMap by values using comparator
		Collections.sort(listOfEntries, valueComparator);
		LinkedHashMap<Integer, ArrayList<Character>> columnsSortedByValue = new LinkedHashMap<Integer, ArrayList<Character>>(listOfEntries.size());
		//copying entries from List to Map
		for(Entry<Integer, ArrayList<Character>> entry : listOfEntries){
	        columnsSortedByValue.put(entry.getKey(), entry.getValue());
	    }
	    
		ArrayList<Character> temp = new ArrayList<Character>();
	    //sorting entries by values
	    Set<Entry<Integer, ArrayList<Character>>> columnsEntrySetSortedByValue = columnsSortedByValue.entrySet();
	    for(Entry<Integer, ArrayList<Character>> mapping : columnsEntrySetSortedByValue){
	        //removing nums so only cipher chars remain
	        temp = mapping.getValue();
	        temp.remove(0);
	        cipher += temp.toString();
	    }
	    //removing brackets, comas and spaces
	    cipher = Arrays.toString(cipher.toCharArray()).replace("[", "").replace("]", "").replace(", ", "").replace(",", "").replace(" ", "");
		
		return this.cipher;
	}
	
	public String decode() {
		int blocks_len = this.cipher.length() / this.key.length;
		int blocks_num = key.length;		
		int start = 0;
		int end = blocks_len;
		int counter = 1;
		Map<Integer, ArrayList<Character>> map = new HashMap<Integer, ArrayList<Character>>();
		
		while (start <= (this.cipher.length()-4)) {
			ArrayList<Character> block = new ArrayList<Character>();
			String block_string = cipher.substring(start, end);
			//System.out.println("block_string: " + block_string);
			char[] block_chars = block_string.toLowerCase().toCharArray();
			for (Character c : block_chars) {
				//System.out.println("block_chars: " + c);
				block.add(c);
			}
			map.put(counter++, block);
			start += blocks_len;
			end += blocks_len;
		}
		
//		for (Map.Entry<Integer, ArrayList<Character>> entry : map.entrySet())
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		
		this.matrix = new Character[blocks_num][blocks_len];
	
		this.open_text = "";
		for(int i = 0; i < blocks_len; i++) {
			for (Integer j: this.key) {
				this.open_text += map.get(j).get(i);
			}
		}
		return this.open_text;
	}
	
	public static void main(String[] args) {
		Integer[] key = {4,3,1,2,5,7,6};
		ColumnarTranspositionCipher cipher = new ColumnarTranspositionCipher("kriptografija i kriptoanaliza", key);
		cipher.printMatrix();
		System.out.println("\nEncode result: " + cipher.encode());
		ColumnarTranspositionCipher cipher2 = new ColumnarTranspositionCipher(key, "ifilpipirarakrkntjtzgiaxoaoa");
		System.out.println("\nDecode result: " + cipher2.decode());
	}

}
