import java.util.*

public class Endpoint {
    
    int id;
    List<Request> requests;
    
    public Endpoint(int id, List<Request> requests) {
        this.id = id;
        this.requests = requests;
    }

}
