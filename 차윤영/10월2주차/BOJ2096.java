import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2096 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][3];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 초기 최대 및 최소 점수 배열 -> memoization
        int[] maxArr = new int[3];
        int[] minArr = new int[3];

        // 첫 번째 줄 초기화
        for (int j = 0; j < 3; j++) {
            maxArr[j] = map[0][j];
            minArr[j] = map[0][j];
        }

        // 점수 계산
        for (int i = 1; i < N; i++) {
            int[] newMaxArr = new int[3];
            int[] newMinArr = new int[3];

            for (int j = 0; j < 3; j++) {
                newMaxArr[j] = Integer.MIN_VALUE;
                newMinArr[j] = Integer.MAX_VALUE;
            }

            for (int j = 0; j < 3; j++) {
                for (int k = -1; k <= 1; k++) {
                    int nj = j + k;
                    if (nj >= 0 && nj < 3) {
                    	// 현재 칸에 도달할 수 있는 이전 칸 까지의 점수에서, 현재 칸의 점수를 더한 값 중
                    	// 가장 큰 값과, 작은 값 선정
                        newMaxArr[j] = Math.max(newMaxArr[j], maxArr[nj] + map[i][j]);
                        newMinArr[j] = Math.min(newMinArr[j], minArr[nj] + map[i][j]);
                    }
                }
            }

            maxArr = newMaxArr;
            minArr = newMinArr;
        }

        // 최종 점수 계산
        // 마지막 행에서 가장 큰 / 작은 점수 선택
        int maxScore = Math.max(maxArr[0], Math.max(maxArr[1], maxArr[2]));
        int minScore = Math.min(minArr[0], Math.min(minArr[1], minArr[2]));
        System.out.println(maxScore + " " + minScore);
    }
}
