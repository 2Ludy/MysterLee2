// https://velog.io/@2ludy/boj20056

package baekjoon; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_20056 {
	
	static int N, M, K;
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}; // 8방향
	static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
	static Queue<int[]> que; // int 배열의 각 인덱스 = r 좌표, c 좌표, m 질량, s 속력, d 방향
	static int[][] count; // 각 칸에 몇개의 파이어볼이 모이는지를 판별하는 배열
	static List<int[]> tmp; // 이동한 파이어볼들을 모두 담을 list
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 입력
		M = Integer.parseInt(st.nextToken()); // 입력
		K = Integer.parseInt(st.nextToken()); // 입력
	
		que = new LinkedList<>();
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine()); // 입력을 받아서
			int[] nums = new int[5]; 
			for(int j=0; j<5; j++) {
				nums[j] = Integer.parseInt(st.nextToken()); // nums 배열에 담을꺼야
			}
			nums[0]--; // 근데 r좌표랑 c좌표가 0부터 시작이 아닌 1부터 시작되니까 -1 해줄꺼야
			nums[1]--;
			que.offer(nums); // 그다음에 que에 넣어줄꺼야
		}
		
		while(K > 0) { // K번 이동한 후의 상태를 봐야 하므로, K가 0이 될때 까지 돌려보자
			count = new int[N][N]; // 1번 이동할 때 마다 파이어볼이 어디에 있는지 체크해야 하므로 여기서 초기화
			tmp = new ArrayList<>(); // 파이어볼들의 정보를 1번 이동할때마다 전부 넣어줘야 하므로 여기서 초기화
			while(!que.isEmpty()) { // que가 빌때까지 돌려 그럼!!
				int[] nums = que.poll(); // que에서 뽑아
				int r = nums[0]; // 0 인덱스는 r좌표
				int c = nums[1]; // 1 인덱스는 c좌표
				int m = nums[2]; // 2 인덱스는 질량
				int s = nums[3]; // 3 인덱스는 속력
				int d = nums[4]; // 4 인덱스는 방향
				r = r + dr[d]*s; // 좌표 결정하기 
				if(r>=0) { // r이 0보다 크면 그냥 나머지 구하면 돼 
					r = r % N;
				}else {
					r = N - (Math.abs(r) % N); // 0보다 작으면 절대값으로 나머지 구한 값을 N으로 빼면 돼
					if(r == N) r = 0; // 근데 r이 N일 수도 있는데 이때는 r이 0이 되어야 해
				}
				
				c = c + dc[d]*s; // c좌표도 r좌표와 동일
				if(c>=0) {
					c = c % N;
				}else {
					c = N - (Math.abs(c) % N);
					if(c == N) c = 0;
				}
				
				count[r][c]++; // 해당 좌표 위치에 파이어볼 누적 시키기
				tmp.add(new int[] {r, c, m, s, d}); // tmp에 정보 넣어주기
			}
			
			boolean[] visited = new boolean[tmp.size()]; // 방문 배열 초기화 해주기
			for(int i=0; i<tmp.size(); i++) { // tmp에서 하나씩 살펴볼꺼야
				if(visited[i]) continue; // 방문했으면 넘겨
				int[] nums = tmp.get(i); 
				int r = nums[0];
				int c = nums[1];
				int m = nums[2];
				int s = nums[3];
				int d = nums[4];
				int newD = 0; // 방향이 모두 짝수인지, 홀수인지를 판별하는 변수
				visited[i] = true; // 방문했으니 true
				
				if(count[r][c] == 1) { // 만약 파이어볼이 한개만 있으면 밑의 로직 할 필요 없이 que에 정보 넣으면 돼
					que.offer(nums); 
				}else {
					for(int j=0; j<tmp.size(); j++) { // 만약 파이어볼이 두개 이상이면 다시 돌려서 같은 위치에 있는 파이어볼 찾기
						if(visited[j]) continue; // 방문했으면 들릴 필요 없어
						int[] newNums = tmp.get(j); // 뽑아
						int nr = newNums[0];
						int nc = newNums[1];
						int nm = newNums[2];
						int ns = newNums[3];
						int nd = newNums[4];
						if(nr != r || nc != c) continue; // r좌표와 c좌표가 서로 다르면 넘겨
						m += nm; // 질량 누적시키고
						s += ns; // 속력도 누적시키고
						visited[j] = true; // 방문했으니 true
						if(d%2 != nd%2) newD = 1; // 만약 나머지(짝수, 홀수) 판별해서 한번이라도 다르면 변수 1로 만들어줘
					}
					if(m/5 == 0) continue; // 만약 m/5 값이 0이 되면 파이어볼은 소멸하니까 그 다음 순번 진행시켜
					for(int k=0; k<=6; k=k+2) { // que에 넣어줄껀데 방향은 변수 이용해서 넣어줄꺼야
						que.offer(new int[] {r, c, m/5, s/count[r][c], k+newD});
					}
				}
			}
			K--; // 1번의 이동이 모두 종료 되었으니 K-- 해주자
		}
		if(que.size() == 0) { // 여기 까지 도달했다면, K번의 이동이 종료되었다는것, 근데 que에 아무것도 없다면 파이어볼이 모두 소멸한것
			System.out.println(0); // 따라서 0 출력
		}else { // que에 뭔가 있다면 파이어볼이 1개 이상이라는 뜻
			int sum = 0; // 질량을 모두 더하기 위해 만든 변수
			while(!que.isEmpty()) {
				sum += que.poll()[2]; // 질량 누적하여 더해주고
				}
			System.out.println(sum); // 출력
		}
	}
}
