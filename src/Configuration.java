import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class Configuration {
	
	int number_videos;
	int number_endpoints;
	int number_request_descriptions;
	int number_servers;
	int capacity;
	
	Video[] Videos;
	Endpoint[] Endpoints;
	Server[] Servers;
	
	public Configuration(String file){
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line = br.readLine();
			String[] parts = line.split(" ");
			
			this.number_videos = Integer.parseInt(parts[0]);
			this.number_endpoints = Integer.parseInt(parts[1]);
			this.number_request_descriptions = Integer.parseInt(parts[2]);
			this.number_servers = Integer.parseInt(parts[3]);
			this.capacity = Integer.parseInt(parts[4]);
			
			Videos = new Video[number_videos];
			Endpoints = new Endpoint[number_endpoints];
			Servers = new Server[number_servers];
			
			line = br.readLine();
			parts = line.split(" ");
			for(int i = 0; i<number_videos; i++){
				Videos[i] = new Video(i, Integer.parseInt(parts[i]));
			}
			
			for(int i=0; i<number_servers; i++){
				Servers[i] = new Server(i);
			}
			
			for(int i = 0; i<number_servers; i++){
				line = br.readLine();
				String[] description = line.split(" ");
				Endpoints[i] = new Endpoint(i, Integer.parseInt(description[0]),number_videos);
				for(int j = 0; i<Integer.parseInt(description[1]); i++){
					String lat = br.readLine();
					String[] lat_des = lat.split(" ");
					Endpoints[i].connectTo(Servers[Integer.parseInt(lat_des[0])], Integer.parseInt(lat_des[1]));
				}
			}
			
			for(int i=0; i<number_request_descriptions; i++){
				line = br.readLine();
				String[] res_des = line.split(" ");
				Endpoints[Integer.parseInt(res_des[1])].addRequest(Videos[Integer.parseInt(res_des[0])], Integer.parseInt(res_des[2]));;	
			
			br.close();}
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
	
