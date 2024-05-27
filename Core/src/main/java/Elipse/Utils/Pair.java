package Elipse.Utils;

import java.io.Serializable;

public class Pair<K, V> implements Serializable {
	private K key;
	private V value;

	/**
	 * @return the first value of the pair
	 */
	public K getKey() {
		return key;
	}

	/**
	 * 
	 * @return the second value of the pair
	 */
	public V getValue() {
		return value;
	}

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
}