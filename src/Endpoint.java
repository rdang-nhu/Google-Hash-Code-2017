import java.util.*;

public class Endpoint {
    
    int id;
    int latence_center;
    List<Connexion> connexions;
    HashSet<Video> videos;
    int[] number_request;
    
    public Endpoint(int id, int latence, int nb_videos) {
        this.id = id;
        latence_center = latence;
        connexions = new LinkedList<Connexion>();
        number_request = new int[nb_videos];
        videos = new HashSet<Video>();
    }

    public void connectTo(Server server, int latence) {
        Connexion c = new Connexion(server, this, latence);
        connexions.add(c);
        server.connexions.add(c);
    }

    public void addRequest(Video video, int nb) {
        number_request[video.id] += nb;
        videos.add(video);
    }
    
    public void removeVideo(Video video) {
        number_request[video.id] = 0;
        videos.remove(video);
    }

    public float score() {
        int s = 0;
        for (Video video : videos) {
            s += (float) number_request[video.id] / video.size;
        }
        if (videos.size() == 0) return 0;
        return (float) (s / videos.size());
    }

    public int score_ameliore(int capacity) {
        // Étape 1 : on trie par nb / size
        List<Video> l = new LinkedList<Video>();
        l.addAll(videos);
        Collections.sort(l, new Comparator<Video>() {
            @Override
            public int compare(Video v1, Video v2) {
                float r1 = (float) number_request[v1.id] / v1.size;
                float r2 = (float) number_request[v2.id] / v2.size;
                if (r1 > r2) return -1;
                else return 1;
            }
        });

        int remaining_space = capacity;
        int s = 0;
        for (Video v : l) {
            if (v.size <= remaining_space) {
                s += number_request[v.id];
                remaining_space -= v.size;
            }
        }
        return s;
    }


}
