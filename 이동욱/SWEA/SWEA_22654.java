package 이동욱.SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SWEA_22654 {
	
	static int T, N, Q, sr, sc;
	static char[][] map;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	static List<Character>[] list;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());
			map = new char[N][N];
			for(int i=0; i<N; i++) {
				char[] cs = br.readLine().toCharArray(); 
				for(int j=0; j<N; j++) {
					map[i][j] = cs[j];
					if(map[i][j] == 'X') {  // 시작 지점 r, c 받기
						sr = i;
						sc = j;
					}
				}
			} // 맵 생성
			
			Q = Integer.parseInt(br.readLine());
			list = new ArrayList[Q]; // rc카 커맨드가 들어가 있는 list배열 초기화
			for(int i=0; i<Q; i++) {
				list[i] = new ArrayList<>(); // 각 list 초기화
				StringTokenizer st = new StringTokenizer(br.readLine());
				int M = Integer.parseInt(st.nextToken());
				char[] cs = st.nextToken().toCharArray();
				for(int j=0; j<M; j++) {
					list[i].add(cs[j]);
				}
			} // 커맨드 입력 받기 완료
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(t).append(" ");
			for(int i=0; i<Q; i++) {
				int r = sr;
				int c = sc;
				int direction = 0; // 위 0, 오른쪽 1, 아래 2, 왼쪽 3  (clock-wise)
				for(int j=0; j<list[i].size(); j++) { // 각 list[i]의 size 만큼만 돌면 됨
					char ch = list[i].get(j);
					if(ch == 'R') {  // 우로 회전
						direction = direction+1;
						if(direction == 4) { // 3+1 = 4 일때만 처리해주면 됨
							direction = 0;
						}
					}else if(ch == 'L') { // 좌로 회전
						direction = direction-1;
						if(direction == -1) { // 0-1 = -1 일때만 처리해주면 됨
							direction = 3;
						}	
					}else if(ch == 'A') { // 앞으로 이동가능
						int nr = r+dr[direction];
						int nc = c+dc[direction];
						if(!check(nr,nc)) continue;  //  배열 인덱스 넘어가면 continue
						if(map[nr][nc] == 'T') continue;  //  장애물인 T가 있으면 continue
						r = nr;  // 다 통과하면 A일때 앞으로 이동 가능한것으로 r 갱신
						c = nc; // c 갱신
					}
				} // for문 종료
				if(map[r][c] == 'Y') { // 위에 다 빠져나왔을때의 r,c 좌표가 Y를 가리키면 성공 
					sb.append(1).append(" ");
				}else { // 실패
					sb.append(0).append(" ");
				}
			}
			System.out.println(sb); // 출력
		}
	}

	private static boolean check(int r, int c) {  //  배열 인덱스 체크 메서드
		return r>=0 && r<N && c>=0 && c<N;
	}
}
