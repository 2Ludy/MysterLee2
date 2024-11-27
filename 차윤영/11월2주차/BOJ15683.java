import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * [감시]
 * 총 K개의 CCTV가 설치
 * 1: 우
 * 2: 우, 좌
 * 3: 상, 우
 * 4: 상, 우, 좌
 * 5: 상, 우, 하, 좌
 * 각각 바라보는 방향이 다름
 * -> 90도씩 회전 가능
 * 벽을 만날 때 까지 해당 방향으로 모두 감시 가능
 */

public class BOJ15683 {

	static int N, M; // 세로, 가로 길이
    static int[][] map; // 원본 맵
    static int min = Integer.MAX_VALUE; // 최소 사각지대
    static List<int[]> cctvs = new ArrayList<>(); // CCTV 위치 (행, 열, 타입)
    static int[][] directions = { // 상, 우, 하, 좌 (방향 순서)
        {-1, 0}, // 상
        {0, 1},  // 우
        {1, 0},  // 하
        {0, -1}  // 좌
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] >= 1 && map[i][j] <= 5) {
                    cctvs.add(new int[] {i, j, map[i][j]}); // CCTV 정보 (행, 열, 종류)
                }
            }
        }

        // 모든 CCTV에 대해 가능한 모든 방향을 고려하여 사각지대 최소화
        dfs(0, new int[cctvs.size()]);

        System.out.println(min);
    }

    // CCTV 회전 가능한 4가지 방향에 대해 시도하는 함수
    static void dfs(int idx, int[] rotation) {
        if (idx == cctvs.size()) {
            // 모든 CCTV의 방향이 결정되었으면 맵을 갱신하고 사각지대 계산
            int[][] tempMap = new int[N][M];
            for (int i = 0; i < N; i++) {
                System.arraycopy(map[i], 0, tempMap[i], 0, M);
            }

            // 각 CCTV를 회전시키면서 감시 영역을 갱신
            for (int i = 0; i < cctvs.size(); i++) {
                int[] cctv = cctvs.get(i);
                int r = cctv[0], c = cctv[1], type = cctv[2];
                int rot = rotation[i];
                watch(r, c, type, rot, tempMap);
            }

            // 감시 후, 사각지대의 크기를 계산
            min = Math.min(min, countBlindSpots(tempMap));
            return;
        }

        // 현재 CCTV에 대해 4가지 회전 방향에 대해 시도
        for (int rot = 0; rot < 4; rot++) {
            rotation[idx] = rot; // 현재 CCTV의 회전 상태 저장
            dfs(idx + 1, rotation); // 다음 CCTV로 넘어감
        }
    }

    // CCTV가 감시할 수 있는 영역을 갱신하는 함수
    static void watch(int r, int c, int type, int rot, int[][] tempMap) {
        int[] dirs = getDirections(type, rot);

        for (int dir : dirs) {
            int nr = r, nc = c;
            while (true) {
                nr += directions[dir][0];
                nc += directions[dir][1];

                // 벽을 만나면 종료
                if (nr < 0 || nr >= N || nc < 0 || nc >= M || tempMap[nr][nc] == 6) break;

                // CCTV가 감시할 수 있는 곳을 9로 표시
                if (tempMap[nr][nc] == 0) {
                    tempMap[nr][nc] = 9;
                }
            }
        }
    }

    // CCTV 종류에 따른 감시 가능한 방향을 반환하는 함수
    static int[] getDirections(int type, int rot) {
        switch (type) {
            case 1:
                // 1번 CCTV는 1개 방향을 감시 (회전시킬 수 있음)
                if (rot == 0) return new int[] {0}; // 상
                if (rot == 1) return new int[] {1}; // 우
                if (rot == 2) return new int[] {2}; // 하
                return new int[] {3}; // 좌
            case 2:
                // 2번 CCTV는 2개 방향을 감시 (회전시킬 수 있음)
                if (rot == 0) return new int[] {1, 3}; // 우, 좌
                if (rot == 1) return new int[] {0, 2}; // 상, 하
                if (rot == 2) return new int[] {3, 1}; // 좌, 우
                return new int[] {2, 0}; // 하, 상
            case 3:
                // 3번 CCTV는 3개 방향을 감시 (회전시킬 수 있음)
                if (rot == 0) return new int[] {0, 1}; // 상, 우
                if (rot == 1) return new int[] {1, 2}; // 우, 하
                if (rot == 2) return new int[] {2, 3}; // 하, 좌
                return new int[] {3, 0}; // 좌, 상
            case 4:
                // 4번 CCTV는 4개 방향을 감시 (회전시킬 수 있음)
                if (rot == 0) return new int[] {0, 1, 3}; // 상, 우, 좌
                if (rot == 1) return new int[] {0, 1, 2}; // 상, 우, 하
                if (rot == 2) return new int[] {1, 2, 3}; // 우, 하, 좌
                return new int[] {2, 3, 0}; // 하, 좌, 상
            case 5:
                // 5번 CCTV는 4방향 모두 감시
                return new int[] {0, 1, 2, 3}; // 우, 상, 하, 좌
            default:
                return new int[] {};
        }
    }

    // 사각지대의 개수를 세는 함수
    static int countBlindSpots(int[][] tempMap) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tempMap[i][j] == 0) { // 감시되지 않은 영역은 사각지대
                    count++;
                }
            }
        }
        return count;
    }
}
