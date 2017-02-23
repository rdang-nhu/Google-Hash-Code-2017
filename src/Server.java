import java.util.*;

public class Server {
    
    int id;
    int taille;
    List<Connexion> connexions;

    public Server(int id, int taille) {
        this.id = id;
        this.taille = taille;
        this.connexions = new LinkedList<Connexion>();
    }



