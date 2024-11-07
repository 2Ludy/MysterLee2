// https://velog.io/@2ludy/boj1922
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1922 {
	static class Edge{ // 프림 알고리즘을 돌리기 위해 끝점과 비용을 담을 Edge 클래스 생성
		int end;
		int cost;
		public Edge(int end, int cost) {
			this.end = end;
			this.cost = cost;
		}
	}
	
	static int N, M;
	static List<Edge>[] list; // 인접 리스트로 간선들 관리
	static boolean[] visited; // 방문 체크 배열
	static int cost; // 모든 컴퓨터를 연결하는데 필요한 최소 비용을 갱신 할 변수
	static PriorityQueue<Edge> pq; // 우선순위 큐로 프림 알고리즘 돌리장
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		list = new ArrayList[N+1]; // 인접 리스트는 N+1 까지 0번 인덱스는 버려
		
		for(int i=0; i<N+1; i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0; i<M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			list[a].add(new Edge(b,c)); // 양 방향이니까 양쪽에 담아주자
			list[b].add(new Edge(a,c)); // 양 방향이니까 양쪽에 담아주자
		}
		visited = new boolean[N+1]; // 방문 배열도 N+1로 관리 0번 인덱스는 버리자
		cost = 0; // 0으로 초기화해서 비용 갱신해주자
		pq = new PriorityQueue<>(new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.cost - o2.cost;
			}
			
		}); // 우선순위 큐는 cost가 작은순서대로 나오게 만들어주자

		for(int i=0; i<list[1].size(); i++) {
			pq.offer(list[1].get(i));
		} // prim은 아무 정점부터 시작해도 상관 없으므로, N이 1부터 시작한다는 것은 1이 무조건 존재하니까 1에 해당하는 간선들 싹 다 넣어주자
		
		visited[1] = true; // 1에 해당하는 간선들 다 넣었으니 방문처리 필수
		
		while(!pq.isEmpty()) {
			Edge edge = pq.poll(); // 간선 빼
			if(visited[edge.end]) continue; // 간선 뺐는데 끝 점이 방문한 점이면 통과 시켜
			cost += edge.cost; // 아니라면 갱신해야되니까 cost 갱신
			N--; // 1개씩 연결되니까 N을 줄여줘서 밑에서 빠져나올래
			if(N==0 || N==1) { // N이 0이거나 1이면 모든 컴퓨터가 연결된거니까 바로 빠져나와
				System.out.println(cost); // 비용 출력
				return;
			}
			visited[edge.end] = true; // 해당 간선의 끝점 방문 완료 했으니 이제 갈필요 없으니까 방문했다고 갱신
			for(int i=0; i<list[edge.end].size(); i++) {
				pq.offer(list[edge.end].get(i)); // 해당 간선의 끝점부터 시작하는 간선들 싹 다 넣어주자
			}
		}
	}
}
