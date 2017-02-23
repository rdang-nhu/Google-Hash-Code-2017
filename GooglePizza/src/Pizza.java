import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;

public class Pizza {
	int[][] table;
	int R, C, L, H;
	HashSet<Slice> allValidSlices;
	List<Slice> qallValidSlices;
	Slice[] allValidSlices_array;
	HashSet<Slice> res;
	HashSet<Slice> max_slice;
	HashMap<Integer, ArrayList<Integer>> neighbour;
	
	
	public Pizza(String file){
		allValidSlices = new HashSet<Slice>();
		qallValidSlices = new ArrayList<Slice>();
		max_slice = new HashSet<Slice>(); 
		res = new HashSet<Slice>();
		neighbour = new HashMap<Integer, ArrayList<Integer>>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String[] parts = line.split(" ");
			this.R = Integer.parseInt(parts[0]);
			this.C = Integer.parseInt(parts[1]);
			this.L = Integer.parseInt(parts[2]);
			this.H = Integer.parseInt(parts[3]);
			table = new int[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
			for (int i = 0; i < R; i++){
				line = br.readLine();
				for (int j = 0; j < C; j++){
					if(line.charAt(j) == 'M'){
						table[i][j] = 1;
					}
				}
			}
			br.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void allValidSlice(){
		for (int i1 = 0; i1 < R; i1++){
			for (int i2 = i1; i2 < Math.min(R, i1 + H); i2++){
				for (int j1 = 0; j1 < C; j1++){
					for (int j2 = j1; j2 < Math.min(C, j1+H); j2++){
						if ((i2-i1+1)*(j2-j1+1) <= H){
							int T_counter = 0;
							int M_counter = 0;
							for (int k = i1; k < i2 + 1 ; k++){
								for (int l = j1; l < j2 + 1 ; l++){
									if (table[k][l] == 1){
										M_counter ++;
									}
									else T_counter ++;
								}
							}
							if (M_counter >= L && T_counter >= L){
								allValidSlices.add(new Slice(i1,j1,i2,j2));
							}
						}
					}
				}
			}
		}
		allValidSlices_array = new Slice[allValidSlices.size()];
		int i = 0;
		for(Slice s : allValidSlices){
			allValidSlices_array[i] = s;
			i++;
		}
		System.out.println(allValidSlices.size());
	}
	
	public void neighbour(){
		for(int i = 0; i<allValidSlices_array.length; i++ ){
			System.out.println(i);
			ArrayList<Integer> neighb = new ArrayList<Integer>();
			for(int j=0;j<allValidSlices_array.length; j++){
				if (allValidSlices_array[i].intersect(allValidSlices_array[j])){
					neighb.add(j);
				}
			}
			neighbour.put(i, neighb);
		}
	}
	

	public void horizontal_cut(Slice s, double rho1, double rho2){
		if(s.area() < 2*L){
			return;
		}
		else if(s.isValid(this)){
			res.add(s);
		}
		else{
			int m = (s.r1+s.r2)/2;
			if (Math.random() < rho1){
				vertical_cut(new Slice(s.r1, s.c1, m, s.c2), rho1, rho2);
			}
			else{
				horizontal_cut(new Slice(s.r1, s.c1, m, s.c2), rho1, rho2);
			}
			if (Math.random() < rho1){
				vertical_cut(new Slice(m+1, s.c1, s.r2, s.c2), rho1, rho2);
			}
			else{
				horizontal_cut(new Slice(m+1, s.c1, s.r2, s.c2), rho1, rho2);
			}
		}
	}
	
	public void vertical_cut(Slice s, double rho1, double rho2){
		if(s.area() < 2*L){
			return;
		}
		else if(s.isValid(this)){
			res.add(s);
		}
		else{
			int m = (s.c1+s.c2)/2;
			if (Math.random() < rho2){
				horizontal_cut(new Slice(s.r1, s.c1, s.r2, m), rho1, rho2);
			}
			else{
				vertical_cut(new Slice(s.r1, s.c1, s.r2, m), rho1, rho2);
			}
			if (Math.random() < rho2){
				horizontal_cut(new Slice(s.r1, m+1, s.r2, s.c2), rho1, rho2);
			}
			else{
				vertical_cut(new Slice(s.r1, m+1, s.r2, s.c2), rho1, rho2);
			}
		}
	}
	
	
	
	public void cut_manager(){
		res.clear();
		this.vertical_cut(new Slice(0,0,R-1,C-1), 0.98,1);
	}
	
	public void iter_cut(int n){
		int max = 0;
		HashSet<Slice> max_slice = new HashSet<Slice>();
		for(int i=0; i<n; i++){
			cut_manager();
			if (this.area() > max){
				max = this.area();
				max_slice.clear();
				max_slice.addAll(res);
			}
		}
		System.out.println(max);
	}
	
	
	public void removeInterSlice(Slice s){
		HashSet<Slice> toRemove = new HashSet<Slice>();
		for (Slice p : qallValidSlices){
			if(p.intersect(s)){
				toRemove.add(p);
			}
		}
		qallValidSlices.removeAll(toRemove);
	}
	
	public void random(){
		while(!(qallValidSlices.isEmpty())){
			Slice s0 = qallValidSlices.get(new Random().nextInt(qallValidSlices.size()));
			res.add(s0);
			removeInterSlice(s0);
		}
	}
	
	
	
	public void glouton(){
		Random rn = new Random();
		int rand, sum_area, k;
		LinkedList<Slice> candidates = new LinkedList<Slice>();
		Slice s0 = qallValidSlices.get(new Random().nextInt(qallValidSlices.size()));
		res.add(s0);
		removeInterSlice(s0);
		while(!(qallValidSlices.isEmpty())){
			int dist = Integer.MAX_VALUE;
			int area;
			candidates.clear();
			for (Slice s : qallValidSlices){
				int new_dist = s.ListDist(qallValidSlices);
				int new_area = s.area();
				if (new_dist < dist){
					dist = new_dist;
					area = new_area;
					candidates.clear();
					candidates.add(s);
				}
				else if(new_dist == dist){
					candidates.add(s);
				}
			}
			area = Slice.listArea(candidates);
			rand = rn.nextInt(area) + 1;
			sum_area = 0;
			k=-1;
			while(sum_area < rand){
				k ++;
				sum_area += candidates.get(k).area();
			}
			s0 = candidates.get(k);
			res.add(s0);
			removeInterSlice(s0);
		}
	}
	
	public void local_search(long time){
		qallValidSlices.addAll(allValidSlices);
		qallValidSlices.removeAll(res);
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis()-startTime)/Math.pow(10, 3)<time){
			HashSet<Slice> toRemove = new HashSet<Slice>();
			Slice s0 = qallValidSlices.get(new Random().nextInt(qallValidSlices.size()));
			for (Slice p : res){
				if(p.intersect(s0)){
					toRemove.add(p);
				}
			}
			if(Slice.setArea(toRemove) < s0.area()){
				res.removeAll(toRemove);
				res.add(s0);
				qallValidSlices.remove(s0);
				qallValidSlices.addAll(toRemove);
			}
		}
		System.out.println(this.area());
	}
	
	public void local_random_search(long time, double rho, double T){
		qallValidSlices.addAll(allValidSlices);
		qallValidSlices.removeAll(res);
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis()-startTime)/Math.pow(10, 3)<time){
			HashSet<Slice> toRemove = new HashSet<Slice>();
			Slice s0 = qallValidSlices.get(new Random().nextInt(qallValidSlices.size()));
			for (Slice p : res){
				if(p.intersect(s0)){
					toRemove.add(p);
				}
			}
			int set_area = Slice.setArea(toRemove);
			if(set_area < s0.area()){
				res.removeAll(toRemove);
				res.add(s0);
				max_slice.clear();
				max_slice.addAll(res);
				qallValidSlices.remove(s0);
				qallValidSlices.addAll(toRemove);
				System.out.println(this.area());
			}
			else if (Math.random() < Math.exp((s0.area()-set_area)/rho-T)){
				res.removeAll(toRemove);
				res.add(s0);
				qallValidSlices.remove(s0);
				qallValidSlices.addAll(toRemove);
				System.out.println(this.area());
			}
		}
		
	}
	
	public void bande(){
		HashMap<List<Integer>,ArrayList<Slice>> bande = new HashMap<List<Integer>,ArrayList<Slice>>();
		int[][][] area_array = new int[R][C][H];
		int max_area;
		Slice s0;
		for (int i = 0; i<R; i++){
			for (int j = 0 ; j < R; j++){
				for(int k=0; k<H && i+k<R;k++){
					max_area = 0;
					ArrayList<Slice> toAdd = new ArrayList<Slice>();
					List<Integer> curr = Arrays.asList(i,j,k);
					if(j>0){
//						for(List<Integer> a : bande.keySet()){
//							System.out.println(Arrays.deepToString(a));
//						}
						max_area = area_array[i][j-1][k];
						List<Integer> prec0 = Arrays.asList(i,j-1,k);
						//System.out.print(Arrays.deepToString(prec0));
						//System.out.println(bande.get(prec0) == null);
						if(!(area_array[i][j-1][k] == 0)){
							toAdd.addAll(bande.get(prec0));
						}
						for (int x = L/(k+1); x<H/(k+1)+1 && j - x >=0;x++){
							s0 = new Slice(i, j-x, i+k, j);
							if(s0.isValid(this)){
								List<Integer> prec = Arrays.asList(i,j-x-1,k);
								if (bande.containsKey(prec)){
									if (max_area < s0.area() + area_array[i][j-x-1][k]){
										toAdd.clear();
										toAdd.add(s0);
										toAdd.addAll(bande.get(prec));
										bande.put(curr,toAdd);
										max_area = s0.area() + area_array[i][j-x-1][k];
										area_array[i][j][k] = max_area;
									}
								}
								else{
									toAdd.clear();
									toAdd.add(s0);
									bande.put(curr,toAdd);
									max_area = s0.area();
									area_array[i][j][k] = max_area;
								}
							}
						}
					}
					else{
						s0 = new Slice(i, 0, i+k, 0);
						System.out.print(i + " " + j +" " + k + " ");
						if(s0.isValid(this)){
							toAdd.add(s0);
							max_area = s0.area();
							area_array[i][j][k] = max_area;
						}
						
						bande.put(curr,toAdd);
						//System.out.print(Arrays.deepToString(curr));
						System.out.print(area_array[i][j][k] + " ");
						System.out.println(bande.get(curr) == null);
					}
					
				}
			}
		}
		
		int[] bande_area = new int[R];
		HashMap<Integer,ArrayList<Slice>> bande_allc = new HashMap<Integer,ArrayList<Slice>>();
		
		bande_area[0] = area_array[0][C-1][0];
		List<Integer> curr = Arrays.asList(0,C-1,0);
		bande_allc.put(0, bande.get(curr));
		
		for(int i =1; i<R; i++){
			System.out.println(i);
			ArrayList<Slice> toAdd = new ArrayList<Slice>();
			for(int k=0; k<H && i-k-1 >=0;k++){
				System.out.println(area_array[i-k][C-1][k]);
				if(bande_area[i] < bande_area[i-k-1] + area_array[i-k][C-1][k]){
					bande_area[i] = bande_area[i-k-1] + area_array[i-k][C-1][k];
					toAdd.clear();
					toAdd.addAll(bande_allc.get(i-k));
					List<Integer> curr0 = Arrays.asList(i, C-1, k);
					toAdd.addAll(bande.get(curr0));
				}
			}
			bande_allc.put(i, toAdd);
			//System.out.println(i + " size " + toAdd.size());
		}
		System.out.println(R);
		max_slice.addAll(bande_allc.get(R-1));
		res.addAll(bande_allc.get(R-1));
		System.out.println(res.size());
		System.out.println(this.area());
		
	}
	
	public int iter_random(int n){
		int max = 0;
		HashSet<Slice> max_slice = new HashSet<Slice>();
		for(int i= 0 ; i< n; i++){
			qallValidSlices.clear();
			qallValidSlices.addAll(allValidSlices);
			this.res.clear();
			this.random();
			if(this.area() > max){
				max = this.area();
				max_slice.clear();
				max_slice.addAll(res);
			}
		}
		System.out.println(max_slice.size());
		for(Slice s : max_slice){
			s.print();
		}
		return max;
	}
	
	
	public int iter(int n){
		int max = 0;
		HashSet<Slice> max_slice = new HashSet<Slice>();
		for(int i= 0 ; i< n; i++){
			qallValidSlices.clear();
			qallValidSlices.addAll(allValidSlices);
			this.res.clear();
			this.glouton();
			if(this.area() > max){
				max = this.area();
				max_slice.clear();
				max_slice.addAll(res);
			}
		}
		System.out.println(max_slice.size());
		return max;
	}
	
	
	public int area(){
		int area = 0;
		for (Slice s : this.res){
			area += (s.c2-s.c1+1)*(s.r2-s.r1+1);
		}
		return area;
	}
	
	
	public void register(String file){
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
		{
        bw.write(String.valueOf(this.res.size()));
        for (Slice s : res){
        	bw.newLine();
        	bw.write(s.r1 + " "+ s.c1 + " " + s.r2 + " " + s.c2);
        	
        }
        bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void register_max_slice(String file){
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
		{
        bw.write(String.valueOf(this.max_slice.size()));
        for (Slice s : max_slice){
        	bw.newLine();
        	bw.write(s.r1 + " "+ s.c1 + " " + s.r2 + " " + s.c2);
        	
        }
        bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void load_res(String file){
		res.clear();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String[] parts = line.split(" ");
			int n = Integer.parseInt(parts[0]);
			for (int i = 0; i < n; i++){
				line = br.readLine();
				parts = line.split(" ");
				res.add(new Slice(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
				}
			br.close();
			qallValidSlices.addAll(allValidSlices);
			qallValidSlices.removeAll(res);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String file = "big.in";
		String file_res = "big_res2.in";
		String file_load = "big_res2.in";
		Pizza pizze = new Pizza(file);
		pizze.load_res(file_load);
		//pizze.bande();
		pizze.allValidSlice();
//		pizze.load_res(file_res);
//		pizze.iter_cut(50);
		pizze.local_search(200);
		pizze.local_random_search(30, 0.4, 0.2);
		pizze.local_search(200);
//		System.out.println("change");
		pizze.local_random_search(30, 0.4, 0.2);
		pizze.register_max_slice(file_res);
		
//		System.out.println(pizze.H);
//		System.out.println(pizze.L);
//		for (Slice s : pizze.qallValidSlices){
//			s.print();
//		}
//		pizze.glouton();
//		System.out.println(pizze.area());
//		for (Slice s : pizze.res){
//			s.print();
//		}
//		System.out.println(Arrays.deepToString(pizze.table));
		
		
	}
}
