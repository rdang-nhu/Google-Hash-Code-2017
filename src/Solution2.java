import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class Solution2 {
	
	public static void solution2(String file){
		Configuration c = new Configuration(file + ".in");
        HashSet<Server> empty_servers = new HashSet<Server>();
        c.load("me_at_the_zoo.out");
		long temps0 = System.currentTimeMillis();
		long temps = System.currentTimeMillis();
		while(temps-temps0 < 1000){
			LocalSearch.Localsearch(c);
			temps = System.currentTimeMillis();
			
		}

        c.save(file+ "1"+ ".out");
		
	}
	
	public static void main(String[] args) {
		solution2("me_at_the_zoo");
	}
}
