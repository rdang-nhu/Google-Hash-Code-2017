public class Video {

    int id;
    int size;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Video)) {
            return false;
        }

        Video video = (Video) o;

		return id == video.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Video(int id, int size) {
        this.id = id;
        this.size = size;
    }
    
    public float scoreServer(Server S){
    	int score = 0;
    	for(Connexion connexion : S.connexions){
    		int LS = connexion.latence;
    		Endpoint E = connexion.endpoint;
    		int LD = E.latence_center;
    		int requests = E.number_request[id];
    		score += requests*(LD-LS);
    	}
    	return ((float) score / size);
    }

    public String toString() {
        return "" + id;
    }

    public float scoreLocalSearch(Server S){
    	int score = 0;
    	for(Connexion connexion : S.connexions){
    		int LS = connexion.latence;
    		Endpoint E = connexion.endpoint;
    		int LD = E.latence_center;
    		int requests = E.number_request[id];
    		score += requests*(LD-LS);
    	}
    	return score;
    }
    
}
