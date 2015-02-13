package logic;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class UnicodeUtil {

	public static String decodeUTF16(String str) throws UnsupportedEncodingException {
			byte[] utf16 = str.getBytes("UTF-16");
			return cnvByte2Hex(utf16, 2);
	}

	public static String encodeUTF16(String strUtf16) {
		char[] chars = strUtf16.replaceAll(" ", "").toCharArray();
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i <= chars.length - 1; i = i+4) {

			if (i+4 > chars.length) {
				break;
			}
			
            String codeunit = new String(Arrays.copyOfRange(chars, i, i+4));
            sb.append((char)Integer.parseInt(codeunit,16));
		}
		
		return sb.toString();
	}

	public static String[] convCodepoint2SurrogateParir(String strCodePoint) {
		byte[] surrogatePairBytes = surrogate(Integer.parseInt(strCodePoint,16));
		String[] surrogatePairStrs = {
				cnvByte2Hex(Arrays.copyOfRange(surrogatePairBytes, 0,2), 4),
				cnvByte2Hex(Arrays.copyOfRange(surrogatePairBytes, 2,4), 4)};
		return surrogatePairStrs;
	}
	
	public static String convSurrogatePair2Codepoint(String high, String low) {
		
		if (high == null || low == null || high.isEmpty() || low.isEmpty()) {
			return "";
		}
		
		//char[] chars = {(char)Integer.parseInt(high, 16), (char)Integer.parseInt(low, 16)};
		int codepoint = Character.toCodePoint(
				(char)Integer.parseInt(high, 16), (char)Integer.parseInt(low, 16));
		
		return zeroSaples(cnvByte2Hex(cnvInt2Byte(codepoint),0));
	}
	
    /**
     * byte配列変換
     *
     * @param value
     *            変換対象の値
     * @return byte配列に変換された値
     * @see http://lifeofsnufkin.blog63.fc2.com/blog-entry-354.html
     */
    public static byte[] cnvInt2Byte(int value) {
        int arraySize = Integer.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
        return buffer.putInt(value).array();
    }  
	
	/**
	* コードポイントからサロゲートペアバイト配列にしてます。
	* チェック処理してない
	* @param codepoint
	* @return
	* @see http://programamemo2.blogspot.jp/2010/11/java.html
	*/
	static byte[] surrogate(int codepoint) {
        int c = codepoint;// 21bit
        c -= 0x10000;
        int c1 = (c >>> (10));// 10bit
        int c2 = 0x3FF & c;// 10bit
        byte[] high = bytes(c1 + 0xD800);
        byte[] low = bytes(c2 + 0xDC00);
        byte[] bs = mergeLowerBytes(high, low);
        return bs;
	}

	/**
	* サロゲート文字からコードポイントを求める。
	* チェック処理してない
	* @param surrogate
	* @return
	* @see http://programamemo2.blogspot.jp/2010/11/java.html
	*/
	static int codepoint(int surrogate) {
        int c = surrogate;// 32bit
        // high
        int high = 0xffff0000 & c;
        int low = 0x0000ffff & c;
        high >>>= 16;
        high -= 0xD800;
        low -= 0xDC00;
        high <<= 10;
        int merge = (high + low) + 0x10000;
        return merge;
	}
	
	 /**
	* intをバイト配列にします。
	* @param a
	* @return
	* @see http://programamemo2.blogspot.jp/2010/11/java.html
	*/
	static byte[] bytes(int a) {
        byte[] bs = new byte[4];
        bs[3] = (byte) (0x000000ff & (a));
        bs[2] = (byte) (0x000000ff & (a >> 8));
        bs[1] = (byte) (0x000000ff & (a >> 16));
        bs[0] = (byte) (0x000000ff & (a >> 24));
        return bs;
	}

	/**
	*
	* @param bs1
	* @param bs2
	* @return
	* @see http://programamemo2.blogspot.jp/2010/11/java.html
	*/
	static byte[] mergeLowerBytes(byte[] bs1, byte[] bs2) {
        byte[] bs = new byte[4];

        System.arraycopy(bs1, 2, bs, 0, 2);
        System.arraycopy(bs2, 2, bs, 2, 2);
        return bs;
	}
	
	private static String cnvByte2Hex(byte[] strByte, int delimitIntervalByte) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i <= strByte.length - 1 ; i++) {
			sb.append(Integer.toHexString((strByte[i] & 0xF0) >> 4));
			sb.append(Integer.toHexString(strByte[i] & 0xF));
			
			if (delimitIntervalByte > 0 && (i + 1) % delimitIntervalByte == 0) {
                sb.append(" ");
			}
		}
		
		return sb.toString().toUpperCase().trim();
	}
	
	/**
	* ゼロサプレス.
	*
	* @param inStr 入力文字列
	* @return 変換結果文字列
	*/
	public static String zeroSaples(final String inStr) {
	    if (null == inStr || "".equals(inStr)) {
	        return "";
	    }
	    String result = inStr;
	    final String prefix = "0";
	    while (result.startsWith(prefix)) {
	        result = result.substring(1);
	    }
	    return result;
	}
}
