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

public class Mainclass {

	public static void main(String [] args) {
		int n = 10, i = 0;
		char cléabsente = 'T';
		char [] r = {'Z','B','E','K','M','N','U','L','V','X'};
		HashProbing linear = new HashLinearProbing(n+1);
		HashProbing quadratic = new HashQuadraticProbing(n+1);
		HashProbing doublehash = new HashDoubleProbing(n+1);
		
		while(i < n) {
			linear.put(r[i], i);
			quadratic.put(r[i], i);
			doublehash.put(r[i], i);
			i++;
		}
		
		i = 0;
		while(i < n) {
			System.out.format("get(%c) = %d | %d | %d\n", 
					r[i], linear.get(r[i]), quadratic.get(r[i]), doublehash.get(r[i]));
			i++;
		}
		System.out.format("Recherche d'une clé absente get(%c) = %d | %d | %d\n", 
				cléabsente, linear.get(cléabsente), quadratic.get(cléabsente), doublehash.get(cléabsente));
		linear.get(cléabsente);
		quadratic.get(cléabsente);
		doublehash.get(cléabsente);
		
		i = 0;
		System.out.println("Suppression de tous les elements");
		while(i < n) {
			linear.remove(r[i]);
			quadratic.remove(r[i]);
			doublehash.remove(r[i]);
			i++;
		}
		System.out.format("Tableau vide? %b | %b | %b\n", linear.isempty(), quadratic.isempty(), doublehash.isempty());
	}
}
