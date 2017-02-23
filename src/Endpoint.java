import java.util.*

public class Endpoint {
    
    int id;
    List<Request> requests;
    List<Connexion> connexions;
    
    public Endpoint(int id, List<Request> requests) {
        this.id = id;
        this.requests = requests;
        this.connexions = new LinkedList<Connexion>();
    }

}
