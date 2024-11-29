package nov_4주차;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 수열 A의 가장 긴 증가하는 부분 수열 (순서 바꾸지 X)
 */

public class BOJ12015 {
	
	static int N; // 주어진 배열의 길이
	static int[] A; // 주어진 배열
	static List<Integer> list; // 부분 수열 저장할 리스트

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		A = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		list = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			int location = binary(list, A[i]);
			
			// 만약 location이 list의 크기와 같으면 A[i]가 가장 큰 값이므로 list의 가장 끝에 추가
			if (location == list.size()) {
				list.add(A[i]);
			}
			// location의 위치보다 A[i]가 더 작은 경우, 값을 갱신 
			// location 위치에 A[i] 값을 새로 넣음
			else {
				list.set(location, A[i]);
			}
		}
		
		// 부분수열을 저장한 리스트의 길이 출력
		System.out.println(list.size());

	}
	
	// 이진탐색
	static int binary(List<Integer> tmp, int x) {
		int left = 0;
		int right = tmp.size();
		while (left < right) { 
			int mid = (left + right) / 2;
			// 중간값이 찾는 값보다 작다면 left를 옮김
			if (tmp.get(mid) < x) {
				left = mid + 1;
			} 
			// 중간값이 찾는 값보다 크다면 right를 옮김
			else if (x < tmp.get(mid)){
				right = mid;
			}
		}
		
		return left;
	}

}
