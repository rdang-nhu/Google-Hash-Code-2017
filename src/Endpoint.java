import java.util.*;

public class Endpoint {
    
    int id;
    int latence_center;
    List<Connexion> connexions;
    HashSet<Video> videos;
    int[] number_request;
    
    public Endpoint(int id, int latence, int nb_videos) {
        this.id = id;
        latence_center = latence;
        connexions = new LinkedList<Connexion>();
        number_request = new int[nb_videos];
    }

    public void connectTo(Server server, int latence) {
        Connexion c = new Connexion(server, this, latence);
        connexions.add(c);
        server.connexions.add(c);
    }

    public void addRequest(Video video, int nb) {
        number_request[video.id] += nb;
        videos.add(video);
    }
    
    public void removeVideo(Video video) {
        number_request[video.id] = 0;
        videos.remove(video);
    }

    public int score() {
        int s = 0;
        for (Video video : videos) {
            s += number_request[video.id] / video.size;
        }
        return s;
    }

}
