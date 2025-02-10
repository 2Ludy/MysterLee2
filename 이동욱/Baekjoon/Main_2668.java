package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main_2668 {

	static int N;
	static int[] nums;
	static boolean[] visited;
	static PriorityQueue<Integer> answer;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		nums = new int[N+1];
		visited = new boolean[N+1];
		answer = new PriorityQueue<>();

		for(int i=1; i<=N; i++) {
			int num = Integer.parseInt(br.readLine());
			nums[i] = num;
		}

		a: for(int i=1; i<=N; i++) {
			if(!visited[i]) {
				visited[i] = true;
				boolean[] logic = new boolean[N+1];
				PriorityQueue<Integer> pq1 = new PriorityQueue<>();
				PriorityQueue<Integer> pq2 = new PriorityQueue<>();

				int tmp = i;
				while(!logic[tmp]) {
					logic[tmp] = true;
					int tmpValue = nums[tmp];
					pq1.offer(tmp);
					pq2.offer(tmpValue);
					tmp = tmpValue;
				}

				while(!pq1.isEmpty()) {
					int pollNum1 = pq1.poll();
					int pollNum2 = pq2.poll();
					if(pollNum1 != pollNum2) continue a;
				}

				for(int j=1; j<=N; j++) {
					if(logic[j]) {
						visited[j] = true;
						answer.offer(nums[j]);
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(answer.size()).append("\n");
		while(!answer.isEmpty()) {
			sb.append(answer.poll()).append("\n");
		}

		System.out.print(sb);
	}
}
