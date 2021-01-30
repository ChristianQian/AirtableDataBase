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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientGet {
	String uri, apikey, basekey;
	private final HttpClient hc = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2).build();
	
	public HttpClientGet(String uri, String apikey, String basekey) {
		this.uri = uri;
		this.apikey = apikey;
		this.basekey = basekey;
	}
	
	//get values of table with size tabletaille
	public int [] getTableValues(String table, int tabletaille) throws IOException, InterruptedException {
		int[] t = new int[tabletaille];
		HttpRequest req = HttpRequest.newBuilder().GET()
				.uri(URI.create(uri+basekey+"/"+table+"?api_key="+apikey)).build();
		HttpResponse<String> resp = hc.send(req, HttpResponse.BodyHandlers.ofString());
		
		int i = 0;
		Matcher m = Pattern.compile("\"Name\":\"(\\d+)\"").matcher(resp.body());
		while (m.find() && i < t.length) 
			t[i++] = Integer.parseInt(m.group(1));
			
		return t;
	}
	
	//get all records id, mainly to delete them
	public List<String> getTableId(String table) throws IOException, InterruptedException {
		HttpRequest req = HttpRequest.newBuilder().GET()
				.uri(URI.create(uri+basekey+"/"+table+"?api_key="+apikey)).build();
		HttpResponse<String> resp = hc.send(req, HttpResponse.BodyHandlers.ofString());
		Matcher m = Pattern.compile("\"id\":\"([A-Za-z0-9]+)\"").matcher(resp.body());
		List<String> t = new ArrayList<>();

		while (m.find()) 
			t.add(m.group(1));
			
		return t;
	}

	
	public static void main(String [] args){
		String uri = "https://api.airtable.com/v0/", apikey = "keyAKfpQ5ihTg56jn", basekey = "appiJseYJbkOuCzmZ";
		HttpClientGet get = new HttpClientGet(uri, apikey, basekey);
		int[] r;
		try {
			r = get.getTableValues("B01",10);
			for(int i : r)
				System.out.println(i);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
