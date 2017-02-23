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
	Request[] Requests;
	
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
			
			System.out.println(number_request_descriptions);
			
			Videos = new Video[number_videos];
			Endpoints = new Endpoint[number_endpoints];
			Servers = new Server[number_servers];
			Requests = new Request[number_request_descriptions];
			
			line = br.readLine();
			parts = line.split(" ");
			for(int i = 0; i<number_videos; i++){
				Videos[i] = new Video(i, Integer.parseInt(parts[i]));
			}
			
			for(int i=0; i<number_servers; i++){
				Servers[i] = new Server(i, capacity);
			}
			
			for(int i = 0; i<number_servers; i++){
				line = br.readLine();
				String[] description = line.split(" ");
				Endpoints[i] = new Endpoint(i, Integer.parseInt(description[0]),number_videos);
				for(int j = 0; j<Integer.parseInt(description[1]); j++){
					String lat = br.readLine();
					String[] lat_des = lat.split(" ");
					Endpoints[i].connectTo(Servers[Integer.parseInt(lat_des[0])], Integer.parseInt(lat_des[1]));
				}
			}
			
			for(int i=0; i<number_request_descriptions; i++){
				line = br.readLine();
				String[] res_des = line.split(" ");
				Endpoints[Integer.parseInt(res_des[1])].addRequest(Videos[Integer.parseInt(res_des[0])], Integer.parseInt(res_des[2]));
				Requests[i] = new Request(Integer.parseInt(res_des[2]), Videos[Integer.parseInt(res_des[0])], Endpoints[Integer.parseInt(res_des[1])]);
			}
			
			
			br.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(String file_res){
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file_res))){
			bw.write(number_servers);
			for(int i = 0; i< this.number_servers; i++){
				bw.newLine();
				bw.write(i);
				for(int j = 0; j< Servers[i].videos.size(); j++){
					bw.write(Servers[i].videos.get(j).id);
					bw.write(" ");
				}
			}
			bw.close();
		}
		catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(String file_load){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file_load));
			String line = br.readLine();
			for(int i =0; i<number_servers; i++){
				line = br.readLine();
				String[] parts = line.split(" ");
				int num_serv = Integer.parseInt(parts[0]);
				for(int j = 1; j< parts.length; j++){
					Servers[num_serv].putVideo(Videos[Integer.parseInt(parts[j])]);
				}
			}
			br.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
	
