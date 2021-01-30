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

package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import join.HashDoubleProbing;
import join.HashLinearProbing;
import join.HashProbing;
import join.HashQuadraticProbing;

public class TestHash {
	HashProbing linear;
	HashProbing quadratic;
	HashProbing doublehash;
	int n, i;
	@Before
	public void setUp() throws Exception {
		n = 10; i = 0;
		char [] r = {'Z','B','E','K','M','N','U','L','V','X'};
		
		linear = new HashLinearProbing(n+1);
		quadratic = new HashQuadraticProbing(n+1);
		doublehash = new HashDoubleProbing(n+1);
		while(i < n) {
			linear.put(r[i], i);
			quadratic.put(r[i], i);
			doublehash.put(r[i], i);
			i++;
		}
	}
	public boolean sameSign(int a, int b) {
		return (a >= 0 && b >= 0) || (a < 0 && b < 0);
	}
	@Test
	public void test() {
		char [] s = {'A','B','B','E','G','J','K','U','V','Z'};
		int [] result = {-1,2,2,2,-1,-1,2,2,2,2};
		i = 0;
		while(i < n) {
			assertTrue(sameSign(linear.get(s[i]), result[i]));
			assertTrue(sameSign(quadratic.get(s[i]), result[i]));
			assertTrue(sameSign(doublehash.get(s[i]), result[i]));
			i++;
		}
	}

}
