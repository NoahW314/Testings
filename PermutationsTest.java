import java.util.ArrayList;

public class PermutationsTest {
	public static void main(String[] args) {
		for(int i = 0; i < N; i++) {
			partPerms[i] = 0;
		}
		iterate(0);
		System.out.println(perms);
	}
	
	static int level = -1;
	static int N = 3;
	static ArrayList<String> perms = new ArrayList<String>(fact(N));
	static Integer[] partPerms = new Integer[N];
	
	static void iterate(int i){
		level++;
		partPerms[i] = level;
		if(level == N) {addItem();}
		else {
			for(int j = 0; j < N; j++) {
				if(partPerms[j] == 0) {
					iterate(j);
				}
			}
		}
		level = level-1;
		partPerms[i] = 0;
	}
	static void addItem() {
		String str = "";
		for(int k = 0; k < partPerms.length; k++) {
			str+=Integer.toString(partPerms[k]);
		}
		perms.add(str);
	}
	static int fact(int N) {
		if(N <= 1){return N;}
		return N*fact(N-1);
	}
}