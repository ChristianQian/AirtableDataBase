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

public interface HashProbing {

	public void put(int key, int value);
	public int get(int key);
	public void remove(int key);
	public boolean isempty();
	public boolean isfull();
}
