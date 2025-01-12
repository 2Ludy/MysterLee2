package JAN_1;

/*
 * 이용객 p가 구간 s를 이용할 때, 고속철도를 이용하는 경우
 * -> s를 이용할 때, 고속철도 <= 항공편
 * -> s를 주파하는데 4시간 이하가 걸릴 때
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ32776 {

    static int S; // 역 a에서 역 b까지
    // S = Ma + F + Mb
    static int Ma, Mb; // 역 a ~ 공항 a, 공항 b ~ 역 b
    static int F; // 공항 a에서 공항 b까지
    static String[] ans = {"high speed rail", "flight"};
    static int fin;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        S = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        Ma = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());
        Mb = Integer.parseInt(st.nextToken());
        int flight = Ma + F + Mb;

        if (S < 4 * 60 || S <= flight) fin = 0;
        else fin = 1;
        
        System.out.println(ans[fin]);
    }
}
