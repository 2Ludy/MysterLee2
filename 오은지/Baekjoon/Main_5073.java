package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_5073 { // 삼각형과 세 변
	static int[] nums;
	
	public static void main(String[] args) throws Exception {
		// 세 변 길이가 모두 같으면 Equilateral
		// 세 변 길이가 모두 다르면 Scalene
		// 두 변 길이만 같으면 Isosceles
		// 긴 변 >= 남은 변의 합 이면 Invalid
		nums = new int[3];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			nums[0] = Integer.parseInt(st.nextToken());
			nums[1] = Integer.parseInt(st.nextToken());
			nums[2] = Integer.parseInt(st.nextToken());
			
			if(nums[0]+nums[1]+nums[2] == 0) break;
			
			Arrays.sort(nums);
			
			if(nums[2] >= nums[1]+nums[0]) {
				sb.append("Invalid\n");
			} else {
				if(nums[0] == nums[1] && nums[1] == nums[2]) sb.append("Equilateral\n");
				else if(nums[0] == nums[1] || nums[1] == nums[2] || nums[2] == nums[0]) sb.append("Isosceles\n"); 
				else sb.append("Scalene\n");
			}			
		}
		System.out.println(sb.toString());
	}
}
