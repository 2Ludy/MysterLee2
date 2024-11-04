import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/* 
 * [미세먼지 안녕!]
 * 
 * R x C 격자판
 * 
 * 공기청정기는 항상 1번 열에 위치, 두 행을 차지
 * 공기청정기가 설치되지 않은 칸에는 미세먼지 존재
 * 1초동안 일어나는 일
 * -> 미세먼지가 존재하는 모든 칸에서 인접한 네 방향으로 미세먼지 확산
 *    인접한 방향에 공기청정기가 있거나, 칸이 없으면 확산 X
 *    확산되는 양은 해당 칸의 미세먼지 양 / 5 (몫)
 *    해당 칸에 남은 미세먼지의 양 = 원래의 양 - 확산양 * 확산 방향의 개수
 * -> 공기청정기 작동
 *    위쪽 공기청정기는 반시계 방향으로 순환, 아래쪽 공기청정기는 시계방향으로 순환
 *    바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동
 *    공기청정기의 바람은 미세먼지가 없는 바람, 공기청정기로 들어간 미세먼지는 모두 정화
 * 
 */

public class BOJ17144_re {
	
	// R, C: 6 이상 50 이하, T: 1 이상 1000 이하
	static int R, C, T; 
	// 미세먼지 맵 (-1: 공기청정기, 나머지: 미세먼지의 양)
	static int[][] map; 
	static int[][] copy;
	// 확산에 의해 나눠 가질 값, 확산 후 남아있는 값
	static int[][][] div; 
	
	static int ans; // T초 후 남아있는 미세먼지의 양
	// 위쪽 공기청정기의 반시계방향 델타
	static int[] udr = {0, -1, 0, 1}; 
	static int[] udc = {1, 0, -1, 0};
	// 아래쪽 공기청정기의 시계방향 델타
	static int[] ddr = {0, 1, 0, -1}; 
	static int[] ddc = {1, 0, -1, 0};
	// 기본 델타 (상, 우, 하, 좌)
	static int[] dr = {-1, 0, 1, 0}; 
	static int[] dc = {0, 1, 0, -1};
	
	static int a1, a2; // 위쪽, 아래쪽 공기청정기 위치

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		List<Integer> tmp = new ArrayList<>(); // 공기청정기의 위치 임시 저장
		map = new int[R][C];

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == -1) {
					tmp.add(i);
				}
			}
		}
		
		// 위쪽 공기청정기의 행
		a1 = tmp.get(0);
		// 아래쪽 공기청정기의 행
		a2 = tmp.get(1);
		
		// T초 동안 미세먼지의 확산과 공기청정기 가동에 의한 회전 수행
		for (int t = 0; t < T; t++) {
			spread();
			wind();
		}
		
		ans = 0;
		// 남아있는 미세먼지의 양 계산
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] != -1) ans += map[i][j];
			}
		}

		System.out.println(ans);
	}

	// 미세먼지의 확산을 계산하는 메서드
	static void spread() {
	    div = new int[R][C][2];
	    for (int i = 0; i < R; i++) {
	        for (int j = 0; j < C; j++) {
	        	// 해당 위치가 공기청정기가 아니며, 미세먼지가 있을 때
	            if (map[i][j] > 0) {
	            	// 인접한 방향으로 확산될 양
	                int spreadAmount = map[i][j] / 5;
	                // 확산될 칸의 수 초기화
	                int cnt = 0;

	                // 확산 가능한 방향 수 세기
	                for (int d = 0; d < 4; d++) {
	                    int nr = i + dr[d];
	                    int nc = j + dc[d];
	                    // 인접한 칸이 배열의 범위 내이며, 공기청정기가 아니라면
	                    if (check(nr, nc) && map[nr][nc] != -1) {
	                    	// 확산될 칸의 수 증가
	                        cnt++;
	                        // 인접한 칸의, 확산에 의해 전달받을 미세먼지의 양 추가
	                        div[nr][nc][0] += spreadAmount;
	                    }
	                }

	                // 남아있는 미세먼지 계산
	                div[i][j][1] = map[i][j] - (spreadAmount * cnt);
	            }
	        }
	    }

	    // map 업데이트
	    copy = new int[R][C];
	    for (int i = 0; i < R; i++) {
	        for (int j = 0; j < C; j++) {
	        	// 공기청정기는 공기청정기로 유지
	            if (map[i][j] == -1) {
	                copy[i][j] = -1;
	            } 
	            // 공기청정기가 아닌 칸이면
	            else {
	            	// (남아있는 양 + 확산된 양) 으로 갱신
	                copy[i][j] = div[i][j][1] + div[i][j][0]; 
	            }
	        }
	    }

	    for (int i = 0; i < R; i++) {
	        System.arraycopy(copy[i], 0, map[i], 0, C);
	    }
	}

	// 공기청정기에 의한 미세먼지의 회전을 수행하는 메서드
	static void wind() {
	    // 위쪽 공기청정기 위치 (r, c) = (a1, 0)
	    int r = a1;
	    int c = 0;
	    // 첫 번째 방향부터 시작
	    int d = 0;
	    int tmp1 = map[a1][1]; // 첫 번째 바람이 불어오는 방향의 미세먼지
	    
	    while (true) {
	    	// 반시계방향 델타를 고려한 이동 위치
	        int nr = r + udr[d];
	        int nc = c + udc[d];
	        
	        // 이동 후의 위치가 위쪽 공기청정기라면
	        if (nr == a1 && nc == 0) {
	        	// 위쪽 공기청정기의 위치는 고정
	            map[nr][nc] = -1;
	            // 공기청정기에서 나온 바람은 미세먼지 농도 0
	            map[a1][1] = 0;
	            // 회전 종료
	            break;
	        }
	        
	        // 만약 이동한 곳이 배열의 범위 내라면 회전 시행
	        if (check(nr, nc)) {
	            int tmp2 = map[nr][nc];
	            map[nr][nc] = tmp1;
	            tmp1 = tmp2;
	            r = nr;
	            c = nc;
	        } 
	        // 해당 방향으로 더 이동했을 때, 배열을 벗어난다면
	        else {
	            d = (d + 1) % 4; // 방향 변경
	        }
	    }

	    // 아래쪽 공기청정기 위치 (r, c) = (a2, 0)
	    r = a2;
	    c = 0;
	    // 첫 번째 방향부터 시작
	    d = 0;
	    tmp1 = map[a2][1]; // 첫 번째 바람이 불어오는 방향의 미세먼지
	    
	    while (true) {
	    	// 시계방향 델타를 고려한 이동 위치
	        int nr = r + ddr[d];
	        int nc = c + ddc[d];
	        
	        // 이동 후의 위치가 아래쪽 공기청정기라면
	        if (nr == a2 && nc == 0) {
	        	// 아래쪽 공기청정기의 위치는 고정
	            map[nr][nc] = -1;
	            // 공기청정기에서 나온 바람은 미세먼지 농도 0
	            map[a2][1] = 0;
	            // 회전 종료
	            break;
	        }
	        
	        // 만약 이동한 곳이 배열의 범위 내라면 회전 시행
	        if (check(nr, nc)) {
	            int tmp2 = map[nr][nc];
	            map[nr][nc] = tmp1;
	            tmp1 = tmp2;
	            r = nr;
	            c = nc;
	        } 
	        // 해당 방향으로 더 이동했을 때, 배열을 벗어난다면
	        else {
	            d = (d + 1) % 4; // 방향 변경
	        }
	    }
	}

	// 배열의 범위를 벗어나는지 확인하는 메서드
	static boolean check(int r, int c) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}

}
