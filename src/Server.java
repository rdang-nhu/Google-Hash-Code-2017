import java.util.*;

public class Server {
    
    int id;
    List<Connexion> connexions;

    public Server(int id) {
        this.id = id;
        this.connexions = new LinkedList<Connexion>();
    }

}
