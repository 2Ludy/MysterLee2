import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*
 * [캐슬 디펜스]
 * 
 * N x M 칸의 격자판
 * 한 칸에 포함된 적의 수는 최대 하나
 * N번 행 바로 아래의 모든 칸에는 성이 위치
 * 성에 3명의 궁수 배치, 하나의 칸에 최대 한 명의 궁수
 * 각각의 턴마다 궁수 한 명당 적 하나 공격, 모든 궁수는 동시에 공격
 * 궁수가 공격하는 적: 거리가 D 이하인 적 중에서 가장 가까운 적 (맨해튼 거리)
 * 턴이 끝나면 적은 아래로 한 칸 이동, 성이 있는 칸으로 이동하면 게임에서 제외
 * 모든 적이 격자판에서 제외되면 게임 끝
 * -> 궁수의 공격으로 제거할 수 있는 적의 최대 수
 */

public class BOJ17135 {
	
	// N x M (3 이상 15 이하), D: 궁수의 공격 거리 제한 (1 이상 10 이하)
    static int N, M, D;  
    // 격자판, 0: 빈 칸, 1: 적이 있는 칸
    static int[][] map; 
    static int[][] copy;
    static int max; // 궁수의 공격으로 제거할 수 있는 적의 최대 수
    static int[] nums; // 선택한 궁수의 위치를 저장할 배열

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        map = new int[N][M]; 
        copy = new int[N][M];
        // 격자판의 정보 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        // 궁수의 위치에 따라 게임을 진행할 떄, 격자판의 정보가 바뀌기 때문에
        // copy에 map의 정보 저장해두기
        for (int i = 0; i < N; i++) {
            System.arraycopy(map[i], 0, copy[i], 0, M);
        }

        nums = new int[3];
        max = 0;
        
        combi(0, 0);
        
        System.out.println(max);
    }
    
    // 조합을 통해 궁수의 위치 선택
    static void combi(int cnt, int start) {
        if (cnt == 3) {
            startGame();
            return;
        }
        for (int i = start; i < M; i++) {
            nums[cnt] = i;
            combi(cnt + 1, i + 1);
        }
    }

    // 게임 시작
    static void startGame() {
    	// 원본의 배열을 사용하기 위해 map에 copy를 깊은 복사
        for (int i = 0; i < N; i++) {
            System.arraycopy(copy[i], 0, map[i], 0, M);
        }
        int cnt = 0; // 궁수의 공격으로 제거한 적의 수
        
        while (!allClear()) { // 맵에서 적이 모두 사라질 때 까지
        	// 공격한 적의 위치를 저장할 배열
            boolean[][] killed = new boolean[N][M];
            // 각 궁수의 가장 가까운 적의 위치 저장
            List<int[]> targets = new ArrayList<>();

            for (int k = 0; k < 3; k++) {
                int r = N; // 궁수는 N번째 행에 위치
                int c = nums[k]; // 조합을 통해 고른 궁수의 열
                // 가장 가까운 적의 위치와 거리를 초기화
                int targetR = -1, targetC = -1, minDist = Integer.MAX_VALUE;

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        if (map[i][j] == 1) {
                            int distance = Math.abs(r - i) + Math.abs(c - j);
                            // D보다 가까운 적일 때
                            if (distance <= D) {
                            	// 그동안의 적보다 더 가까운 적이거나
                                if (distance < minDist 
                                		// 거리가 같은 적이지만 더 왼쪽에 있는 적일 때
                                		// (조합을 통해 왼쪽의 궁수부터 고려하기 때문에)
                                		|| (distance == minDist && j < targetC)) {
                                	// 해당 적의 위치와 거리 저장
                                    targetR = i;
                                    targetC = j;
                                    minDist = distance;
                                }
                            }
                        }
                    }
                }

                // 만약 해당 궁수에 대해 가장 가까운 적이 있다면 List에 저장
                if (targetR != -1) {
                    targets.add(new int[] {targetR, targetC});
                }
            }

            // 공격할 적 중복 제거
            for (int[] target : targets) {
                int targetR = target[0];
                int targetC = target[1];
                // 이미 제거된 적이 아니라면 제거
                if (!killed[targetR][targetC]) {
                	// 해당 위치의 적은 이미 제거되었음을 표시
                    killed[targetR][targetC] = true; 
                    // 적이 제거되었음을 표시
                    map[targetR][targetC] = 0;
                    cnt++; // 제거된 적 수 증가
                }
            }
            // 적이 아래로 한 칸씩 이동
            moveEnemies();
        }
        max = Math.max(cnt, max);
    }

    static boolean allClear() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 1) return false;
            }
        }
        return true;
    }
    
    static void moveEnemies() {
        for (int i = N - 1; i > 0; i--) {
            System.arraycopy(map[i - 1], 0, map[i], 0, M);
        }
        Arrays.fill(map[0], 0);
    }
}
