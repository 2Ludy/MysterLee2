package 이동욱.SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA_22683 {
	
	static int TC, N, T, sr, sc;
	static char[][] map;
	static boolean[][][] visited;
	static int[] dr = {-1, 0, 1, 0}; // 위 오른쪽 아래 왼쪽 (clock-wise)
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		TC = Integer.parseInt(br.readLine());
		a : for(int t=1; t<=TC; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			T = Integer.parseInt(st.nextToken());
			map = new char[N][N]; 
			visited = new boolean[N][N][T+1]; // 방문배열(tree를 얼마나 깰수있는지 3차원으로 0부터 T까지 포함해야 하므로 T+1로 만듬)
			
			for(int i=0; i<N; i++) {
				char[] cs = br.readLine().toCharArray(); // 맵
				for(int j=0; j<N; j++) {
					map[i][j] = cs[j]; // 맵 입력
					if(map[i][j] == 'X') {  //  시작 지점 담기
						sr = i;
						sc = j;
					}
				}
			} // 완
			
			//PriorityQueue로 할꺼임  그 이유는 que에 담는 int 배열 3번째 인덱스 자리에
			//커맨드를 얼마나 입력했는지 넣을껀데, 그 커맨드가 작은 순서대로 que에서 빠져나오게 만들어서
			//가장 최소로 탈출할 수 있게 만들기 위해서임
			PriorityQueue<int[]> que = new PriorityQueue<>(new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return o1[2] - o2[2];  // 오름차순으로 정렬
				}
			});
			
			que.offer(new int[] {sr, sc, 0, T, 0}); // 인덱스 순서대로 r, c, count, T, 방향(0:위, 1: 오른쪽, 2:아래, 3:왼쪽)
			visited[sr][sc][T] = true; // 시작 지점 배열 true
			while(!que.isEmpty() ) {
				int[] nums = que.poll();
				int r = nums[0]; // r
				int c = nums[1]; // c
				int count = nums[2]; // 커맨드 얼마나 입력했는지
				int tree = nums[3]; // tree를 몇개나 부실 수 있는지 저장하는 변수
				int direction = nums[4]; // 방향을 저장하는 변수
				
				if(map[r][c] == 'Y') { // 만약 목적지 즉 Y에 도착한다면 출력 후 다음 테케 진행 
					System.out.println("#" + t + " " + count);
					continue a;
				}
				
				for(int d=0; d<4; d++) { // 4면 돌려
					int nr = r+dr[d];
					int nc = c+dc[d];
					if(!check(nr,nc)) continue; // 배열 인덱스 확인
					if(visited[nr][nc][tree]) continue; // 방문 체크
					int tmp = Math.abs(direction-d); // 절대값으로 방향을 얼마나 돌려야 하는지 계산
					if(tmp == 3) tmp = 1; // 근데 3번 돌면 반대 방향으로 1번 도는거랑 동일해서 최소 값 출력해야하니 1로 변경
					int treetmp=tree;  // tree 몇개 부술 수 있는지 임시 저장
					if(map[nr][nc] == 'T') { // tree를 만났어
						if(tree == 0) { // 못 부수면 넘어가야지
							continue;
						}else {
							treetmp--; // 부술수 있으면 tree 깻으니 -- 해줌
						}
					}
					visited[nr][nc][treetmp] = true; // 방문 배열 true로 만들어줌
					que.offer(new int[] {nr, nc, count+tmp+1, treetmp, d});  // que에 다시 넣어줌
					
				}
			}
			System.out.println("#" + t + " " + -1); // 굶어 죽어서 여기까지 도착하면 실패했으므로 실패 출력
		}
	}

	private static boolean check(int r, int c) { // 배열 인덱스 체크 메서드
		return r>=0 && r<N && c>=0 && c<N;
	}
}
