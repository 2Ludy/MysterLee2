package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1202 { // 보석 도둑
	// 총 N개의 보석 
	// 각 보석은 무게M, 가격v 를 가짐
	// 가방에 1개만 넣을 수 있고. 최대 무게는 C
	// 가방 K개 가지고 있음

	// 가방 무게 오름차순 정렬, 가방마다 C무게 이하인 보석 중 가장 비싼 보석 선택
	// PriortyQueue 활용!
	
	static class Jewel implements Comparable<Jewel> {
		int weight, price;
		
		public Jewel(int weight, int price) {
			this.weight = weight;
			this.price = price;
		}

		@Override
		public int compareTo(Jewel o) {
			return this.weight - o.weight;
		}
	}
	
	static int N,K,M,V,C;
	static PriorityQueue<Jewel> jewels;
	static int[] maxWeight;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 보석 총 개수
		K = Integer.parseInt(st.nextToken()); // 가방 개수
		jewels = new PriorityQueue<>();
		maxWeight = new int[K];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken()); // 무게
			V = Integer.parseInt(st.nextToken()); // 가격
			jewels.add(new Jewel(M, V));
		}
		
		for (int i = 0; i < K; i++) {
			maxWeight[i] = Integer.parseInt(br.readLine());
		}
		
		// 가방 무게 오름차순 정렬
		Arrays.sort(maxWeight);
		
		// 가방에 담길 보석의 가격 저장용 내림차순 우선순위큐 생성
		PriorityQueue<Integer> prices = new PriorityQueue<Integer>(Collections.reverseOrder());
		
		long totalPrice = 0;
		
		for(int maxweight : maxWeight) {
			while(!jewels.isEmpty() && jewels.peek().weight <= maxweight) {
				prices.add(jewels.poll().price);
			}
			
			if(!prices.isEmpty()) totalPrice += prices.poll();
		}

		System.out.println(totalPrice);
	}

}
