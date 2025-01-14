package JAN_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ32775 {

    static int S; // 역 a에서 역 b까지
    // S = 역 a ~ 공항 a + F + 공항 b ~ 역 b
    static int F; // 공항 a에서 공항 b까지
    static String[] ans = {"high speed rail", "flight"};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        S = Integer.parseInt(br.readLine());
        F = Integer.parseInt(br.readLine());

        if (S > F) {
            System.out.println(ans[1]);
        } else {
            System.out.println(ans[0]);
        }
    }

}
