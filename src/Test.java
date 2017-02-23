
public class Test {
	public static void test1(){
		Configuration c = new Configuration("kittens.in");
		for(int i = 0; i < c.number_servers; i++){
			System.out.println(c.Servers[i].score());
		}
	}
	
	public static void main(String[] args) {
		test1();
	}
}
