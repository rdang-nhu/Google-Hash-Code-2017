
public class Connexion {
	Server server;
	Endpoint endpoint;
	int latence;
	
	Connexion(Server server, Endpoint endpoint, int lat){
		this.server = server;
		this.endpoint = endpoint;
		latence = lat;
	}
	
	
}
