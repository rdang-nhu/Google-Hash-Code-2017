import java.util.*;

public class Server {
    
    int id;
    List<Connexion> connexions;

    public Server(int id) {
        this.id = id;
        this.connexions = new LinkedList<Connexion>();
    }

    public connectTo(Endpoint endpoint, int latence) {
        Connexion c = new Connexion(this, endpoint, latence);
        connexions.add(c);
    }
}
