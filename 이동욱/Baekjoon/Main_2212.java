package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2212 {

	static int N, K;
	static PriorityQueue<Integer> pq;
	static List<Integer> list;
	static int answer;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine());
		pq = new PriorityQueue<>();

		for(int i=0; i<N; i++) {
			pq.offer(Integer.parseInt(st.nextToken()));
		}

		int prev = pq.poll();
		list = new ArrayList<>();

		while(!pq.isEmpty()) {
			int next = pq.poll();
			if(prev == next) continue;

			list.add(next-prev);
			prev = next;
		}

		Collections.sort(list);
		answer = 0;

		for(int i=0; i<list.size()-(K-1); i++) {
			answer += list.get(i);
		}

		System.out.println(answer);

	}
}
