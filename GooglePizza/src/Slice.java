import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Slice {
	int c1, c2, r1, r2;
	
	public Slice(int r1, int c1, int r2, int c2){
		this.c1 = c1;
		this.c2 = c2;
		this.r1 = r1;
		this.r2 = r2;
	}
	
	public boolean intersect(Slice s){
		if (s.r1 > r2 || s.r2 < r1 || s.c1 > c2 || s.c2 < c1){
			return false;
		}
		else return true;
	}
	
	public boolean isValid(Pizza p){
		if(this.area() > p.H){
			return false;
		}
		else{
			int T_counter = 0;
			int M_counter = 0;
			for (int i = r1; i <= r2; i++){
				for (int j = c1; j <= c2; j++){
					if (p.table[i][j] == 1){
						M_counter ++;
					}
					else T_counter++;
				}
			}
			return(T_counter >= p.L && M_counter >= p.L);
		}
	}
	
	public int SetDist(Set<Slice> s){
		int res = Integer.MAX_VALUE;
		for (Slice p : s){
			res = Math.min(res, this.distance(p));
		}
		return res;
	}
	
	public int ListDist(List<Slice> s){
		int res = Integer.MAX_VALUE;
		for (Slice p : s){
			res = Math.min(res, this.distance(p));
		}
		return res;
	}
	
	public int area(){
		return((r2-r1+1)*(c2-c1+1));
	}
	
	public int distance(Slice s){
		if (this.intersect(s)) return 0;
		else{
			if ((s.r1 <= r1 && r1 <= s.r2) || (s.r1 <= r2 && r2 <= s.r2) || (r1 <= s.r1 && s.r1 <= r2) || (r1 <= s.r2 && s.r2 <= r2)){
				return((int)(Math.min(Math.abs(s.c1-c2), Math.abs(s.c2-c1))));
			}
			else if((s.c2 <= c1 && c1 <= s.c1) || (s.c2 <= c2 && c2 <= s.c1) || (c2 <= s.c1 && s.c1 <= c1) || (c2 <= s.c2 && s.c2 <= c1)){
				return((int)(Math.min(Math.abs(s.r1-r2), Math.abs(s.r2-r1))));
			}
			else{
				return((int)(Math.min(Math.abs(s.c1-c2), Math.abs(s.c2-c1)))+(int)(Math.min(Math.abs(s.r1-r2), Math.abs(s.r2-r1))));
			}
		}
	}
	public void print(){
		System.out.println(r1 + " " + c1 + " " + r2 + " "+ c2);
	}
	
	public static int setArea(Set<Slice> s){
		int area = 0;
		for(Slice p : s){
			area += p.area();
		}
		return area;
	}
	
	public static int listArea(List<Slice> s){
		int area = 0;
		for(Slice p : s){
			area += p.area();
		}
		return area;
	}
	
	

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    Slice s = (Slice) obj;
	    return(r1 == s.r1 && r2 == s.r2 && c1 == s.c1 && c2 == s.c2);
	}

	
}
