/**
* TP n °: 5
*
* Titre du TP : Hash Probing
*
* Date : 27 novembre 2020
*
* Nom : Qian
* Prénom : Christian
* N ° d'étudiant : 21964319
*
* email : christian-qian@live.fr
*
* Remarques : 
*/

package join;

public class HashLinearProbing implements HashProbing {
	private int m, PRIME = 11, current_size = 0;
	private int[] keys, values;
	
	public HashLinearProbing(int m) {
		this.m = m;
		keys = new int[m];
		values = new int[m];
	}
	
	public boolean isfull() {
		return current_size == m-1;
	}
	
	public boolean isempty() {
		return current_size == 0;
	}
	
	private int hash(int key) {
		return key % PRIME;
	}
	
	
	@Override
	public void put(int key, int value) {
		if(isfull())
			return;
		current_size++;
		int index = hash(key);
		while(keys[index] != 0)
			index = (index + 1) % m;
		keys[index] = key;
		values[index] = value;
	}

	@Override
	public int get(int key) {
		int index = hash(key), res = -1;
		if(isempty())
			return res;
		
		while(keys[index] != key && keys[index] != 0)
			index = (index + 1) % m;
		
		if(keys[index] == key)
			res = values[index];
		return res;
	}

	@Override
	public void remove(int key) {
		if(isempty())
			return;
		
		int index = hash(key);
		while(keys[index] != key && keys[index] != 0)
			index = (index + 1) % m;
		
		//on vérifie que l'index est correct avant de supprimer
		if(keys[index] != key) {
			System.out.format("Suppression impossible : clé '%c' absente\n", key);
			return;
		}
		
		//delete
		current_size--;
		keys[index] = 0; values[index] = 0;
		
		// rehash
		int savedvalue;
		int savedkey;
		index = (index + 1) % m;
		while(keys[index] != 0) {
			current_size--;
			savedkey = keys[index]; savedvalue = values[index];
			keys[index] = 0; values[index] = 0;
			put(savedkey, savedvalue);
			index = (index + 1) % m;
		}
	}
}
