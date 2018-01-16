import java.util.Random;


/*
	パスワード規約に基づいて、ランダムな長さのパスワードを生成するプログラム
*/
public class PasswordManager {
	static Random r = new Random();

	static final int MAXLENGTH = 32;
	static final int MINLENGTH = 8;

	static final int TYPEMAX = 4;
	static final double TYPE0_PERCENTAGE = 0.2;
	static final char[] PASSSYMBOL = { '_', '-', '#','~','&','%' };
	static final char[] PASSCHARA = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	static int TYPE0_MAX = -1;
	static int TYPE2_3_MAX = -1;

	static int TYPE0_USEMAX = -1;
	//パスワード規約:英数字小文字大文字最低使用および記号の最低使用
	//パスワードは記号から始まらない
//type0:記号,type1:数字,type2:アルファベット小文字,type3:アルファベット大文字
	public static void main(String[] args) {
		int len = r.nextInt(MAXLENGTH - MINLENGTH) + MINLENGTH;
		int[] past = new int[len];
		TYPE0_USEMAX = (int) (len * TYPE0_PERCENTAGE);
		TYPE0_MAX = PASSSYMBOL.length;
		TYPE2_3_MAX = PASSCHARA.length;
		initPast(past);
		char[] password = createPassword(past);
		System.out.println();
		for(char c:password){
			System.out.print(c);
		}
	}
	public static int[] initPast(int[] past) {
		int[] typenums = new int[TYPEMAX-1];
		int cash = 0,len = past.length;
		len-=typenums[0]=(len/2);
		len-=typenums[1]=(len/3);
		typenums[2]=len;
		len = past.length;
		//ランダムに初期化
		for(int i = 0;i<len;i++){
			cash =r.nextInt(TYPEMAX-1);
			if(typenums[cash]==0){
				i--;
				continue;
			}
			past[i]=cash+1;
			typenums[cash]--;
		}
		//ラムダムに入れ替え
		int index = 0,temp=-1;
		for(int i = 0;i<len;i++){
			cash = r.nextInt(len);
			index = r.nextInt(len);
			index = cash==index?++cash%len:index;
			temp = past[cash];
			past[cash] = past[index];
			past[index] = temp;
		}
		//symbolの挿入
		for(int i = 1;i<TYPE0_USEMAX;i++){
			cash = r.nextInt(len-1)+1;
			if(past[cash]==0){
				i--;
				continue;
			}
			past[cash] = 0;
		}
		return past;
	}
	public static char[] createPassword(int[] past){
		int len=past.length;
		StringBuilder buf = new StringBuilder();
		for(int i = 0;i<len;i++ ){
			switch(past[i]){
			case 0:
				buf.append(PASSSYMBOL[r.nextInt(TYPE0_MAX)]);
				break;
			case 1:
				buf.append(r.nextInt(10));
				break;
			case 2:
				buf.append(PASSCHARA[r.nextInt(TYPE2_3_MAX)]);
				break;
			case 3:
				buf.append(String.valueOf(PASSCHARA[r.nextInt(TYPE2_3_MAX)]).toUpperCase());
				break;
			}
		}
		return buf.toString().toCharArray();
	}
}
