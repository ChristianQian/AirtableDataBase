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

public class MainClass {
	public int inodetailleR, inodetailleS, inodetailleRS, blocktaille;
	public Inode objectr, objects, objectrs;
	public Nestedloop n;
	public MainClass(String uri, String apikey, String basekey, int inodetailleR, int inodetailleS, int blocktaille) throws IOException, InterruptedException {
		this.inodetailleR = inodetailleR;
		this.inodetailleS = inodetailleS;
		this.inodetailleRS = Math.min(inodetailleR, inodetailleS);
		this.blocktaille = blocktaille;
		HttpClientGet get = new HttpClientGet(uri, apikey, basekey);
		HttpClientDelete del = new HttpClientDelete(uri, apikey, basekey);
		HttpClientPost post = new HttpClientPost(uri, apikey, basekey, blocktaille);
		objectr = new Inode("TableR", inodetailleR, blocktaille, get, del, post);
		objects = new Inode("TableS", inodetailleS, blocktaille, get, del, post);
		objectrs = new Inode("TableRS", inodetailleRS, blocktaille, get, del, post);

		n = new Nestedloop(blocktaille, objectrs);
		
		
	}
	
	public void addInodeValues(int [][] v) throws Exception{
		objectr.addInodeValues(v[0]);
		objects.addInodeValues(v[1]);
		objectrs.addInodeValues(v[2]);
	}

	public void addNewValues(int [][] v) throws Exception{
		objectr.createRelation(v[0]);
		objects.createRelation(v[1]);
		objectrs.deleteRelation();
	}
	
	//store result of nestedloop in rs
	public void exeNestedloop() throws Exception{
		int[] r, s;
		
		int[] rs = new int [blocktaille]; 
		
		int i = 0, j = 0, index = 0;
		while(i < inodetailleR) {
			r = objectr.getBlockValues(i);
			j = 0;
			while(j < inodetailleS) {
				s = objects.getBlockValues(j);
				//return true if there are more values than blocktaille, post them and pass to the next block rsd[k++]
				if(n.nloop(r, s, rs, index))
					index++;
				j++;
			}
			i++;
		}
		//if there are still some values left, post them
		if(rs[0] != 0)
			objectrs.addToBlock(index, rs);

	}
	
	public static void main(String [] args) {
		String uri= "https://api.airtable.com/v0/", apikey = "keyAKfpQ5ihTg56jn", basekey = "appiJseYJbkOuCzmZ";
		int letter = 6, nbR = 96, nbS = 46, blocktaille = 10, inodetailleR = 10, inodetailleS = 5;
		MainClass m;
		try {
			m = new MainClass(uri, apikey, basekey, inodetailleR, inodetailleS, blocktaille);
			m.addInodeValues(Blockcreation.inodeValues(inodetailleR, inodetailleS));
			m.addNewValues(Blockcreation.newValues(letter, nbR, nbS));
			//System.out.println("Call exeNestedloop");
			m.exeNestedloop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
