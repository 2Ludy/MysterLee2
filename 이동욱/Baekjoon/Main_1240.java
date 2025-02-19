package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1240 {

	static class Edge implements Comparable<Edge>{
		int end;
		int cost;

		public Edge(int end, int cost) {
			this.end = end;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge edge) {
			return this.cost - edge.cost;
		}
	}


	static int N, M;
	static List<Edge>[] list;
	static int[][] points;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		list = new ArrayList[N+1];
		for(int i=0; i<N+1; i++) {
			list[i] = new ArrayList<>();
		}
		points = new int[M][2];

		for(int i=1; i<=N-1; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			list[start].add(new Edge(end, cost));
			list[end].add(new Edge(start, cost));
		}

		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			points[i][0] = start;
			points[i][1] = end;
		}

		a: for(int i=0; i<M; i++) {
			int startPoint = points[i][0];
			int endPoint = points[i][1];

			boolean[] visited = new boolean[N+1];
			PriorityQueue<Edge> pq = new PriorityQueue<>();
			pq.offer(new Edge(startPoint,0));
			visited[startPoint] = true;

			while(!pq.isEmpty()) {
				Edge edge = pq.poll();
				int start = edge.end;
				int cost = edge.cost;

				if(start == endPoint) {
					System.out.println(cost);
					continue a;
				}

				for(int j=0; j<list[start].size(); j++) {
					Edge newEdge = list[start].get(j);
					int end = newEdge.end;
					int newCost = newEdge.cost;

					if(visited[end]) continue;
					visited[end] = true;
					pq.offer(new Edge(end,cost+newCost));
				}
			}
		}
	}
}

