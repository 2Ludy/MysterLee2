package baekjoon;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2589 { // 보물섬
    static int N,C,res;
    static char[][] map;
    static boolean[][] visited;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[N][C];
        res = 0;
        
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for(int j=0; j < C; j++) {                
                map[i][j] = str.charAt(j);
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < C; j++) {
                if(map[i][j] == 'L') {
                    bfs(i,j);
                }
            }
        }
        
        System.out.println(res);
        
    }

     static void bfs(int r, int c) {
         Queue<int[]> que = new LinkedList<>(); 
         visited = new boolean[N][C];
         
         que.offer(new int[] {r,c,0});
         visited[r][c] = true;
        
        while(!que.isEmpty()) {
            int[] cur = que.poll();
            int cr = cur[0];
            int cc = cur[1];
            int cd = cur[2];

            res = Math.max(res, cd);
            
            for (int d = 0; d < 4; d++) {
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                
                if(check(nr,nc)) {
                    visited[nr][nc] = true;
                    que.offer(new int[] {nr, nc, cd+1});
                }
            }            
            
        }
        
    }

    static boolean check(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < C && !visited[x][y] && map[x][y] == 'L';
    }
}