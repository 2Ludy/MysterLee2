package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10431 { // 줄세우기
	static int P,T,cnt;
	static int[] height;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		P = Integer.parseInt(br.readLine());
		
		while(P>0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			T = Integer.parseInt(st.nextToken());
			height = new int[20];
			
			for (int i = 0; i < 20; i++) {
				height[i] = Integer.parseInt(st.nextToken());
			}
			
			cnt = 0;
			for (int i = 0; i < 20; i++) {
				for (int j = i+1; j < 20; j++) {
					if(height[i] > height[j]) {
						cnt++;
					}
				}
			}
			System.out.println(T+" "+cnt);
			P--;
		}
	}
}
