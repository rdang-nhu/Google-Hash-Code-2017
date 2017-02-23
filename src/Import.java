import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Import {
	
	
	public Import(String file){
		
		
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
	}
}
