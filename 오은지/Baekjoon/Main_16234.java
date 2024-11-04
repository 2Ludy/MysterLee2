package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_16234 { // 인구 이동 // dfs + 시뮬
    static int N,L,R,sum;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    static List<int[]> nearMap;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 맵 크기
        L = Integer.parseInt(st.nextToken()); // 최소 인구차이
        R = Integer.parseInt(st.nextToken()); // 최대 인구차이
        map = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        System.out.println(simul());
    
    }

    static int simul() {
        int day = 0; // 이동 일수
        boolean moved = true; // 이동 여부
        
        while(moved) {
            moved = false;
            visited = new boolean[N][N];

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(!visited[i][j]) {
                        nearMap = new ArrayList<>(); // 연합국 좌표 리스트
                        sum = 0; // 연합 인구수 합
                        dfs(i, j);
                        
                        if(nearMap.size() >= 2) {
                            int avg = sum / nearMap.size(); // 평균 인구수
                            for(int[] per : nearMap) { // 연합국 인구 수정
                                map[per[0]][per[1]] = avg;
                            }
                            moved = true; // 이동 발생했음!
                        }
                    }
                }
            }
            if(moved) day++; // 발생했으면 날짜++
        }
        return day; // 총 이동일수
    }

    // 연합 찾기
    static void dfs(int r, int c) {
        visited[r][c] = true;
        nearMap.add(new int[] {r,c,});
        sum += map[r][c];
        
        for (int d = 0; d < 4; d++) {
            int nr = r+dr[d];
            int nc = c+dc[d];
            
            if(check(nr,nc)) {
                int dif = Math.abs(map[r][c] - map[nr][nc]);
                if(dif >= L && dif <= R) dfs(nr,nc);
            }
            
        }
    }
    
    static boolean check(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && !visited[x][y];
    }
    
}