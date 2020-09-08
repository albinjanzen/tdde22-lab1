public class SymbolTable {
	private static final int INIT_CAPACITY = 7;


	/* Number of key-value pairs in the symbol table */
	private int N;
	/* Size of linear probing table */
	private int M;
	/* The keys */
	private String[] keys;
	/* The values */
	private Character[] vals;

	/**
	 * Create an empty hash table - use 7 as default size
	 */
	public SymbolTable() {
		this(INIT_CAPACITY);
	}

	/**
	 * Create linear probing hash table of given capacity
	 */
	public SymbolTable(int capacity) {
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
	}

	/**
	 * Return the number of key-value pairs in the symbol table
	 */
	public int size() {
		return N;
	}

	/**
	 * Is the symbol table empty?
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Does a key-value pair with the given key exist in the symbol table?
	 */
	public boolean contains(String key) {
		return get(key) != null;
	}

	/**
	 * Hash function for keys - returns value between 0 and M-1
	 */
	public int hash(String key) {
		int i;
		int v = 0;

		for (i = 0; i < key.length(); i++) {
			v += key.charAt(i);
		}
		return v % M;
	}

	/**
	 * Insert the key-value pair into the symbol table
	 */
	public void put(String key, Character val) {
		if (N==M) throw new RuntimeException("Tabellen Full");
		final int home = hash(key);
		int search_index = 0;
		for (int i = 0; i < M; i++) {
			search_index = (home + i) % M;
			if (this.keys[search_index]==null) {
				this.keys[search_index] = key;
				this.vals[search_index] = val;
				N++;
				return;
			}
			if (this.keys[search_index].equals(key)) {
				this.vals[search_index]=val;
				return;
			}
		}
	} 

	/**
	 * Return the value associated with the given key, null if no such value
	 */
	public Character get(String key) {
		final int home = hash(key);
		int search_index = 0;
		for (int i = 0; i < M; i++) {
			search_index = (home + i) % M;
			if (key.equals(this.keys[search_index])) {
				return this.vals[search_index];
			}
		}
		return null;
	} 

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key) {
		if (this.contains(key)) {
			final int home = hash(key);
			int search_index = 0;
			for (int i = 0; i < M; i++) {
				search_index = (home + i) % M;
				if (key.equals(this.keys[search_index])) {
					this.keys[search_index] = null;
					this.vals[search_index] = null;
				}
			}
		}
		this.rebalance();
		return;
		
	} 

	/**
	 * Print the contents of the symbol table
	 */
	public void dump() {
		String str = "";

		for (int i = 0; i < M; i++) {
			str = str + i + ". " + vals[i];
			if (keys[i] != null) {
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			} else {
				str = str + " -";
			}
			System.out.println(str);
			str = "";
		}
	}

	private void rebalance() {
		SymbolTable temp = new SymbolTable();
		for (int i = 0; i < M; i++) {
			if (this.keys[i] != null) {
				temp.put(this.keys[i], this.vals[i]);
			}
		}

		this.keys = temp.keys;
		this.vals = temp.vals;
	}
}
