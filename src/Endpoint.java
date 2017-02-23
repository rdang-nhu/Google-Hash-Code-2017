import java.util.*;

public class Endpoint {
    
    int id;
    int latence_data_center;
    List<Request> requests;
    List<Connexion> connexions;
    int[] numbers_request;
    
    public Endpoint(int id, int latence) {
        this.id = id;
        this.latence = latence;
        requests = new LinkedList<Request>();
        connexions = new LinkedList<Connexion>();
    }

    public void connectTo(Server server, int latence) {
        Connexion c = new Connexion(server, this, latence);


}
