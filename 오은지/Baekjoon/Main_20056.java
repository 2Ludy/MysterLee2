package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_20056 { // 마법사 상어와 파이어볼
    static int n, m, k;
    static List<FireBall>[][] map;
    static List<FireBall> balls;
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
    
    static class FireBall {
        int r, c, m, s, d;
        FireBall(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken()); // 맵 크기 
        m = Integer.parseInt(st.nextToken()); // 파이어볼 개수
        k = Integer.parseInt(st.nextToken()); // 이동 횟수
        
        map = new ArrayList[n][n]; // 맵
        balls = new ArrayList<>(); // 파이어볼 리스트
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
        
        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1; // 행 (인덱스-1)
            int c = Integer.parseInt(st.nextToken()) - 1; // 열 (인덱스-1)
            int m = Integer.parseInt(st.nextToken()); // 질량
            int s = Integer.parseInt(st.nextToken()); // 속력
            int d = Integer.parseInt(st.nextToken()); // 방향
            balls.add(new FireBall(r, c, m, s, d));
        }
        
        for(int t = 0; t < k; t++) {
            move(); // 1. 파이어볼 이동 시뮬
            merge(); // 2. 같은 칸 파이어볼 합치기 시뮬
        }
        
        int total = 0; // 남은 파이어볼 질량 합
        for(int i = 0; i < balls.size(); i++) {
            FireBall b = balls.get(i);
            total += b.m;
        }
        System.out.println(total);
    }
    
    // 1. 이동 시뮬
    static void move() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                map[i][j].clear();
            }
        }
        
        for(int i = 0; i < balls.size(); i++) {
            FireBall b = balls.get(i);
            b.r = (b.r + dr[b.d] * b.s % n + n) % n;
            b.c = (b.c + dc[b.d] * b.s % n + n) % n;
            map[b.r][b.c].add(b);
        }
    }
    
    // 2. 합치기 + 새로운 파이어볼로 나누기 시뮬
    static void merge() {
        List<FireBall> newBalls = new ArrayList<>();
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(map[i][j].size() == 1) {
                    newBalls.add(map[i][j].get(0));
                    continue;
                }
                if(map[i][j].size() < 2) continue;
                
                int mSum = 0;
                int sSum = 0;
                boolean even = true;
                boolean odd = true;
                
                for(int k = 0; k < map[i][j].size(); k++) {
                    FireBall b = map[i][j].get(k);
                    mSum += b.m;
                    sSum += b.s;
                    if(b.d % 2 == 0) odd = false;
                    else even = false;
                }
                
                int nm = mSum / 5;
                if(nm == 0) continue;
                
                int ns = sSum / map[i][j].size();
                int nd = even || odd ? 0 : 1;
                
                for(int d = nd; d < 8; d += 2) {
                    newBalls.add(new FireBall(i, j, nm, ns, d));
                }
            }
        }
        
        balls = newBalls;
    }
    
}