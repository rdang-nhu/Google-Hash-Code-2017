import java.util.*;

public class Solution {
	public static void solution1(String file){
		Configuration c = new Configuration(file + ".in");
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
            Collections.sort(l, new Comparator<Video>() {
                @Override
                public int compare(Video v1, Video v2) {
                    if (v1.scoreServer(server) > v2.scoreServer(server)) return -1;
                    else return 1;
                }
            });

            // Étape 3 : on remplit
            for (Video v : l) {
                server.putVideo(v);
            }

            // Étape 4 : on enlève le serveur
            empty_servers.remove(server);
        }
        
		for(int i = 0; i < c.number_servers; i++){
            Server s = c.Servers[i];
            
		}

        c.save(file + ".out");
	}
	
	public static void solution2(String file){
		Configuration c = new Configuration(file + ".in");
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
            Collections.sort(l, new Comparator<Video>() {
                @Override
                public int compare(Video v1, Video v2) {
                    if (v1.scoreServer(server) > v2.scoreServer(server)) return -1;
                    else return 1;
                }
            });

            // Étape 3 : on remplit
            for (Video v : l) {
                server.putVideo(v);
            }

            // Étape 4 : on enlève le serveur
            empty_servers.remove(server);
        }
        
		long temps0 = System.currentTimeMillis();
		long temps = System.currentTimeMillis();
		while(temps-temps0 < 1000){
			LocalSearch.Localsearch(c);
			temps = System.currentTimeMillis();
			
		}
		for(int i = 0; i < c.number_servers; i++){
            Server s = c.Servers[i];
            
		}

        c.save(file + ".out");
		
	}
	
	public static void main(String[] args) {

		// solution2("me_at_the_zoo");
		solution2("trending_today");
		// solution1("videos_worth_spreading");
		// solution1("kittens");

		//solution1("me_at_the_zoo");
		//solution1("trending_today");
		//solution1("videos_worth_spreading");
		solution1("kittens");
	}
}
