import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * [특정한 최단 경로]
 * 방향성이 없는 그래프
 * 1번 정점에서 N번 정점으로 이동하는 최단 경로 -> 이동이 불가능하면 -1
 * 임의의 주어진 두 정점은 반드시 통과
 * 한 번 이동했던 정점, 간선은 다시 이동 가능
 */

public class BOJ1504 {
	
	static class Node implements Comparable<Node> {
		int V, W; // 연결된 노드, 거리(가중치)

		public Node(int v, int w) {
			super();
			V = v;
			W = w;
		}

		// priorityQueue 사용을 위한 compareTo
		@Override
		public int compareTo(Node o) {
			return W - o.W;
		}
		
	}
	
	static int N, E; // 노드의 개수, 간선의 개수
	static List<Node>[] link;
	static int v1, v2; // 반드시 거쳐야하는 정점 2개
	static int INF = 200000 * 1000;
	static int[] dist;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		link = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			link[i] = new ArrayList<>();
		}
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			// 0번 노드부터 시작할 수 있도록, 각 노드 번호에 1을 빼준다
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int w = Integer.parseInt(st.nextToken());
			// 양방향 간선
			link[a].add(new Node(b, w));
			link[b].add(new Node(a, w));
		}
		st = new StringTokenizer(br.readLine());
		v1 = Integer.parseInt(st.nextToken()) - 1;
		v2 = Integer.parseInt(st.nextToken()) - 1;
		
		int case1 = 0; // 0 -> v1 -> v2 -> N-1 경로로 갈 경우
		int case2 = 0; // 0 -> v2 -> v1 -> N-1 경로로 갈 경우
		
		case1 = func(0, v1) + func(v1, v2) + func(v2, N-1);
		case2 = func(0, v2) + func(v2, v1) + func(v1, N-1);
		
		// 각각의 상황에서 가능하지 않은 경우 때문에 INF가 생길 경우 -1 반환
		System.out.println((case1 >= INF && case2 >= INF) ? -1 : Math.min(case1, case2));		
	}

	static int func(int start, int end) {
		// 거리 최댓값으로 채우기
		dist = new int[N];
		Arrays.fill(dist, INF);
		// 각 노드가 방문되었는지 여부를 저장
		visited = new boolean[N];
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(start, 0)); // pq에 시작점 삽입 (시작점의 거리는 0)
		dist[start] = 0; // 시작점은 거리 0으로 설정
		
		while (!pq.isEmpty()) {
			// PriorityQueue에 의해 가장 작은 거리를 가진 노드를 큐에서 꺼냄
			Node curNode = pq.poll();
			// 현재 노드를 cur 변수에 저장
			int cur = curNode.V; // 현재 노드의 이동 가능한 점
			
			// 만약 방문한 적이 없는 노드라면
			if (!visited[cur]) {
				// 방문 체크
				visited[cur] = true;
			
				// 현재 노드와 연결된 모든 노드에 대해
				for (Node node : link[cur]) {
					// 만약 방문한 적이 없는 노드이고, 더 작은 거리를 찾는다면
					if (!visited[node.V] && (dist[node.V] > dist[cur] + node.W)) {
						// 경로 갱신
						dist[node.V] = dist[cur] + node.W;
						// 갱신된 경로를 넣어 우선순위 큐에 삽입
						pq.add(new Node(node.V, dist[node.V]));
					}
				}
			}
		}
		
		// 최종 목적지의 최단거리 반환
		return dist[end];
	}

}
