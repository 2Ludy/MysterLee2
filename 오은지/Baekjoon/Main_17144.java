package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_17144 { // 미세먼지 안녕! (시뮬)
    static int R, C, T, sum;
    static int[][] map;
    static int[][] copyMap;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};    
    static List<Integer> machineR, machineC;
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken()); // 제한 시간
        
        map = new int[R][C];
        machineR = new ArrayList<>();
        machineC = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == -1) {
                    machineR.add(i);
                    machineC.add(j);
                }
            }
        }
        
        int time = 0;
        while(time < T) {
            dust(); // 미세먼지 확산
            machine(); // 공기청정기 작동
            time++;
        }
        
        sum = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] > 0) {
                    sum += map[i][j];
                }
            }
        }
        System.out.println(sum);
    }

    static void dust() {
        copyMap = new int[R][C];
        for (int i = 0; i < R; i++) {
            System.arraycopy(map[i], 0, copyMap[i], 0, C);
        }
        
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if(map[i][j] > 0) {
                    int diff = 0;
                    int amount = map[i][j] / 5;
                    
                    for (int d = 0; d < 4; d++) {
                        int nr = i + dr[d];
                        int nc = j + dc[d];
                        
                        if(check(nr, nc)) {
                            diff++;
                            copyMap[nr][nc] += amount;
                        }
                    }
                    copyMap[i][j] -= amount * diff;
                }
            }
        }
        
        for (int i = 0; i < R; i++) {
            System.arraycopy(copyMap[i], 0, map[i], 0, C);
        }
    }

    static boolean check(int x, int y) {
        return x >= 0 && x < R && y >= 0 && y < C && map[x][y] != -1;
    }

    static void machine() {
        int upR = machineR.get(0);
        int downR = machineR.get(1);

        // 위쪽 공기청정기 반시계 방향
        for (int i = upR-1; i > 0; i--) { // 아래 > 위
            map[i][0] = map[i-1][0];
        }
        for (int i = 0; i < C-1; i++) { // 왼 > 오
            map[0][i] = map[0][i+1];
        }
        for (int i = 0; i < upR; i++) { // 위 > 아래
            map[i][C-1] = map[i+1][C-1];
        }
        for (int i = C-1; i > 1; i--) { // 오 > 왼
            map[upR][i] = map[upR][i-1];
        }
        map[upR][1] = 0;
        
        // 아래쪽 공기청정기 시계 방향
        for (int i = downR+1; i < R-1; i++) { // 위 > 아래
            map[i][0] = map[i+1][0];
        }
        for (int i = 0; i < C-1; i++) { // 왼 > 오
            map[R-1][i] = map[R-1][i+1];
        }
        for (int i = R-1; i > downR; i--) { // 아래 > 위
            map[i][C-1] = map[i-1][C-1];
        }
        for (int i = C-1; i > 1; i--) { // 오 > 왼
            map[downR][i] = map[downR][i-1];
        }
        map[downR][1] = 0;
    }
}