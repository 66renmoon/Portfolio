import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*
	ネットワーク上に存在する
	jpegファイルをURLからダウンロードするメソッドを提供する
*/
public class Download_to_URL_IMG {
	//引数1　保存先URL,ディレクトリの場合新規にファイルを作成
	//引数2　対象の画像UR
	private static File file = null;
	public static void main(String[] args) {
		HttpURLConnection hc = null;
		ArrayList<Integer> byteList = null;
		try {
			if (args.length != 2) {
				throw new IOException("引数が異常です");
			}
			file = new File(args[0]);
			hc = imgConnection(args[1]);
			init();
			byteList = download(hc);
			outputForFile(file, byteList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void init() throws IOException {
		if (file.isDirectory()) {
			file = new File(file.getPath()+"JPEG.jpg");
		}
		if (file.exists()) {
			throw new IOException("ファイルがすでに存在します");
		} else {
			file.createNewFile();
		}
	}

	private static HttpURLConnection imgConnection(String surl) throws IOException {
		HttpURLConnection hc = null;
		hc = (HttpURLConnection) new URL(surl).openConnection();
		int state = hc.getResponseCode();
		if (state != 200) {
			throw new IOException("ステータスコードが異常です　" + state);
		}
		String[] mimetype = hc.getContentType().split("/");
		
		if (!(mimetype[0].equals("image") && mimetype[1].equals("jpeg"))) {
			throw new IOException("ContentTypeが異常です　" + mimetype[0] + "/" + mimetype[1]);
		}
		return hc;
	}

	private static ArrayList<Integer> download(HttpURLConnection hc) throws IOException {
		if (hc == null) {
			throw new IOException();
		}
		InputStream is = hc.getInputStream();
		ArrayList<Integer> bylist = new ArrayList<>();
		int cash = -1;
		while ((cash = is.read()) != -1) {
			bylist.add(cash);
		}
		return bylist;
	}

	private static void outputForFile(File file, ArrayList<Integer> bylist) throws IOException {
		try  {
			FileOutputStream fos = new FileOutputStream(file);
			for (int b : bylist) {
				fos.write(b);
			}
			fos.flush();
			fos.close();
		} catch (IOException e) {
			file.delete();
			throw new IOException("ファイルが正常に書き込まれませんでした");
		}
	}
}
