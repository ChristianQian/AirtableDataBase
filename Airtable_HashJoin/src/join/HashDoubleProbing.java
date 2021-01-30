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


public class HashDoubleProbing implements HashProbing{
	private int m, PRIME1 = 11, PRIME2 = 7, current_size = 0, nbtombstone = 0;
	private int[] values, keys;
	
	public HashDoubleProbing(int m) {
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
	
	private void rehashall() {
		nbtombstone = 0; current_size= 0;
		int [][] cpt = new int [2][m];
		int i = 0, j = 0;
		
		while(i < m) {
			if(keys[i] > 0) {
				cpt[0][j] = keys[i]; 
				cpt[1][j++] = values[i];	
			}
			keys[i] = 0; values[i++] = 0;
		}
		
		j = 0;
		while(cpt[0][j] != 0) 
			put(cpt[0][j], cpt[1][j++]);
		
	}
	
	private int hash(int key) {
		return key % PRIME1;
	}
	
	private int hash2(int key) {
		return PRIME2 - (key % PRIME2);
	}
	
	@Override
	public void put(int key, int value) {
		if(isfull())
			return;
		current_size++;
		
		int h = hash(key), index = h, h2 = hash2(key), i = 1;

		while(keys[index] > 0) 
			index = (h + (i++) * h2) % m;
		
		if(keys[index] == -1)
			nbtombstone--;
		
		keys[index] = key;
		values[index] = value;
	}

	@Override
	public int get(int key) {
		int h = hash(key), index = h, res = -1, h2 = hash2(key), i = 1;
		if(isempty())
			return res;
		
		while(keys[index] != key && keys[index] != 0) 
			index = (h + (i++) * h2) % m;
		
		if(keys[index] == key)
			res = values[index];
		
		return res;
	}

	@Override
	public void remove(int key) {
		if(isempty())
			return;

		int h = hash(key), index = h, h2 = hash2(key), i = 1;
		
		while (keys[index] != key && keys[index] != 0) 
            index = (h + (i++) * h2) % m; 
		
		//on vérifie que l'index est correct avant de supprimer
		if(keys[index] != key) {
			System.out.format("Suppression impossible : clé '%c' absente\n", key);
			return;
		}
		//delete
		current_size--;
		keys[index] = -1; values[index] = 0;
		nbtombstone++;
		
		if(nbtombstone >= m/2)
			rehashall();
	}
}
