/*
[ Notion url ]
https://www.notion.so/Algol-115634ecbbc48048a999c7f49465ed3b?p=121634ecbbc480249b38f7a49df56e3d&pm=s
*/
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4963 { // 섬의 개수 구하기 (dfs)
    // 1 : 땅 -> 8방 인접하면 1개의 섬
    // 0 : 바다
    static int w,h,cnt;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {-1,-1,-1,0,1,1,1,0};
    static int[] dc = {-1,0,1,1,1,0,-1,-1};
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        while(true) {
            st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken()); // 너비 
            h = Integer.parseInt(st.nextToken()); // 높이
            
            if(w == 0 && h == 0) {
                break;
            }
            
            map = new int[h][w];
            visited = new boolean[h][w];
            cnt = 0; //섬의 개수
            
            for (int i = 0; i < h; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < w; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (map[i][j] == 1 && !visited[i][j]) { // 땅이면서 아직 방문하지 않은 경우
                        dfs(i, j);
                        cnt++;
                    }
                }
            }
            
            System.out.println(cnt);            
        } // while        
    }

    // dfs
    static void dfs(int r, int c) {
        visited[r][c] = true;
        
        for (int d = 0; d < 8; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            
            // 경계, 방문, 땅 체크
            if(nr >= 0 && nr < h && nc >= 0 && nc < w && !visited[nr][nc] && map[nr][nc] == 1) {
                dfs(nr, nc); // 재귀 호출
            }            
        }
    }

}
