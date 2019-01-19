/**
 * @author Leejia James
 * @author Nirbhay Sibal
 *
 * Comparison of hashing implementations
 *
 * Ver 1.0: 2018/10/20
 */
package lxj171130;

public class CuckooHashing<T> {
	
	int tableNum;
	int maxLocations;
	int numHashFunctions;
	Object[][] table;
	Object minIntValue = Integer.MIN_VALUE;
	int threshold;
	
	
	public CuckooHashing() {
		tableNum = 1;
		maxLocations = 4194304; //power of 2, 2^22
		numHashFunctions = 2;
		threshold = maxLocations;
		table = new Object[tableNum][maxLocations];
		for(int i=0; i<tableNum; i++) {
			for(int j=0; j<maxLocations; j++) {
				table[i][j] = minIntValue;
			}
		}
	}
	
	/**
	 * Calculates and returns hash code
	 * 
	 * @param i
	 * @param x
	 * @return hashcode
	 */
	private int hashCodeCuckoo(int i, T x) {
		switch(i) {
			case 1:
				return indexFor(hash(x.hashCode()), maxLocations);
			default:
				return 1 + x.hashCode() % 9;
		}
	}
	
	/**
	 * Code extracted from Java’s HashMap
	 * @param h
	 * @param length
	 * @return index
	 */
	private int indexFor(int h, int length) { // length = table.length is a power of 2
		return h & (length-1);
	}
	
	/**
	 * Code extracted from Java’s HashMap
	 * @param h
	 * @return hashcode
	 */
	private int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	/**
	 * Add element x to the hashtable
	 * 
	 * @param x
	 * @return true if element is added, false if element is not added
	 */
	public boolean add(T x) {
		for(int i=1; i<=numHashFunctions; i++ ) {
			if(table[0][hashCodeCuckoo(i,x)].equals(x)) {
				return false;
			}
		}
		for(int i=1; i<=numHashFunctions; i++ ) {
			if(table[0][hashCodeCuckoo(i,x)].equals(minIntValue)) {
				table[0][hashCodeCuckoo(i,x)] = x;
				return true;
			}
		}
		int i = 1;
		int count = 1;
		while(count++ < threshold) {
			int location = hashCodeCuckoo(i,x);
			if(table[0][location] == minIntValue) {
				table[0][location] = x;
				return true;
			}
			else {
				Object temp = x;
				x = (T) table[0][location];
				table[0][location] = temp;
			}
			i = i == numHashFunctions ? 1 : (i+1);
		}
		
		//Rebuild hash table with new hash functions;
		rehashTable();
		add(x);
		return true;
	}
	
	/**
	 * Rehashing table when hashing could not find location in table 
	 * even after reaching threshold
	 */
	private void rehashTable() {
		Object[][] table1 = new Object[tableNum][maxLocations];
		for(int i1=0; i1<tableNum; i1++) {
			for(int j=0; j<maxLocations; j++) {
				table1[i1][j] = table[i1][j];
				table[i1][j] = minIntValue;
			}
		}
		maxLocations = 2 * maxLocations;
		table = new Object[tableNum][maxLocations];
		for(int i1=0; i1<tableNum; i1++) {
			for(int j=0; j<maxLocations; j++) {
				table[i1][j] = minIntValue;
			}
		}
		for(int i1=0; i1<tableNum; i1++) {
			for(int j=0; j<maxLocations/2; j++) {
				add((T) table1[i1][j]);
			}
		}
	}

	/**
	 * Checks if table contains the element x
	 * @param x
	 * @return true if element is present in hashtable, false otherwise
	 */
	public boolean contains(T x) {
		for(int i=1; i<=numHashFunctions; i++ ) {
			if(table[0][hashCodeCuckoo(i,x)].equals(x)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the element x from hashtable and marks the spot as deleted
	 * @param x
	 * @return the removed element x, null otherwise
	 */
	public Object remove(T x) {
		if(contains(x)) {
			for(int i=1; i<=numHashFunctions; i++ ) {
				if(table[0][hashCodeCuckoo(i,x)].equals(x)) {
					table[0][hashCodeCuckoo(i,x)] = minIntValue;
					return x;
				}
			}
		}
		else {
			return null;
		}
		return null;
	}
}
