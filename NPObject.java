import java.util.ArrayList;

/*
	Integerの拡張として、因数分解または素数判定が行われたintを格納するラッパークラス
	素数判定及び因数分解を提供する
*/
public class NPObject {
	public static void main(String[] args){
		NPObject np = null;
		np = new NPObject(101);
		System.out.println(np.getInt());
		System.out.println(np.isPrimeNumber());
		System.out.println(np.toString());
		np = new NPObject(102);
		System.out.println(np.getInt());
		System.out.println(np.isPrimeNumber());
		System.out.println(np.toString());
		System.out.println(NPObject.isPrimeNumber(101));
		System.out.println(NPObject.factorization(101));
		System.out.println(NPObject.isPrimeNumber(102));
		System.out.println(NPObject.factorization(102));
	}
	
	private static final ArrayList<Integer> primenumbers = new ArrayList<>();
	static {
		primenumbers.add(2);
		primenumbers.add(3);
	}
	//public 因数分解メソッド
	public static String factorization(int n) {
		if (n>=0&&n<2) {
			return String.valueOf(n);
		}
		if (isPrimeNumber(n)) {
			return n+" = Prime_Number";
		}
		ArrayList<Integer> li = getFactorization(n);
		return getNumberExpression(li,n<0);
	}
	//因数分解メソッド
	private static ArrayList<Integer> getFactorization(int n) {
		ArrayList<Integer> li = new ArrayList<>();
		int index = 0;
		int I = 0;
		int count = 0;
		n*= (n<0)?-1:1;
		while (n != 1) {
			if (index + 1 > primenumbers.size()) {
				getPrimeNumber();
			}
			I = primenumbers.get(index);
			if (n % I == 0) {
				while (n % I == 0) {
					n /= I;
					count++;
				}
			}
			li.add(index, count);
			count = 0;
			index++;
		}
		return li;
	}
	private static String getNumberExpression(ArrayList<Integer> li,boolean flg) {
		if(li==null){
			throw new NullPointerException("ArrayList<Integer> li ==null");
		}
		StringBuilder sb = new StringBuilder();
		String s;
		int b = 0;
		for (int index = 0, size = li.size(); index < size; index++) {
			b = li.get(index);
			if (b == 0) {
				continue;
			}
			s = (b > 1) ? primenumbers.get(index) + "^" + b : String.valueOf(primenumbers.get(index));
			sb.append(s);
			if (index != size - 1) {
				sb.append("・");
			}
		}
		return (flg)? "-( "+sb.toString()+" )" : sb.toString();
	}
	//素数の補充
	private static void getPrimeNumber() {
		int n = primenumbers.get(primenumbers.size() - 1) + 2;
		while (true) {
			if (isPrimeNumber(n)) {
				primenumbers.add(n);
				break;
			} else {
				n += 2;
			}
		}
	}

	//素数判定
	public static boolean isPrimeNumber(int n) {
		if(n<2){
			return false;
		}
		if (primenumbers.contains(n)) {
			return true;
		}
		int in = (int) Math.sqrt(n);
		if (in % 2 == 0) {
			in++;
		}
		if (n % 2 == 0) {
			return false;
		}
		while (in < n / 2 + 1) {
			if (n % in == 0) {
				return false;
			}
			in += 2;
		}
		return true;
	}

	private boolean isPrimeNumberFlag = false;

	private int nm;

	private ArrayList<Integer> member = null;
	private boolean minusFlg = false;
	public NPObject(int n) {
		this.nm = n;
		if (n < 0) {
			minusFlg=true;
			return;
		}
		this.isPrimeNumberFlag = isPrimeNumber(n);
		if (isPrimeNumberFlag) {
			return;
		}
		this.member = getFactorization(n);
	}

	//このオブジェクトのintを返す
	public int getInt() {
		return nm;
	}

	//このオブジェクトが素数である場合,true
	public boolean isPrimeNumber() {
		return isPrimeNumberFlag;
	}

	//このオブジェクトが素数である場合素数、合成数の場合、因数分解
	public String toString() {
		return (this.isPrimeNumberFlag)?"Prime_Number":getNumberExpression(this.member,this.minusFlg);
	}
}
