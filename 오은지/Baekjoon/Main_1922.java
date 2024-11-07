package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Edge implements Comparable<Edge>{
	int start;
	int end;
	int value;
	
	public Edge(int start, int end, int value) {
		this.start = start;
		this.end = end;
		this.value = value;
	}
	
	@Override
	public int compareTo(Edge o) {
		return this.value - o.value;
	}	
}

public class Main_1922 { // 네트워크 연결
	static int N,M,a,b,c;
	static int[] parent;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // 컴퓨터 수
		M = Integer.parseInt(br.readLine()); // 선의 수
		PriorityQueue<Edge> edges = new PriorityQueue<>();
		
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			edges.add(new Edge(a,b,c));
		}
		
		parent = new int[N+1];
		for (int i = 1; i < N+1; i++) {
			parent[i] = i;
		}
		
		int ans = 0;
		int useEdge = 0;
		
		while(useEdge < N-1) {
			Edge edge = edges.poll();
			int s = edge.start;
			int e = edge.end;
			
			if(find(s) != find(e)) {
				union(s,e);
				ans += edge.value;
				useEdge++;
			}
		}
		
		System.out.println(ans);
			
	}

	static void union(int x, int y) {
		x = find(x);
		y = find(y);
		if(x != y) parent[y] = x;
	}

	static int find(int z) {
		if(z == parent[z]) return z;
		else return parent[z] = find(parent[z]);
	}

}
