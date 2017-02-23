import java.util.*;

public class Endpoint {
    
    int id;
    int latence_center;
    List<Connexion> connexions;
    HashSet<Integer> videos;
    int[] numbers_request;
    
    public Endpoint(int id, int latence, int nb_videos) {
        this.id = id;
        latence_center = latence;
        connexions = new LinkedList<Connexion>();
        numbers_request = new int[nb_videos];
    }

    public void connectTo(Server server, int latence) {
        Connexion c = new Connexion(server, this, latence);
        connexions.add(c);
        server.connexions.add(c);
    }

    public void addRequest(Video video, int nb) {
        numbers_request[video.id] += nb;
        videos.add(video.id);
    }
    
    public void removeVideo(Video video) {
        numbers_request[video.id] = 0;
        videos.remove(video.id);
    }

    public int score() {
        int s = 0;
        
        return s;
    }

}
