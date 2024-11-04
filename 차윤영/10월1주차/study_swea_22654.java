package study_1005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * G: 땅 (RC카가 이동 가능)
 * T: 나무 (RC카 이동 불가능)
 * X: 시작점 (처음 바라보는 방향은 위)
 * Y: 목적지
 * 
 * A: 전진 (나무가 있거나 필드를 벗어나면 아무 일도 안일어남)
 * L: 좌회전
 * R: 우회전
 * 
 * RC카는 위를 바라보는 방향에서 시작
 * 조종정보가 주어졌을 때 목적지에 도달할 수 있는지 판단
 */

public class study_swea_22654 {
	
	static int T;
	static int N; // 필드의 크기, 2 이상 5 이하
	static char[][] map; // 필드의 정보
	static int M; // 조종 횟수
	static Queue<Character> comm; // 조종 정보 (1개 이상 50개 이하)
	static boolean ans; // 목적지에 도달 가능하면 1, 아니면 0
	
	static int[] dr = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			
			// 입력
			map = new char[N][N];
			// 출발점
			int sr = -1;
			int sc = -1;

			for (int i = 0; i < N; i++) {
				String str = br.readLine();
				for (int j = 0; j < N; j++) {
					map[i][j] = str.charAt(j);
					// 출발점을 찾는 조건문
					if (map[i][j] == 'X') {
						sr = i;
						sc = j;
					}
				}
			}
			
//			System.out.println("sr: " + sr + ", sc: " + sc);
//			System.out.println("er: " + er + ", ec: " + ec);
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc);
			
			// 몇 번의 커맨드를 진행할건지
			M = Integer.parseInt(br.readLine());
			
			for (int o = 0; o < M; o++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				// 해당 경우의 커맨드 개수
				int C = Integer.parseInt(st.nextToken());
				String str = st.nextToken();
				// 커맨드를 Queue에 저장
				comm = new LinkedList<>();
				for (int i = 0; i < C; i++) {
					comm.offer(str.charAt(i));
				}
				
				// 도달한 위치가 도착점인지를 판단하는 ans 초기화
				ans = false;
				// (r, c, 바라보고 있는 방향)을 저장한 정수형 배열
				int[] rc = new int[] {sr, sc, 0}; 
				
//				System.out.println((o+1) + "번째 커맨드");
//				System.out.println(comm);
				
				while (!comm.isEmpty()) {
					// Queue에서 커맨드 추출
					char order = comm.poll();
					// rc 배열에 이동한 위치와 바라보고 있는 방향 저장
					rc = move(rc, order);
					
//					for (int i = 0; i < N; i++) {
//						for (int j = 0; j < N; j++) {
//							if (i == rc[0] && j == rc[1]) {
//								System.out.print('O' + " ");
//							} else {
//								System.out.print(map[i][j] + " ");
//							}
//						}
//						System.out.println();
//					}
//					System.out.println(order + ": " + Arrays.toString(rc));
//					System.out.println("=========================");

				}
				
				// 최종 위치 (r, c)
				int r = rc[0];
				int c = rc[1];
				// 최종 위치가 Y라면 ans에 true 저장
				if (map[r][c] == 'Y') ans = true;
				
				// 만약 ans가 true라면 1, false라면 0 반환
				sb.append(" ").append(ans ? 1 : 0);
			}
			
			
			System.out.println(sb);
			
		}

	}

	// 이동한 위치와 바라보는 방향을 찾는 메서드
	static int[] move(int[] rc, char o) {
		int r = rc[0];
		int c = rc[1];
		int d = rc[2]; // 바라보는 방향
		// 커맨드가 A(전진)일 때
		if (o == 'A') {
			// 바라보고 있는 방향으로 이동이 가능하다면
			if (canGo(r, c, d)) {
				// 델타 배열을 이동한 위치 이동
				r += dr[d];
				c += dc[d];
			}
		} 
		// 커맨드가 R(우회전)일 때
		else if (o == 'R') {
			// 바라보는 방향에 +1 
			// 단, 델타 배열의 길이가 4이므로 모듈러 이용
			d = (d + 1) % 4;
		} 
		// 커맨드가 L(좌회전)일 때
		else if (o == 'L') {
			// 바라보는 방향에 -1
			// 단, 델타 배열의 4이며, -1을 했을 때 음수가 될 수 있으므로
			// 4를 더한 후 모듈러 이용
			d = (d - 1 + 4) % 4;
		}
		
		return new int[] {r, c, d};
	}

	// 이동 가능 여부를 반환하는 메서드
	static boolean canGo(int r, int c, int d) {
		// (r, c)에서 해당 방향으로 이동했을 때
		int nr = r + dr[d];
		int nc = c + dc[d];
		// 이동한 위치가 배열을 벗어나지 않고, 해당 위치에 나무가 있지 않으면 true 반환
		return check(nr, nc) && map[nr][nc] != 'T';
	}

	// 배열의 범위를 벗어나는지 확인하는 메서드
	static boolean check(int r, int c) {
		// r, c의 범위: 0 ~ N-1
		return r >= 0 && r < N && c >= 0 && c < N;
	}

}
