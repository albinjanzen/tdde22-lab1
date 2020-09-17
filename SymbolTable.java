import java.util.ArrayList;

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
		if (val == null) {
			this.delete(key);
			return;
		}
		if (N == M)
			throw new RuntimeException("Tabellen Full");
		final int home = hash(key);
		int search_index = 0;
		for (int i = 0; i < M; i++) {
			search_index = (home + i) % M;
			if (this.keys[search_index] == null) {
				this.keys[search_index] = key;
				this.vals[search_index] = val;
				N++;
				return;
			}
			if (this.keys[search_index].equals(key)) {
				this.vals[search_index] = val;
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
			// Added break if empty
			if (this.keys[search_index].equals(null)) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key) {
		// Removed contains condition
		final int home = hash(key);
		int search_index = 0;
		for (int i = 0; i < M; i++) {
			search_index = (home + i) % M;
			if (key.equals(this.keys[search_index])) {
				this.keys[search_index] = null;
				this.vals[search_index] = null;
				this.N--;
				this.rebalance(home, search_index);

				break;
			}
			// Added condition for not finding the searched key
			if (this.keys[search_index] == null) {
				return;
			}
		}
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

	private void rebalance(int home, int end) {
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<Character> vals = new ArrayList<Character>();
		// Start at home and "pick up" the values that hashed wrong.
		for (int i = 0; i < M; i++) {
			int search_index = (home + 1 + i) % M;
			if (this.keys[search_index] == null) {
				break;
			}
			if (!(search_index == hash(this.keys[search_index]))) {
				keys.add(this.keys[search_index]);
				vals.add(this.vals[search_index]);
				this.keys[search_index] = null;
				this.vals[search_index] = null;
				this.N--;
			}
		}
		// Readd the "picked" values to the table
		for (int i = 0; i < keys.size(); i++) {
			this.put(keys.get(i), vals.get(i));

		}

	}
}
