package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_5430 { // AC
	
	static int T,n;
	static String p,input;
	static char[] cmd;
	
	public static void main(String[] args) throws Exception {	
		// R : 순서 뒤집기
		// D : 첫번째 수 버리기
		// -> 비어있을떄 D 사용하면 error
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for (int t = 0; t < T; t++) {
			
			p = br.readLine(); // 함수 RDD
			n = Integer.parseInt(br.readLine()); // 배열 길이
			input = br.readLine().replace("[", "").replace("]", ""); // 입력 배열 대괄호 삭제
			
			List<Integer> list = new ArrayList<>();
			
			if(!input.isEmpty()) {
				StringTokenizer st = new StringTokenizer(input, ",");
				while(st.hasMoreTokens()) {
					list.add(Integer.parseInt(st.nextToken()));
				}
			}
			
			boolean isReversed = false;
			boolean isError = false;
			
			cmd = p.toCharArray();
			for (int i = 0; i < cmd.length; i++) {
				if(cmd[i] == 'R') {
					isReversed = !isReversed;
				} else if(cmd[i] == 'D') {
					if(list.isEmpty()) {
						sb.append("error\n");
						isError = true;
						break;
					} else {
						if(isReversed) list.remove(list.size()-1);
						else list.remove(0);
					}
					
				}
			}
			
			if(!isError) {
				if(isReversed) Collections.reverse(list);
				sb.append(list.toString().replace(" ", "")).append("\n");
			}
		}
		
		System.out.println(sb);
	}
}
