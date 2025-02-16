package FEB_2;

import java.io.*;
import java.util.*;

public class BOJ1240 {

    static int N, M;
    static List<int[]>[] tree; // 트리를 인접 리스트로 저장
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 인접 리스트 초기화
        tree = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        // 트리 입력 받기
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int D = Integer.parseInt(st.nextToken());
            tree[A].add(new int[]{B, D});
            tree[B].add(new int[]{A, D});
        }

        // M개의 노드 쌍에 대해 BFS 실행
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            sb.append(bfs(start, end)).append("\n");
        }

        System.out.println(sb);
    }

    // BFS를 사용하여 두 노드 사이의 최단 거리 찾기
    private static int bfs(int start, int end) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[] visited = new boolean[N + 1];

        queue.add(new int[]{start, 0});
        visited[start] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0];
            int dist = curr[1];

            // 목적지 도착하면 거리 반환
            if (node == end) {
                return dist;
            }

            for (int[] next : tree[node]) {
                int nextNode = next[0];
                int nextDist = next[1];

                if (!visited[nextNode]) {
                    visited[nextNode] = true;
                    queue.add(new int[]{nextNode, dist + nextDist});
                }
            }
        }

        return -1; // 도달 불가능한 경우 (트리에서는 발생하지 않음)
    }
}

