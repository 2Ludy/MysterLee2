package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_18352 { // 특정 거리의 도시 찾기
	
	static int N,M,K,X;
	static List<Integer>[] node;
	static int[] visited;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		node = new ArrayList[N+1];
		for (int i = 1; i < N+1; i++) {
			node[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int num1 = Integer.parseInt(st.nextToken());
			int num2 = Integer.parseInt(st.nextToken());
			node[num1].add(num2);
		}
		
		visited = new int[N+1];
		Arrays.fill(visited, -1);
		
		bfs(X);
		
		boolean check = false;
		for (int i = 0; i < N+1; i++) {
			if(visited[i] == K) {
				System.out.println(i);
				check = true;
			}			
		}
		if(!check) System.out.println(-1);
	}

	static void bfs(int start) {
		Queue<Integer> que = new LinkedList<>();
		visited[start] = 0;
		que.offer(start);
		
		while(!que.isEmpty()) {
			int cur = que.poll();
			
			for (int i = 0; i < node[cur].size(); i++) {
				int next = node[cur].get(i);
				
				if(visited[next] == -1) {
					visited[next] = visited[cur]+1;
					que.offer(next);
				}
			}
		}
	}
		
}
