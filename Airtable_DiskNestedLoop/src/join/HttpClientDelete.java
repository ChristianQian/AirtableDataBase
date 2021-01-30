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
import java.net.http.HttpResponse;
import java.util.List;

public class HttpClientDelete {
	HttpClientGet get;
	String uri, apikey, basekey;
	private final HttpClient hc = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2).build();
	
	public HttpClientDelete(String uri, String apikey, String basekey) {
		this.uri = uri;
		this.apikey = apikey;
		this.basekey = basekey;
		get = new HttpClientGet(uri, apikey, basekey);
	}
	
	public void deleteWithInode(int [] inode) throws IOException, InterruptedException {
		int i = 0; 
		while(i < inode.length) {
			String block = "B";
			if(inode[i] < 10)
				block += "0"+inode[i];
			else
				block += inode[i];
			deleteTable(block);
			i++;
		}
	}
	
	public void deleteTable(String table) throws IOException, InterruptedException {
		//getting ids from table
		HttpRequest req;
		HttpResponse<String> resp;
		List<String> l = get.getTableId(table);
	
		//creating string with ids to delete with maximum 10 id
		int j = 0;
		do {
			StringBuilder build = new StringBuilder().append("");
			int i = 0;
			//building records to delete
			while (j < l.size() && i < 10) {
				build.append("&records[]="+l.get(j));
				j++;i++;
			}
			//exit if there are no records to delete
			if(build.toString().length() == 0)
				break;

			//deleting records
			req = HttpRequest.newBuilder().DELETE()
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.uri(URI.create(uri+basekey+"/"+table+"?api_key="+apikey+build.toString())).build();
			
			resp = hc.send(req, HttpResponse.BodyHandlers.ofString());
			//System.out.format("Delete %s| %d\n",table,resp.statusCode());
		//if there are more than 10 ids, delete again
		}while(j < l.size());
	}
	
	public static void main(String [] args) {
		String uri = "https://api.airtable.com/v0/", apikey = "keyAKfpQ5ihTg56jn", basekey = "appiJseYJbkOuCzmZ";
		HttpClientDelete del = new HttpClientDelete(uri, apikey, basekey);
		HttpClientGet get = new HttpClientGet(uri, apikey, basekey);
		try {
			//delete all values of relation TableR
			del.deleteWithInode(get.getTableValues("TableR",10));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}				
		
	}
}
