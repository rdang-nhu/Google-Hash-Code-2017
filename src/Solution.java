import java.util.*;

public class Solution {
	public static void solution1(){
		Configuration c = new Configuration("me_at_the_zoo.in");
        HashSet<Server> empty_servers = new HashSet<Server>();
		for(int i = 0; i < c.number_servers; i++){
            empty_servers.add(c.Servers[i]);
		}
        while (!empty_servers.isEmpty()) {

            System.out.println(empty_servers.size());
            
            // Etape 1 : On prend le max au score
            Server server_max = null;
            float score_max = -1;
            for (Server s : empty_servers) {
                float current_score = s.score();
                if (current_score > score_max) {
                    score_max = current_score;
                    server_max = s;
                }
            }
            final Server server = server_max;

            // Etape 2 : on trie
            List<Video> l = server.allVideos();
            System.out.println(l);
            Collections.sort(l, new Comparator<Video>() {
                @Override
                public int compare(Video v1, Video v2) {
                    if (v1.scoreServer(server) > v2.scoreServer(server)) return -1;
                    else return 1;
                }
            });
            System.out.println(l);

            // Étape 3 : on remplit
            for (Video v : l) {
                System.out.println(v.scoreServer(server));
                server.putVideo(v);
            }

            // Étape 4 : on enlève le serveur
            empty_servers.remove(server);
        }
        
        System.out.println("Les vidéos");
		for(int i = 0; i < c.number_servers; i++){
            Server s = c.Servers[i];
            System.out.println(s.videos);
            System.out.println(s.remaining_capacity);
            
		}

        c.save("test1.out");
	}
	
	public static void main(String[] args) {
		solution1();
	}
}
