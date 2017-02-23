import java.util.*;

public class Server {
    
    int id;
    List<Connexion> connexions;

    public Server(int id) {
        this.id = id;
        this.connexions = new LinkedList<Connexion>();
    }

    public void putVideo(Video video) {
        for (Connexion c: connexions) {
            c.endpoint.removeVideo(video);
        }
    }

}
