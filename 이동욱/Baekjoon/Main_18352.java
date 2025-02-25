package baekjoon;

import java.io.*;
import java.util.*;

public class Main_18352 {
	
	static int N, M, K, X;
	static List<Integer>[] list;
	static boolean[] visited;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		list = new ArrayList[N+1];
		for(int i=0; i<N+1; i++) {
			list[i] = new ArrayList<>();
		}

		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			list[start].add(end);
		}
		int day = 0;
		Queue<Integer> que = new LinkedList<>();
		visited = new boolean[N+1];
		visited[X] = true;
		que.offer(X);

		while(!que.isEmpty()) {
			int size = que.size();
			if(day == K) {
				StringBuilder sb = new StringBuilder();
				List<Integer> tmp = new ArrayList<>();
				while(!que.isEmpty()) {
					tmp.add(que.poll());
				}
				Collections.sort(tmp);
				for(int i=0; i<tmp.size(); i++) {
					sb.append(tmp.get(i)).append("\n");
				}
				System.out.print(sb);
				return;
			}
			for(int i=0; i<size; i++) {
				int start = que.poll();
				for(int j=0; j<list[start].size(); j++) {
					int end = list[start].get(j);
					if(visited[end]) continue;
					visited[end] = true;
					que.offer(end);
				}
			}
			day++;
		}
		System.out.println(-1);
	}
}