package join;

import java.io.IOException;

public class Inode {
	private String name;
	private HttpClientGet get;
	private HttpClientDelete del;
	private HttpClientPost post;
	private int[] point;
	public int inodetaille, blocktaille;
	public Inode(String name, int inodetaille, int blocktaille, HttpClientGet get, HttpClientDelete del, HttpClientPost post) throws IOException, InterruptedException {
		this.name = name;
		this.get = get;
		this.del = del;
		this.post = post;
		this.blocktaille = blocktaille;
		this.inodetaille = inodetaille;
	}
	
	public void addInodeValues(int[] values) throws IOException, InterruptedException {
		point = values;
		del.deleteTable(name);
		post.postBlock(name, values, 0, values.length-1, true);
	}
	
	public void deleteRelation() throws IOException, InterruptedException {
		del.deleteWithInode(point);
	}
	
	public void createRelation(int[] values) throws IOException, InterruptedException {
		del.deleteWithInode(point);
		post.postWithInode(values, point);
	}
	public int getBlock(int index) {
		return point[index];
	}
	
	private String intToBlock(int i) {
		String b = "B";
		if(i < 10)
			return b += "0"+i;
		return b += i;	
	} 
	
	public int[] getBlockValues(int i) throws IOException, InterruptedException {
		return get.getTableValues(intToBlock(point[i]), blocktaille);
	}
	
	public void addToBlock(int k, int[] values) throws IOException, InterruptedException {
		post.postBlock(intToBlock(point[k]), values, 0, blocktaille-1, false);
	}
}
