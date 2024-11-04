// https://velog.io/@2ludy/boj14503
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14503 {
	
	static int N, M;
	static int sr, sc, d; // 시작 위치를 담고 해당 변수로 이동, 시작 방향
	static int count; // 몇칸을 청소했는지 관리할 변수
	static int[][] map; // 방 정보가 담길 배열
	static int[] dr = {-1, 0, 1, 0}; // 북 동 남 서 (d가 0 = 북, 1 = 동, 2 = 남, 3 = 서 이므로 인덱스로 관리)
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		sr = Integer.parseInt(st.nextToken());
		sc = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		count = 0; // count 0으로 초기화
		while(true) { // 시작
			if(map[sr][sc] == 0) { // 만약 청소가 가능하다면
				map[sr][sc] = 2; // 청소시키기
				count++; // 청소했으니 count + 1
			}else { // 만약 이미 청소한 곳이라면..(map[sr][sc] == 2)
				int clean = 0;  //  상하좌우 근접한 4칸에 청소할 수 있는 곳이 몇개 있는지 판별하는 변수
				for(int k=0; k<4; k++) {
					int nr = sr + dr[k]; // 4방향 탐색
					int nc = sc + dc[k];
					if(map[nr][nc] == 0) clean++; // 만약 청소가능한곳이 있다면 clean + 1
				}
				if(clean == 0) { // 만약 주변 4칸에 청소할곳이 하나도 없다면...
					int nr = sr+dr[(d+2)%4]; // 뒤로 후진을 위한 계산식 
					int nc = sc+dc[(d+2)%4];
					if(map[nr][nc] != 1) { // 뒤로 후진할곳이 벽(1)로 막혀있지 않다면..
						sr = nr; // 이동
						sc = nc; 
					}else { // 뒤로 후진할곳이 벽으로 막혀 있다면... 끝
						System.out.println(count);  // 출력
						return;
					}
				}else { // 만약 주변 4칸에 청소할 곳이 하나 이상이라면...
 					int tmpr = sr; // 임시로 sr 좌표를 담아둠
					int tmpc = sc; // 임시로 sc 좌표를 담아둠
					while(tmpr==sr && tmpc==sc) { // sr,sc가 바뀌지 않는다면 while문이 계속 돌아감
						d = d-1; // 반시계로 90도 방향 전환
						if(d == -1) d = 3; // 근데 -1로 간다면... 3으로 가야해 
						int nr = sr+dr[d]; // 반시계 90도 방향 전환 했을때의 좌표
						int nc = sc+dc[d];
						if(map[nr][nc] == 0) { // 반시계 90도 방향 전환 했을때 앞쪽 칸이 청소해야된다면..
							sr = nr; // 이동 
							sc = nc; // 이동
							// 여기서 sr와 sc가 이동되었으므로 바로 위 while문이 깨짐
						}
					}
				}
			}
		}
	}
}
