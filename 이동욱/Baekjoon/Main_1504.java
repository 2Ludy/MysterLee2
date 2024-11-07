// https://velog.io/@2ludy/boj1504
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1504 {
	static class Edge {  // 다익스트라를 돌리기 위해 만든 간선의 끝점과 거리를 나타내는 클래스
		int e;
		int c;
		
		public Edge(int e, int c) {
			this.e = e;
			this.c = c;
		}
	}
	
	static int N, E;
	static int v1, v2; // 반드시 들려야 하는 좌표
	static List<Edge>[] list; // 인접 리스트를 이용하여 시작점을 index로 잡고 edge의 정보들을 넣어줌
	static boolean[] visited; // 방문 했는지 체크하기 위한 배열
	static int[] cost; // 다익스트라를 돌릴때 사용할 비용 배열
	static int max = 200000001; // 간선의개수 200,000개 x 최대거리 1,000 = 2,000,000,000 이므로 최대값을 +1 해서 잡아줌
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N+1];
		for(int i=0; i<N+1; i++) {
			list[i] = new ArrayList<>();
		} // 인접리스트 배열 초기화 
		
		for(int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int num1 = Integer.parseInt(st.nextToken());
			int num2 = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			list[num1].add(new Edge(num2, cost)); // 양방향이므로 둘다 넣어주기
			list[num2].add(new Edge(num1, cost)); // 양방향이므로 둘다 넣어주기
		}
		st = new StringTokenizer(br.readLine());
		v1 = Integer.parseInt(st.nextToken()); // 반드시 들려야하는 점 
		v2 = Integer.parseInt(st.nextToken()); // 반드시 들려야하는 점 
		
		int sum1 = 0; // 시작점(1) > v1 > v2 > N 으로 가는 루트의 다익스트라 값을 저장할 변수
		int sum2 = 0; // 시작점(1) > v2 > v1 > N 으로 가는 루트의 다익스트라 값을 저장할 변수
		
		sum1 += dijkstra(1, v1);  // 시작점(1) > v1     다익스트라
		sum1 += dijkstra(v1, v2); // v1 > v2     다익스트라
		sum1 += dijkstra(v2, N); // v2 > 끝점(N)     다익스트라
		 
		sum2 += dijkstra(1, v2); // 시작점(1) > v2     다익스트라
		sum2 += dijkstra(v2, v1); // v2 > v1     다익스트라
		sum2 += dijkstra(v1, N); // v1 > 끝점(N)     다익스트라
		
		sum1 = Math.min(sum1, sum2); // 둘 중 최소값으로 갱신해주고
		if(sum1 >= max) { // 만약 이 값이 max 보다 크다? 그럼 1에서 N까지 도달을 못했다는거
			System.out.println(-1); // 그러므로 -1 출력
			return;
		}
		
		System.out.println(sum1); // 위의 사항에 해당되지 않으므로 최소값 출력
	}

	private static int dijkstra(int start, int end) {
		PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.c - o2.c;
			}
		}); // 다익스트라를 위한 우선순위 큐( cost가 낮은순으로 나오게)
		
		visited = new boolean[N+1]; // 방문했는지 체크하기위한 배열
		cost = new int[N+1]; // 다익스트라 비용을 관리하기 위한 배열
		Arrays.fill(cost, max); // 비용을 모두 max로 채움
		cost[start] = 0; // 시작점의 비용은 0으로 초기화
		pq.offer(new Edge(start,0)); // 시작점 넣고 시작
		
		while(!pq.isEmpty()) {
			Edge edge = pq.poll(); // pq에 있는 Edge 하나 빼
			if(visited[edge.e]) continue; // 방문했었으면 그냥 통과
			visited[edge.e] = true; // 방문안했으면 방문처리
			for(Edge next:list[edge.e]) { // 해당 정점에 해당하는 간선들 다 뒤져봐
				if(visited[next.e]) continue; // 다음 정점이 방문한 정점이면 그냥 통과
				if(edge.c + next.c < cost[next.e]) { // 비용 비교해보고 cost 배열에 저장된 값보작다면 로직 시작
					cost[next.e]= edge.c + next.c; // 비용 갱신해주고
					pq.offer(new Edge(next.e, cost[next.e])); // 다음 점 pq에 넣어주고
				}else { // 비용 비교 해보고 cost배열 값이 더 작으면 갱신할 필요 없으니까 통과
					continue;
				}
			}
		}
		
		return cost[end]; // 마지막으로 cost배열의 끝점을 반환해주면 돼
	}
}
