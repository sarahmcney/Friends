package friends;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class FriendsDriver {
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("assnsample.txt");
		Scanner sc = new Scanner(file);
		Graph g = new Graph(sc);
		ArrayList<String> sp = Friends.shortestChain(g, "nick", "aparna");
		if(sp == null) {
			System.out.println("null");
		}
		else {
			System.out.println(sp);
		}
		
		ArrayList<ArrayList<String>> cliques = Friends.cliques(g, "rutgers");
		System.out.println(cliques);
		
		ArrayList<String> connectors = Friends.connectors(g);
		System.out.println(connectors);
	}
}
