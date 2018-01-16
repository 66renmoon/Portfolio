import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

/*
	weblioの辞書サービスを利用した単語の存在判定を提供するクラス
*/
public class GetweblioWords {
	private static PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	//引数1　判定対象の英単語（原形）
	public static void main(String[] args) {
		if(args.length==0){
			pw.println("引数がありません");
		}
		String searchWord= args[0];
		HttpsURLConnection hc =null;
		try {
			hc = (HttpsURLConnection) new URL("https://ejje.weblio.jp/content/"+ searchWord).openConnection();
			isExistforWeblio(hc);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void isExistforWeblio(HttpsURLConnection hc) throws IOException {
		String cash = null;
		BufferedReader br =new BufferedReader(new InputStreamReader(hc.getInputStream()));
		for(int i=0;i<30;i++){
			cash = br.readLine();
			if(cash==null){
				break;
			}
			if(cash.indexOf("robots")!=-1){
				pw.println("Not hit.");
				pw.close();
				return;
			}
		}
		pw.println("This word exists in weblio.");
		pw.close();
		br.close();
	}
}
