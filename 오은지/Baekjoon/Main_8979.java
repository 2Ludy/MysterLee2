package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_8979 { // 센서
	
	static int N, K;
	
	public static void main(String[] args) throws Exception {	
		// N개 센서가 주어짐.
		// 집중국 설치해야함, 최대 K개 설치 가능
		
		// 센서 수신 가능 영역 조절 가능
		// 집중국의 수신 가능한 영역의 거리의 합의 최소값 출력
		
		// 센서 개수 N
		// 집중국 개수 K
		// N개의 센서 좌표
		// 예제 ----------------
		// 1-3 6-6-7-9
		// => 2+3 = 5
		// => 집중국 개수 K개의 묶음으로 나뉘어야 함
		// => 각 묶음의 최대값-최소값이 곧 길이이고, 모든 묶음의 길이합이 최소여야 함
		
		// 3 6-7-8 10-12 14-15 18-20
		// => 0+2+2+1+2 = 7
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 센서 리스트
		List<Integer> sensor = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			sensor.add(Integer.parseInt(st.nextToken()));
		}
		
		if(K >= N) {
			System.out.println(0);
			return;
		}
		
		Collections.sort(sensor);

		// 센서 간 거리 리스트
		List<Integer> dis = new ArrayList<>();
		for (int i = 1; i < N; i++) {
			dis.add(sensor.get(i) - sensor.get(i-1));
		}
		
		Collections.sort(dis);
		
		int result = 0;
		for (int i = 0; i < N-K; i++) {
			result += dis.get(i);
		}
		
		System.out.println(result);
	}
}
