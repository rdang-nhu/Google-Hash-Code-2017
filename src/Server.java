import java.util.*;

public class Server {
    
    int id;
    int remaining_capacity;
    List<Video> videos;
    List<Connexion> connexions;
    int capacity;

    public Server(int id, int size) {
        this.id = id;
        this.connexions = new LinkedList<Connexion>();
        this.videos = new LinkedList<Video>();
        remaining_capacity = size;
        capacity = size;
    }

    public void putVideo(Video video) {
        if (video.size <= remaining_capacity) {
            for (Connexion c: connexions) {
                c.endpoint.removeVideo(video);
            }
            videos.add(video);
            remaining_capacity -= video.size;
        }
    }

    public float score() {
        float s = 0;
        for (Connexion c : connexions) {
            //s += c.endpoint.score() * (c.endpoint.latence_center - c.latence);
            s += c.endpoint.score_ameliore(capacity) * (c.endpoint.latence_center - c.latence);
        }
        return s;
    }

    public List<Video> allVideos() {
        Set<Video> s = new HashSet<Video>();
        List<Video> l = new LinkedList<Video>();
        for (Connexion c : connexions) {
            s.addAll(c.endpoint.videos);
        }
        l.addAll(s);
        return l;
    }

}



