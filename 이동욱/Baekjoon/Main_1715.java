package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1715 {

	static int N;
	static Long count;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(int i=0; i<N; i++) {
			pq.offer(Integer.parseInt(br.readLine()));
		}

		count = 0L;
		while(!pq.isEmpty()) {
			if(pq.size() == 1) {
				break;
			}
			int num1 = pq.poll();
			int num2 = pq.poll();
			int sum = num1 + num2;

			pq.offer(sum);
			count += sum;
		}
		System.out.println(count);
	}
}
