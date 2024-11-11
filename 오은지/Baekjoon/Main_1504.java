package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 특정 두 정점을 반드시 거쳐야 하는 최단경로 -> 다익스트라 알고리즘
class Edges implements Comparable<Edges>{
	int end;
	int value;
	
	public Edges(int end, int value) {
		this.end = end;
		this.value = value;
	}
	
	@Override
	public int compareTo(Edges o) {
		return this.value - o.value;
	}	
}

public class Main_1504 { // 특정한 최단 경로
	static int N,E,a,b,c,v1,v2;
	static List<ArrayList<Edges>> graph;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 정점 개수
		E = Integer.parseInt(st.nextToken()); // 간선 개수
		
		graph = new ArrayList<>();
		for (int i = 0; i < N+1; i++) {
            graph.add(new ArrayList<>());
        }
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			graph.get(a).add(new Edges(b,c));
			graph.get(b).add(new Edges(a,c));
		}
		
		st = new StringTokenizer(br.readLine());
		// 반드시 거쳐야 하는 두 개의 서로 다른 정점 번호
		v1 = Integer.parseInt(st.nextToken());
		v2 = Integer.parseInt(st.nextToken());
		
		// 1 -> v1 -> v2 -> N
        long path1 = (long) dijkstra(1, v1) + dijkstra(v1, v2) + dijkstra(v2, N);
        // 1 -> v2 -> v1 -> N
        long path2 = (long) dijkstra(1, v2) + dijkstra(v2, v1) + dijkstra(v1, N);
        
        // 두 경로 중 최소값 선택
        long ans = Math.min(path1, path2);
       
        System.out.println(ans < 200000000 ? ans : -1);
	}
			

	static long dijkstra(int s, int e) {
		int[] parent = new int[N+1];
		Arrays.fill(parent, 200000000);
		
		PriorityQueue<Edges> pq = new PriorityQueue<>();
		pq.offer(new Edges(s, 0));
		parent[s] = 0;
		
		while(!pq.isEmpty()) {
			Edges cur = pq.poll();
			int current = cur.end;
			
			if(parent[current] < cur.value) continue;
			
			for(Edges next : graph.get(current)) {
				int cost = parent[current] + next.value;
				
				if(cost < parent[next.end]) {
					parent[next.end] = cost;
					pq.offer(new Edges(next.end, cost));
				}
			}			
		}

		return parent[e];
	}

}
