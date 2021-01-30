/**
* TP n °: 4
*
* Titre du TP : Disk Nested Loop Join
*
* Date : 15 novembre 2020
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Blockcreation {
	
	//creating values of descriptor S, R, RS
	static public int[][] inodeValues(int nbR, int nbS) {
		int[][] res = new int[3][];
		int nbRS = Math.min(nbR,nbS);
		res[0] = new int[nbR];
		res[1] = new int[nbS];
		res[2] = new int[nbRS];
		
		int i = 0;
		
		while(i < Math.max(nbR,nbS)) {
			if(i < nbR)
				res[0][i] = i;
			if(i < nbS)
				res[1][i] = i + nbR;
			if(i < nbRS)
				res[2][i] = i + nbR + nbS;
			i++;
		}

		return res;
	}
	//creating  new values for R and S
	static public int[][] newValues(int letter, int nbR, int nbS) {
		int[][] res = new int[2][];
		if(letter < 1 || 26 < letter)
			return res;
		res[0] = new int[nbR];
		res[1] = new int[nbS];
		int[] l1 = new int[26];
		List<Integer> l2 = new ArrayList<Integer>();
		
		Arrays.setAll(l1, i -> i + 65);

		for(int i = 0; i<l1.length ; i++) {
			for(int j = 0; j<letter ; j++)
				l2.add(l1[i]*100 + l1[j]); 
		}
		
		Collections.shuffle(l2);
		for(int i = 0; i<nbR ; i++) 
			res[0][i] = l2.get(i); 
		Collections.shuffle(l2);
		for(int i = 0; i<nbS ; i++) 
			res[1][i] = l2.get(i);
		
		return res;
	}
	
	
	public static void main(String [] args) {
		/*
		int[][] r = newValues(8, 96, 46);
		for(int b : r[0]) 
			System.out.format("%d\n",b);
		for(int b : r[1]) 
			System.out.format("%d\n",b);
		*/
		
		for(int [] a : inodeValues(10, 5)) {
			System.out.println();
			for(int b : a)
				System.out.println(b);
		}
		
		
	}
}
