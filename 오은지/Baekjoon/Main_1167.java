package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1167 { // 트리의 지름
	
	static int N,node,adj,dis, treeDia, farNode;
	static List<int[]>[] tree;
	static boolean[] visited;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		tree = new ArrayList[N+1];
		for (int i = 1; i < N+1; i++) {
			tree[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			node = Integer.parseInt(st.nextToken());
			while(true) {
				adj = Integer.parseInt(st.nextToken());
				if(adj == -1) break;
				dis = Integer.parseInt(st.nextToken());
				tree[node].add(new int[] {adj, dis});
			}
		}
		
		farNode = bfs(1)[0];
		treeDia = bfs(farNode)[1];
		System.out.println(treeDia);
	}

	static int[] bfs(int start) {
		Queue<int[]> que = new LinkedList<>();
		visited = new boolean[N+1];
		
		que.add(new int[] {start,0});
		visited[start] = true;
		
		int farNode = start;
		int treeDia = 0;
		
		while(!que.isEmpty()) {
			int[] cur = que.poll();
			int node = cur[0];
			int dis = cur[1];
			
			if(dis > treeDia) {
				treeDia = dis;
				farNode = node;
			}
			
			for (int i = 0; i < tree[node].size(); i++) {
				int[] next = tree[node].get(i);
				int nextNode = next[0];
				int nextDis = next[1];
				
				if(!visited[nextNode]) {
					visited[nextNode] = true;
					que.add(new int[] {nextNode, dis+nextDis});
				}
			}
		}
		return new int[] {farNode, treeDia};
	}
	
}
