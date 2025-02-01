package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1205 { // 등수 구하기
	static int N,newScore,P,rank,cnt;
	static int[] curScore;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		newScore = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		
		curScore = new int[N];
		
		if(N>0) {
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				curScore[i] = Integer.parseInt(st.nextToken());
			}
		}
		
		rank = 1;
		cnt = 0;
		
		for (int i = 0; i < N; i++) {
			if(curScore[i] > newScore) rank++;
			else if(curScore[i] == newScore) rank = rank;
			else break;
			cnt++;
		}
		
		if(cnt >= P &&curScore[N-1] >= newScore) {
			System.out.println(-1);
		} else {
			System.out.println(rank);
		}
		
	}
}
