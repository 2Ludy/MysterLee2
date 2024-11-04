package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_17135 { // 캐슬 디펜스 (시뮬)
	// 궁수 3명을 배치 -> 최대로 제거할 수 있는 적의 수 찾기!
	// 공격할 적 -> 거리가 D이하인 적 중 가장 가깝고, 여럿이면 가장 왼쪽
	static int N,M,D,max;
	static int[][] map, copyMap;
	static int[] player;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken()); // 공격 거리 제한
		map = new int[N][M];
		player = new int[3];
		max = 0;
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 궁수 3명 배치 조합 (mC3)
		combi(0,0);
		
		System.out.println(max);
	}

	static void combi(int start, int cnt) {
		if(cnt == 3) {
			// 배열 복사본 생성
			copyMap = new int[N][M];
			for (int i = 0; i < N; i++) {
	           System.arraycopy(map[i], 0, copyMap[i], 0, M);
	        }
			playGame(); // 시뮬 시작
			return;
		}
		
		for (int i = start; i < M; i++) {
			player[cnt] = i;
			combi(i+1, cnt+1);
		}
		
	}

	static void playGame() {
		int killCnt = 0;
		
		while(true) {
			int[][] killed = new int[N][M];
			int cnt = attack(killed); // 공격으로 제거된 적 수
			killCnt += cnt;

			moveEnemies(); // 남은 적 아래로 이동
			if(!checkEnemies()) break; // 적 남아있는지 확인
		}
		
		max = Math.max(max, killCnt);
	}

	static int attack(int[][] killed) {
		int cnt = 0; // 제거된 적 수
		
		for (int i = 0; i < 3; i++) {
			int playerR = N;
			int playerC = player[i];
			
			int min = Integer.MAX_VALUE;
			int targetR = -1;
			int targetC = -1;
			
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < M; c++) {
					if(copyMap[r][c] == 1) {
						int dis = Math.abs(playerR-r) + Math.abs(playerC-c);
					
						if(dis <= D) {
							if(dis < min) {
								min = dis;
								targetR = r;
								targetC = c;
							} else if(dis == min) {
								if(c < targetC) {
									targetR = r;
									targetC = c;
								}
							}
						}
					
					}
				}
			}
			if(targetR != -1 && targetC != -1) {
				killed[targetR][targetC]++;
			}
		}
		for(int i = 0; i < N; i++) {
		       for(int j = 0; j < M; j++) {
		           if(killed[i][j] > 0) { // 한 번이라도 공격당했으면
		               copyMap[i][j] = 0; // 적 제거
		               cnt++; // 제거된 적의 수 증가
		           }
		       }
	    }
		return cnt;
	}

	static void moveEnemies() {
		for(int i = N-1; i > 0; i--) {
	       System.arraycopy(copyMap[i-1], 0, copyMap[i], 0, M);
	    }
		
		Arrays.fill(copyMap[0], 0);
	}

	static boolean checkEnemies() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(copyMap[i][j] == 1) return true;
			}
		}
		return false;
	}
	
}
