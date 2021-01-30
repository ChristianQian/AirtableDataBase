package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import join.Blockcreation;
import join.MainClass;

public class NestedloopTest {

	int [] rsd;
	int [][] v;
	MainClass m;
	int letter, nbR, nbS, blocktaille, inodetailleR, inodetailleS;
	ArrayList<Integer> l1, l2;
	
	@Before
	public void setUp() throws Exception {
		String uri= "https://api.airtable.com/v0/", api = "keyAKfpQ5ihTg56jn", base = "appiJseYJbkOuCzmZ";
		letter = 6; nbR = 96; nbS = 46; blocktaille = 10; inodetailleR = 10; inodetailleS = 5;

		m = new MainClass(uri, api, base, inodetailleR, inodetailleS, blocktaille);
		v = Blockcreation.newValues(letter, nbR, nbS);
		
		l1 = new ArrayList<>();
		l2 = new ArrayList<>();
		
		int i = 0, j = 0;
		while(i < v[0].length) {
			j = 0;
			while(j < v[1].length) {
				if(v[0][i] == v[1][j] && v[0][i] > 0) 
					l1.add(v[0][i]);
				j++;
				}
			i++;
		}
	}

	@Test
	public void test() throws Exception {
		int [] cpt;
		
		m.addInodeValues(Blockcreation.inodeValues(inodetailleR, inodetailleS));
		m.addNewValues(v);
		m.exeNestedloop();
		
		int i = 0, j = 0;
		while(i < m.inodetailleRS) {
			j = 0;
			cpt = m.objectrs.getBlockValues(i);
			while(j < cpt.length) {
				if(cpt[j] > 0) 
					l2.add(cpt[j]);
				j++;
			}
			i++;
		}
		
		Collections.sort(l1);
		Collections.sort(l2);
		assertEquals(l1, l2);
	}

}
