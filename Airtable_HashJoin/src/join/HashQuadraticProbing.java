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

public class HashQuadraticProbing implements HashProbing{
	private int m, PRIME = 11, current_size = 0, nbtombstone = 0;
	private int[] values, keys;
	
	public HashQuadraticProbing(int m) {
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
	
	//rehash toute la table si le nombre de tombstone depasse la mointié de m
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
	
	//si on s'arrete sur une tombstone, on le remplace
	@Override
	public void put(int key, int value) {
		if(isfull())
			return;
		current_size++;
		
		int h = hash(key), index = h, i = 1;
		while(keys[index] > 0) 
			index = Math.abs((h + (int)Math.pow(-1, i+1) * i*i++) % m);
		
		if(keys[index] == -1)
			nbtombstone--;
		
		keys[index] = key;
		values[index] = value;
	}
	
	@Override
	public int get(int key) {
		int h = hash(key), index = h, i = 1,res = -1;
		if(isempty())
			return res;
		
		while(keys[index] != key && keys[index] != 0) 
			index = Math.abs((h + (int)Math.pow(-1, i+1) * i*i++) % m);

		if(keys[index] == key)
			res = values[index];
          
        return res;
	}
	
	//on remplace les anciennes valeurs par des tombstones pour éviter le vide	
	@Override
	public void remove(int key) {
		if(isempty())
			return;

        int h = hash(key), index = h, i = 1;
        
        while (keys[index] != key && keys[index] != 0) 
            index = Math.abs((h + (int)Math.pow(-1, i+1) * i*i++) % m);        
        
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
