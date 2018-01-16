import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Main {
	static PrintWriter		Wer	= new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	static BufferedReader	Rer	= new BufferedReader(new InputStreamReader(System.in));

	static char[] alp = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	static int enc = 0;
	static char[] password = null;
	static String template = null;
	
	/*
		paiza B40のリファクタリング
	*/
	public static void main(String[] args){
		try {
			init();
			for(int i = 0;i<enc;i++){
				method();
			}
			Wer.println(password);
			Wer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			Wer.close();
			try {
				Rer.close();
			} catch (IOException e) {
			}
		}
	}
	static void init() throws IOException{
		String[] cash = Rer.readLine().split(" ");
		enc = Integer.parseInt(cash[0]);
		template = cash[1];
		password = Rer.readLine().toCharArray();
	}
	static void method() {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for(char c : password){
			if(c==' '){
				sb.append(" ");
				continue;
			}
			index = template.indexOf(c);
			sb.append(alp[index]);
		}
		password = sb.toString().toCharArray();
	}
}