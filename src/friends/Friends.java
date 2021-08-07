package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

		int p1Num, p2Num;
		try {
			p1Num = g.map.get(p1);
			p2Num = g.map.get(p2);
		}
		catch(Exception e) {
			return null;
		}
		if(p1Num == p2Num) { //no self loops allowed
			return null;
		}
		boolean[] visited = new boolean[g.members.length];
		int[] prevs = new int[g.members.length];
		Queue<Integer> q = new Queue<>();
		visited[p1Num] = true;
		System.out.println("visiting " + g.members[p1Num].name);
		q.enqueue(p1Num);
		
		while(!q.isEmpty()) {
			int v = q.dequeue();
			for(Friend friend = g.members[v].first; friend != null; friend = friend.next) { 
				int vnum = friend.fnum;
				if(!visited[vnum]) {
					System.out.println("\n" + g.members[v].name + "--" + g.members[vnum].name);
					visited[vnum] = true;
					q.enqueue(vnum);
					prevs[vnum] = v;
					if(vnum == p2Num) {
						break;
					}
				}
			}
		}
		if(!visited[p2Num]) {
			return null;
		}
		int index = p2Num;
		Stack<Integer> path = new Stack<>();
		while(true) {
			path.push(index);
			if(index == p1Num) {
				break;
			}
			index = prevs[index];
		} 
		
		ArrayList<String> shortestPath = new ArrayList<>();
		while(!path.isEmpty()) {
			shortestPath.add(g.members[path.pop()].name);
		}
		
		return shortestPath;
	}
	
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		boolean[] visited = new boolean[g.members.length];
		ArrayList<ArrayList<String>> cliques = new ArrayList<>();
		Queue<Integer> q = new Queue<>();
		
		for(int v = 0; v < visited.length; v++) {
			if(!visited[v]) {
				ArrayList<String> clique = new ArrayList<String>();
				visited[v] = true;
				if(g.members[v].school != null && g.members[v].school.equals(school)) {
					clique.add(g.members[v].name);
					q.enqueue(v);
					
					while(!q.isEmpty()) {
						int v2 = q.dequeue();
						for(Friend friend = g.members[v2].first; friend != null; friend = friend.next) {
							int vnum = friend.fnum;
							if(!visited[vnum]) {
								visited[vnum] = true;
								if(g.members[vnum].school != null && g.members[vnum].school.equals(school)) {
									clique.add(g.members[vnum].name);
									q.enqueue(vnum);
								}
							} 
						}
					}
					
				}
				if(clique.size() != 0) {
					cliques.add(clique);
				}
			}
		}
		return cliques;
		
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {

		int num = 1;
		int numVertices = g.members.length;
		
		int[] prevs = new int[numVertices];
		int[] dfsnum = new int[numVertices];
		int[] back = new int[numVertices];
		
		boolean[] visited = new boolean[numVertices];
		
		ArrayList<String> connectors = new ArrayList<>();
		
		for(int i = 0; i < prevs.length; i++) {
			prevs[i] = -1;
		}
		
		for(int i = 0; i < visited.length; i++) {
			if(!visited[i]) {
				findConnectors(g, i, num, dfsnum, back, prevs, visited, connectors);
			}
		}
		
		return connectors;
	}
	
	private static void findConnectors(Graph g, int v, int num, int[] dfsnum, int[] back, int[] prevs, boolean[] visited, ArrayList<String> connectors) {
		int children = 0;
		visited[v] = true;
		
		dfsnum[v] = num;
		back[v] = num;
		++num;
		
		for(Friend friend = g.members[v].first; friend != null; friend = friend.next) {
			int fnum = friend.fnum;
			
			if(!visited[fnum]) {
				++children;
				prevs[fnum] = v;
				
				findConnectors(g, fnum, num, dfsnum, back, prevs, visited, connectors);
				
				if(visited[v]) {
					back[v] = Math.min(back[v], back[fnum]);
				}
				if((prevs[v] == -1 && children > 1) || (prevs[v] != -1 && back[fnum] >= dfsnum[v])) {
					if(!connectors.contains(g.members[v].name)) {
						connectors.add(g.members[v].name);
					}
				}
			}
			else if(fnum != prevs[v]) {
				back[v] = Math.min(back[v], dfsnum[fnum]);
			}
	}
	
}
}

