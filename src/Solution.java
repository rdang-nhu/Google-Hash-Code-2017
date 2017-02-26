import java.util.*;

public class Solution {

	public static void bourrin(String file){
		Configuration c = new Configuration(file + ".in");
    
        HashSet<Couple> couples = new HashSet<Couple>();
		for(int i = 0; i < c.number_servers; i++){
            Server s = c.Servers[i];
            for (Video v : s.allVideos()) {
                couples.add(new Couple(s, v));
            }
		}
		
		Couple Last_Couple = null;
		
        while (!couples.isEmpty()) {
            System.out.println(couples.size());

            // Etape 1 : On prend le max au score
            Couple couple_max = couples.iterator().next();
            float score_max = couple_max.score();
            for (Couple current : couples) {
                float current_score = current.memoizedScore(Last_Couple); //modifié en memoizedScore
                if (current_score > score_max) {
                    score_max = current_score;
                    couple_max = current;
                }
            }
            couple_max.putVideo();
            Last_Couple = couple_max;
            couples.remove(couple_max);
        }
        
        c.save(file + ".out");
	}

    public static void knapsack(String file){
		Configuration c = new Configuration(file + ".in");
        HashSet<Server> empty_servers = new HashSet<Server>();
		for(int i = 0; i < c.number_servers; i++){
            empty_servers.add(c.Servers[i]);
		}
        while (!empty_servers.isEmpty()) {
            System.out.println(empty_servers.size());

            // Etape 1 : On prend le max au score
            Server server_max = empty_servers.iterator().next();
            float score_max = server_max.score();
            for (Server s : empty_servers) {
                float current_score = s.score();
                if (current_score > score_max) {
                    score_max = current_score;
                    server_max = s;
                }
            }
            final Server server = server_max;

            // Etape 2 : on prend les meilleurs vidéos
            List<Video> l = Knapsack.solve(server);
 
            // Étape 3 : on remplit
            for (Video v : l) {
                server.putVideo(v);
            }

            // Étape 4 : on enlève le serveur
            empty_servers.remove(server);
        }
        
        c.save(file + ".out");
	}
	
	public static void solution1(String file){
		Configuration c = new Configuration(file + ".in");
        HashSet<Server> empty_servers = new HashSet<Server>();
		for(int i = 0; i < c.number_servers; i++){
            empty_servers.add(c.Servers[i]);
		}
        while (!empty_servers.isEmpty()) {
            System.out.println(empty_servers.size());

            // Etape 1 : On prend le max au score
            Server server_max = empty_servers.iterator().next();
            float score_max = server_max.score();
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

        c.save(file + ".out");
		
	}

    public static void time(String file) {
        long t0 = System.currentTimeMillis();
        solution1(file);
        long t1 = System.currentTimeMillis();
        solution2(file);
        long t2 = System.currentTimeMillis();
        bourrin(file);
        long t3 = System.currentTimeMillis();
        System.out.println(t1 - t0);
        System.out.println(t2 - t1);
        System.out.println(t3 - t2);
    }
	
	public static void main(String[] args) {
        long t0 = System.currentTimeMillis(); 
        //knapsack("me_at_the_zoo");
        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis();
        knapsack("kittens");
        long t3 = System.currentTimeMillis();
        System.out.println(t1 - t0);
        System.out.println(t2 - t1);
        System.out.println(t3 - t2);

        for (String s : args) System.out.println(s);
		// bourrin("videos_worth_spreading"); // Un peu long
		//bourrin("kittens"); // Trop long
		//bourrin("trending_today"); // Pareil
	}
}
