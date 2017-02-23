import java.util.*;

public class Server {
    
    int id;
    List<Video> videos;
    List<Connexion> connexions;

    public Server(int id, int taille) {
        this.id = id;
        this.connexions = new LinkedList<Connexion>();
        this.videos = new LinkedList<Video>();
    }

    public void putVideo(Video video) {
        for (Connexion c: connexions) {
            c.endpoint.removeVideo(video);
        }
        videos.add(video);
    }
    
    

    public float score() {
        float s = 0;
        for (Connexion c : connexions) {
            s += c.endpoint.score();
        }
        return s;
    }
}



