// https://velog.io/@2ludy/boj17135
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_17135 {
	
	static int N, M, D;
	static List<Integer>[] dis; // 궁수로부터 모든 적들의 거리를 나타내는....
	static int[] dr = {-1, 1, 0, 0}; // 좌 우 상 하 
	static int[] dc = {0, 0, -1, 1};
	static List<Integer> rIndex; // 적의 r 좌표를 담을 리스트
	static List<Integer> cIndex; // 적의 c 좌표를 담을 리스트
	static boolean[] visited; // 방문배열로 인덱스 관리 
	static int[] p; // 궁수의 위치 3개를 택하기 위한 배열
	static int count, max; // 적이 총 몇명인지를 담는 변수, 물리칠 수 있는 최대의 적을 담는 변수
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		p = new int[3];
		dis = new ArrayList[M]; // 리스트 배열로 거리 계산, 배열 사이즈는 M 사이즈로, list 배열의 인덱스는(list의 인덱스 말고)
								// 궁수의 위치를 나타냄
		rIndex = new ArrayList<>(); // 적의 r 좌표를 담을 리스트
		cIndex = new ArrayList<>(); // 적의 c 좌표를 담을 리스트 
		
		for(int i=0; i<M; i++) {
			dis[i] = new ArrayList<>(); // 리스트 초기화 
		}
		 
		for(int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c=0; c<M; c++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 1) { // 만약 다음입력에 1이 있다면.... 적이 있다는 것임!!
					rIndex.add(r); // 적의 r 좌표 add
					cIndex.add(c); // 적의 c 좌표 add
					count++; // 적이 있으니 count + 1 
					for(int k=0; k<M; k++) { // 모든 궁수로부터 해당 적의 거리를 계산하기 위한 for문
						int distance = N-r + Math.abs(c-k); // 각 궁수로부터 해당 적까지의 거리 계산
						dis[k].add(distance); // 각 궁수의 리스트에 해당 거리를 넣어줌
					}
				}
			}
		} // 입력 끝
		// count가 곧 사이즈 list 배열 하나하나의 크기는 모두 count로 동일 index로 관리
		max = 0; // 물리칠 수 있는 적의 최솟값은 0 이므로 0으로 초기화
		mC3(0,0); // 궁수 3명을 골라 로직을 시작하는 메서드
		
		System.out.println(max); // 다 돌고 밑으로 내려왔으니 max 출력 
	}

	private static void mC3(int idx, int cnt) { // 궁수 중 3명을 택하여 로직을 시작하는 메서드 
		if(cnt == 3) { // 궁수 3명이 선택되었으면 
			game(); // 로직 시작 
			return; // 로직 끝나면 돌아가 
		}
		for(int j=idx; j<M; j++) { 
			p[cnt] = j; // p배열에 해당 index를 넣어줌
			mC3(j+1, cnt+1); // 중복되지 않게 3명을 택하기 위함.
		}
	}

	private static void game() { // 디펜스 게임 로직 메서드 
		 visited = new boolean[count]; // 게임 할때마다 visited 배열 초기화 해야함, 해당 배열은 적이 처치되거나
		 							   // 성이 있는 칸으로 이동하여 게임에서 제외됨을 판별하기 위한 배열
		 int time = 0; // 시간이 얼마나 지났는지를 나타내는 변수, 시간이 지날때마다 적과 궁수 사이의 거리도 1씩 가까워짐
		 int kill = 0; // 궁수들이 얼마나 많은 적을 공격했는지를 나타내기 위한 변수
		 int enemy = count; // 총 적의 수를 담을 변수 
		 while(enemy > 0) {
			 Set<Integer> set = new HashSet<>(); // 중복된 적을 공격할 수 있으므로 set으로 중복 삭제
			 int[] nums0 = {D+1, 20, -1}; // 첫번째 궁수가 타격하는 거리, c좌표, 인덱스를 담을 배열
			 int[] nums1 = {D+1, 20, -1}; // 두번째 궁수가 타격하는 거리, c좌표, 인덱스를 담을 배열
			 int[] nums2 = {D+1, 20, -1}; // 세번째 궁수가 타격하는 거리, c좌표, 인덱스를 담을 배열
			 
			 for(int i=0; i<count; i++) { // 시간이 1씩 지날때마다 모든 적들의 index를 살펴봄
				if(visited[i]) continue; // 해당 적은 공격받았거나, 성에 도달한 적으로 제외시킴
				
				if(nums0[0] > (dis[p[0]].get(i)-time)) { // 만약 다음 적을 타격할 수 있는 거리가 더 가깝다면 갱신
					nums0[0] = dis[p[0]].get(i)-time; // 타격거리 갱신
					nums0[1] = cIndex.get(i); // c좌표 갱신
					nums0[2] = i; // 적의 index 갱신 
				}else if(nums0[0] != D+1 && nums0[0] == (dis[p[0]].get(i)-time)) { // 만약 다음 적을 타격할 수 있는 거리가 동일 하다면
					if(nums0[1] > cIndex.get(i)) { // 더 왼쪽에 있는 적이 우선순위가 높으므로 C좌표 비교하여 C좌표가 더 작으면 갱신
						nums0[1] = cIndex.get(i); // 어처피 거리는 같으니 c좌표 갱신
						nums0[2] = i; // index 갱신 
					}
				}
				// 밑으로 2번째, 3번째 궁수 동일 
				if(nums1[0] > (dis[p[1]].get(i)-time)) {
					nums1[0] = dis[p[1]].get(i)-time;
					nums1[1] = cIndex.get(i);
					nums1[2] = i;
				}else if(nums1[0] != D+1 && nums1[0] == (dis[p[1]].get(i)-time)) {
					if(nums1[1] > cIndex.get(i)) {
						nums1[1] = cIndex.get(i);
						nums1[2] = i;
					}
				}
				
				if(nums2[0] > (dis[p[2]].get(i)-time)) {
					nums2[0] = dis[p[2]].get(i)-time;
					nums2[1] = cIndex.get(i);
					nums2[2] = i;
				}else if(nums2[0] != D+1 && nums2[0] == (dis[p[2]].get(i)-time)) {
					if(nums2[1] > cIndex.get(i)) {
						nums2[1] = cIndex.get(i);
						nums2[2] = i;
					}
				}
			 }
			 // 디펜스가 종료 된 후 적의 인덱스를 판별하여 초기값이 -1(존재하지 않는다는뜻)이 아니라면
			 // 물리친 적이 존재하므로 set에 넣어서 중복제거 
			 if(nums0[2] != -1) {
				 set.add(nums0[2]);
			 }
			 if(nums1[2] != -1) {
				 set.add(nums1[2]);
			 }
			 if(nums2[2] != -1) {
				 set.add(nums2[2]);
			 }
			 
			 // 중복 제거 되었으니 set에서 빼내서 
			 // 해당 인덱스 true로 변경해주고
			 // 물리친 적의 수는 + 1 , 남은 적의 수는 - 1
			 for(int num : set) {
				 kill++;
				 enemy--;
				 visited[num] = true;
			 }
			 // 게임 종료되었으니 시간이 1만큼 지남
			 time++;
			 
			 // 다음 게임을 위해 성에 도달한 적이 존재한다면 제거 
			 for(int i=0; i<count; i++) {
				 if(rIndex.get(i) == N-time) { // 모든 인덱스를 돌아보며 적의 r인덱스가 성의 r인덱스와 같다면
					 if(visited[i]) continue; // 근데 이미 제외되어있으면 넘어가면됨
					 visited[i] = true; // 제외가 안되어있었다면 제외시켜주고
					 enemy--; // 남은 적의 수만 줄여주면 됨
				 }
			 }
			
		 }
		 max = Math.max(kill, max); // 출력
	}
}