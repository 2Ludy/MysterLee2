import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * [여행 가자]
 * N개의 도시 사이의 길 존재 여부
 * 계획을 세운 순서대로 여행
 * 여행이 가능한지 여부 판별
 */

public class BOJ1976 {
	
	static int N;
	static int M;
	static boolean ans; // true이면 YES, false이면 NO 출력
	
	static int[] parent; // 부모 노드 저장 배열

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		// 각 도시는 자신이 부모 노드로 초기화
		parent = new int[N+1];
		for (int i = 1; i <= N; i++) {
			parent[i] = i; 
		}
		
		// 도시 연결 정보 처리
		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				int tmp = Integer.parseInt(st.nextToken());
				// i와 j가 연결되어있다면, union 메서드 실행
				if (tmp == 1) union(i, j);
			}
		}

		// 여행이 가능한지 여부
		ans = true;
		
		 // 여행 계획
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 첫 번째 도시의 부모 찾기
		int start = find(Integer.parseInt(st.nextToken()));
		// 다음 도시부터 첫 번째 도시와 부모 노드가 같은지 확인
		for (int i = 1; i < M; i++) {
			int next = Integer.parseInt(st.nextToken());
			
			// 맨 처음 출발 도시와 연결되지 않은 도시가 있다면 
			if (start != find(next)) {
				ans = false; // 여행 불가능
				break;  // 바로 종료
			}
		}
		
		System.out.println(ans ? "YES" : "NO");
	}

	// 부모 노드를 찾는 연산
	static int find(int x) {
		// 만약 나 자신이 부모 노드라면 return
		if (x == parent[x]) return x;
		// 내 조상 노드 = 내 부모 노드의 부모 노드
		return parent[x] = find(parent[x]);
	}

	// 합집합을 만드는 연산
	static void union(int x, int y) {
		// x의 부모노드 찾기
		x = find(x);
		// y의 부모노드 찾기
		y = find(y);
		
		// 같은 부모노드를 가졌다면 메서드 종료
		if (x == y) return;
		
		// 그렇지 않다면
		// 노드 번호가 작은 쪽이 부모가 되도록
		if (x < y) parent[y] = x; // x가 y보다 작으면 x를 부모로 설정
		else parent[x] = y; // y가 x보다 작으면 y를 부모로 설정
	}

}
