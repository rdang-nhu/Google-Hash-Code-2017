public class Video {

    int id;
    int size;

    public Video(int id, int size) {
        this.id = id;
        this.size = size;
    }
    
    public int scoreServer(Server S){
    	int score = 0;
    	for(Connexion connexion : S.connexions){
    		int LS = connexion.latence;
    		Endpoint E = connexion.endpoint;
    		int LD = E.latence_center;
    		int requests = E.numbers_request[id];
    		score += requests*(LD-LS);
    	}
    	return score;
    }
    
}
