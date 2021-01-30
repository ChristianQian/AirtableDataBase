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

import java.io.IOException;

public class Nestedloop {
	int tabletaille;
	private Inode objectrs;
	public int all = 0;
	public Nestedloop(int tabletaille, Inode objectrs) {;
		this.tabletaille = tabletaille;
		this.objectrs = objectrs;
	}

	//adding to the previous values before, check if there are more values than a block and store in a second array after.
	public boolean nloop(int[] r, int[] s, int[] before, int index) throws IOException, InterruptedException {
		int k = 0, i = 0, j = 0;
		
		//get the index where we continue to add
		int [] after = new int [tabletaille];
		while(k < tabletaille) {
			if(before[k] == 0) 
				break;
			k++;
		}
		
		while(i < tabletaille) {
			j = 0;
			while(j < tabletaille) {
				if(r[i] == s[j] && r[i] > 0) {
					all++;
					if(k >= tabletaille)
						after[(k++)%tabletaille] = r[i];
					else
						before[k++] = r[i];
				}
				j++;
			}
			i++;
		}
		//if before is not complete return before
		if(before[tabletaille-1] == 0) 
			return false;
 
		objectrs.addToBlock(index, before);
		
		//change new values of before
		i = 0;
		while(i < tabletaille) {
			before[i] = after[i];
			i++;
		}
		return true;
	}
	
}
