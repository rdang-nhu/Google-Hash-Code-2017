import java.util.LinkedList;
import java.util.Random;

public class LocalSearch {
	
	public static void Localsearch(Configuration c){
		
		Request req = c.Requests[new Random().nextInt(c.Requests.length)];
		Server s = req.endpoint.connexions.get(new Random().nextInt(req.endpoint.connexions.size())).server;
		if(req.size<c.capacity && !s.videos.contains(req.video)){
			int delta_score = 0;
			LinkedList<Video> toRemove = new LinkedList<Video>();
			int freesize = s.remaining_capacity;
			while( freesize < req.video.size){
				Video v = s.videos.get(new Random().nextInt(s.videos.size()));
				if(!toRemove.contains(v)){
					delta_score -= v.scoreLocalSearch(s);
					
					toRemove.add(v);
					freesize += v.size;
				}
			}
			delta_score += req.video.scoreLocalSearch(s);
			//System.out.println(req.video.scoreLocalSearch(s));
			if(delta_score > 0){
				s.videos.removeAll(toRemove);
				s.videos.add(req.video);
				s.remaining_capacity = freesize;
			}
		}
		
	}
}
