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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

public class HttpClientPost {
	String uri, apikey, basekey;
	int tabletaille;
	private final HttpClient hc = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2).build();
	
	public HttpClientPost(String uri, String apikey, String basekey, int tabletaille) {
		this.uri = uri;
		this.apikey = apikey;
		this.basekey = basekey;
		this.tabletaille = tabletaille;
	}
	
	public void postWithInode(int [] values, int [] inode) throws IOException, InterruptedException {
		if(inode.length * tabletaille < values.length) {
			System.out.format("Add inode.length %d | tabletaille %d | v.length %d\n",inode.length, tabletaille,values.length);
			System.out.println("Not enough block or block size to store values");
			return;
		}
		
		//number of block used	
		int nombreRequete = values.length/tabletaille + ((values.length%tabletaille != 0)?1:0);
	
		int i = 0, j = 0; 
		while(i < nombreRequete) {
			String block = "B";
			if(inode[i] < 10)
				block += "0"+inode[i];
			else
				block += inode[i];
			j = (i+1)*tabletaille - 1;
			if( j > values.length)
				j = values.length - 1;
			
			postBlock(block, values, i*tabletaille, j, false);
			i++;
		}	
	}
		
		
	public void postBlock(String table, int [] values, int i, int j, boolean inode) throws IOException, InterruptedException {
		HttpRequest req;
		HttpResponse<String> resp;
		
		do {
			StringBuilder build = new StringBuilder().append("{\"records\":[");
			int k = 0;
			//building records to post
			
			//if table is a descriptor, add in 0 for block B00 before being caught in the while loop
			if(inode && values[0] == 0)
				build.append("{\"fields\":{\"Name\":\""+values[i++]+"\"}},");
			
			//add maximum 10 values per http request, don't add 0 values 
			while (i <= j  && k < 10 && values[i] != 0) {
				build.append("{\"fields\":{\"Name\":\""+values[i++]+"\"}},");
				k++;
			}
			
			//exit if there are no records to delete
			if(k == 0)
				break;
			
			build.replace(build.length()-1, build.length(), "]}");
			//deleting records
			req = HttpRequest.newBuilder()
					.method("POST", BodyPublishers.ofString(build.toString()))
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.uri(URI.create(uri+basekey+"/"+table+"?api_key="+apikey)).build();
			
			resp = hc.send(req, HttpResponse.BodyHandlers.ofString());
			//System.out.format("Add %s| %d\n",table,resp.statusCode());
		//if there are more than 10 ids, delete again
		}while(i <= j);
		
	}
	
	public static void main(String [] args) {
		String uri= "https://api.airtable.com/v0/", apikey = "keyAKfpQ5ihTg56jn", basekey = "appiJseYJbkOuCzmZ";
		int[][] v = Blockcreation.newValues(6, 96, 46);
		HttpClientDelete del = new HttpClientDelete(uri, apikey, basekey);
		HttpClientPost post = new HttpClientPost(uri, apikey, basekey,10);
		HttpClientGet get = new HttpClientGet(uri, apikey, basekey);
		try {
			//add new values to relation TableR
			del.deleteWithInode(get.getTableValues("TableR",10));
			post.postWithInode(v[0],get.getTableValues("TableR",10));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
