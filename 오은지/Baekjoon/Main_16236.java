/*
[ Notion url ]
https://www.notion.so/Algol-115634ecbbc48048a999c7f49465ed3b?p=121634ecbbc480249b38f7a49df56e3d&pm=s
*/
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
    0: 빈 칸
    1~6: 물고기의 크기
    9: 아기 상어 위치
    
     처음 아기 상어의 크기 : 2 
     사방 이동하면서 크기의 개수만큼 물고기를 먹으면 상어 크기 +1 증가
     
     -> 1~6이 더이상 없다면 끝 
         --> 이때까지 걸리는 시간 출력
     -> 물고기가 2마리 이상이면 가까운 물고기에게 간다.
         --> 물고기에게 갈 때 지나야 하는 칸 수로 비교
         --> 거리가 같다면 가장 위에 있는 물고기, 그 다음으로는 가장 왼쪽에 있는 물고기에게 간다. 
         -----> 우선순위 큐로 거리가 가깝고, 위쪽에 있으며, 왼쪽에 있는 물고기를 우선적으로 선택
     -> 물고기를 먹으면, 그 칸은 빈 칸이 된다.
     
     // 물고기를 찾아 먹기 bfs
 */

public class Main_16236 { // 아기 상어
    static int N,startX, startY,sharkSize,eatenFish, time;
    static int[][] map;
    static boolean[][] visited;
    static StringTokenizer st;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        sharkSize = 2; // 아기상어 크기
        eatenFish = 0; // 먹은 물고기의 크기
        time = 0; // 소요 시간
        
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 9) { // 시작 위치
                    startX = i;
                    startY = j;
                    map[i][j] = 0;
                }
            }
        }
        
        while(true) {
            // BFS
            // 가장 가까운 물고기 찾기
            int[] nearFish = bfs(startX, startY);
            if(nearFish == null) break;
            
            // 물고기 먹고 상어 갱신
            time += nearFish[2];
            startX = nearFish[0];
            startY = nearFish[1];
            map[startX][startY] = 0;
            
            eatenFish++;
            if(eatenFish == sharkSize) {
                sharkSize++;
                eatenFish = 0;
            }
        }
        
        System.out.println(time);
        
    }

    static int[] bfs(int r, int c) {
    	// 우선순위 큐로 거리,행,열 순으로 정렬
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> {
            if (a[2] != b[2]) return a[2] - b[2];  // 거리 비교
            if (a[0] != b[0]) return a[0] - b[0];  // 행 비교
            return a[1] - b[1];  // 열 비교
        });
        
        visited = new boolean[N][N];        
        queue.offer(new int[] {r,c,0}); // 0 : 거리
        visited[r][c] = true;
        
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];
            int cd = cur[2];
    
            if(map[cr][cc] > 0 && map[cr][cc] < sharkSize) {
                return cur;
            }
            
            for (int d = 0; d < 4; d++) {
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                
                if(check(nr, nc)) {
                    queue.offer(new int[] {nr,nc,cd+1});
                    visited[nr][nc] = true;
                }
            }
        } // while
        
        return null;
    }
    
    static boolean check(int R, int C) {
        return R >= 0 && R < N && C >= 0 && C < N && !visited[R][C] && map[R][C] <= sharkSize;
    }


}