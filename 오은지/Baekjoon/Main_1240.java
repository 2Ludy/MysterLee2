package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1240 { // 노드사이의 거리
	static int N, M;
	static List<int[]>[] tree;
	
	public static void main(String[] args) throws Exception {
		// N개 노드, M개의 쌍
		// 두 노드 사이의 거리 출력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		tree = new ArrayList[N+1];
		for (int i = 1; i < N+1; i++) {
			tree[i] = new ArrayList<>();
		}
		
		// N-1개 줄에 연결된 두 점, 거리
		for (int i = 0; i < N-1; i++) {
			st = new StringTokenizer(br.readLine());
			int p1 = Integer.parseInt(st.nextToken());
			int p2 = Integer.parseInt(st.nextToken());
			int dis = Integer.parseInt(st.nextToken());
			
			tree[p1].add(new int[] {p2, dis});
			tree[p2].add(new int[] {p1,dis});
		}
		
		// M개 줄에 거리를 알고 싶은 노드 쌍
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			sb.append(bfs(start,end)).append("\n");
		}
		System.out.println(sb.toString());
	}

	static int bfs(int start, int end) {
		Queue<int[]> que = new LinkedList<>();
		boolean[] visited = new boolean[N+1];
	
		que.add(new int[] {start,0});
		visited[start] = true;
		
		while(!que.isEmpty()) {
			int[] cur = que.poll();
			int node = cur[0];
			int dis = cur[1];
			
			if(node == end) return dis;
			
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
		
		return -1;
	}
}
