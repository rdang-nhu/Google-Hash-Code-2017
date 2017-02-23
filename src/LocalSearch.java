import java.util.LinkedList;
import java.util.Random;

public class LocalSearch {
	
	public static void Localsearch(Configuration c){
		while(){
			Request req = c.Requests[new Random().nextInt(c.Requests.length)];
			Server s = req.endpoint.connexions.get(new Random().nextInt(req.endpoint.connexions.size())).server;
			if(req.size<c.capacity){
				int delta_score = 0;
				LinkedList<Video> toRemove = new LinkedList<Video>();
				while(s.remaining_capacity < req.video.size){
					Video v = s.videos.get(new Random().nextInt(s.videos.size()));
					delta_score -= v.scoreLocalSearch(s);
				}
			}
		}
	}
}
