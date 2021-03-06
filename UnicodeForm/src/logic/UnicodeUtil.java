package logic;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnicodeUtil {

	public static String UTF16_BOM_HEX = "FEFF";

	public static String UTF8_BOM_HEX = "EF BB BF";

	public static String UTF32_BOM_HEX = "0000FEFF";

	public static String REG_HEX = "^[0-9A-Fa-f]*$";

	public static String decodeUTF8(String str) throws UnsupportedEncodingException {
		byte[] utf8 = str.getBytes("UTF-8");
		return cnvByte2Hex(utf8, 1);
	}

	public static String decodeUTF16(String str) throws UnsupportedEncodingException {
			byte[] utf16 = str.getBytes("UTF-16");
			return cnvByte2Hex(utf16, 2);
	}

	public static String decodeUTF32(String str) throws UnsupportedEncodingException {
		byte[] utf32 = str.getBytes("UTF-32");
		return cnvByte2Hex(utf32, 4);
	}

	/**
	 * 16進数の文字列をバイト配列に変換
	 * @param hex
	 * @return
	 * @see http://techracho.bpsinc.jp/baba/2011_09_03/4414
	 */
	public static byte[] hex2bin(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int index = 0; index < bytes.length; index++) {
			bytes[index] = (byte) Integer.parseInt(hex.substring(index * 2, (index + 1) * 2), 16);
		}
		return bytes;
	}

	/**
	 * 文字列を16進数表現にエンコード
	 * @param strUtf
	 * @param encodingName
	 * @return
	 */
	public static String encodeUTF(String strUtf, String encodingName) {
		String encodeString = "";

		try {
			encodeString = new String(hex2bin(strUtf.replaceAll(" ", "")), encodingName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encodeString;
	}

	public static String[] convCodepoint2SurrogatePair(String strCodePoint) {

		if (!strCodePoint.matches("^[a-fA-F0-9]{5,6}$")) {
			throw new NumberFormatException("コードポイントの値に16進数でない文字が含まれます。");
		}

		int codepoint = Integer.parseInt(strCodePoint,16);
		if (!isCodepoint(codepoint)) {
			throw new IllegalArgumentException("コードポイントの範囲ではありません。");
		}

		byte[] surrogatePairBytes = surrogate(codepoint);
		String[] surrogatePairStrs = {
				cnvByte2Hex(Arrays.copyOfRange(surrogatePairBytes, 0,2), 4),
				cnvByte2Hex(Arrays.copyOfRange(surrogatePairBytes, 2,4), 4)};
		return surrogatePairStrs;
	}

	public static String convSurrogatePair2Codepoint(String high, String low) {

		if (high == null || low == null || high.length() != 4 || low.length() !=4) {
			return "";
		}

		char highSurrogateChar = (char)Integer.parseInt(high, 16);
		char lowSurrogateChar = (char)Integer.parseInt(low, 16);

		if (!Character.isHighSurrogate(highSurrogateChar) || !Character.isLowSurrogate(lowSurrogateChar)) {
			throw new IllegalArgumentException("サロゲートのコード範囲ではありません。");
		}

		int codepoint = Character.toCodePoint(highSurrogateChar , lowSurrogateChar);

		return formatCodepoint(codepoint);
	}

    /**
     * Integer → byte配列変換
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

	public static String formatCodepoint(int codepoint) {
		String formatString = zeroSaples(cnvByte2Hex(cnvInt2Byte(codepoint),0));
		if (formatString.length() < 4) {
			formatString = String.format("%4s",formatString).replace(' ', '0');
		}

		return formatString;

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

	// Charactor.isBmpCodePoint
	// IVS サロゲートペアか
	// サロゲートペア文字か


	public static List<UnicodeInfo> createUnicodeList(String str) {

		List<UnicodeInfo> unicodeList = new ArrayList<>();
		char[] charArray = str.toCharArray();

		boolean isDelimit = true;
		UnicodeUtil util = new UnicodeUtil();
		UnicodeInfo info = null;

		for (int i=0; i <= charArray.length - 1 ; i++) {
			if (isDelimit) {
				if (info != null) {
					setLiteral(info);
					unicodeList.add(info);
				}
                info =  util.new UnicodeInfo();
                isDelimit = false;

			}

			char currentChar = charArray[i];

			// BMPコード単体文字の場合
			if (Character.isBmpCodePoint(currentChar) && !Character.isSurrogate(currentChar)) {
				info.setBmpChar(currentChar);

				if (i == charArray.length - 1) {
					isDelimit = true;
					continue;
				}

				// 次の char が IVS 以外の上位サロゲートまたサロゲート以外のBMPなら区切る
                char nextChar = charArray[i+1];
				if ((!IVSUtil.isVariationSelector(nextChar) && Character.isHighSurrogate(nextChar)) ||
						Character.isBmpCodePoint(nextChar) &&  !Character.isSurrogate(nextChar)) {
                    isDelimit = true;
				}

				continue;
			}

			// 上位サロゲート(Variation Selector の上位サロゲートを除く)
			if (!IVSUtil.isVariationSelector(currentChar) &&
					Character.isHighSurrogate(currentChar) &&
					info.getHighSurrogateChar() == 0) {

				info.setHighSurrogateChar(currentChar);
				continue;
			}

			// 下位サロゲート(Variation Selector の下位サロゲートを除く)
			if (!IVSUtil.isVariationSelector(currentChar) &&
					Character.isLowSurrogate(currentChar) &&
					info.getHighSurrogateChar() != 0  &&
					info.getLowSurrogateChar() == 0) {

				info.setLowSurrogateChar(currentChar);

				if (i + 1 < charArray.length && !IVSUtil.isVariationSelector(charArray[i+1])) {
					isDelimit = true;
				}

				continue;
			}

			// Variation Selector の上位サロゲート
			if (IVSUtil.isVariationSelector(currentChar) &&
					Character.isHighSurrogate(currentChar) &&
					info.getIvsHighSurrogateChar() == 0) {

				info.setIvsHighSurrogateChar(currentChar);
				continue;
			}

			// Variation Selector の下位サロゲート
			if (IVSUtil.isVariationSelector(currentChar) &&
					Character.isLowSurrogate(currentChar) &&
					info.getIvsHighSurrogateChar() != 0) {

				info.setIvsLowSurrogateChar(currentChar);
				isDelimit = true;
				continue;
			}
		}


        if (info != null) {
            setLiteral(info);
            unicodeList.add(info);
        }

		return unicodeList;
	}

	public static String getSurrogatePairString(List<UnicodeInfo> unicodeInfos) {

		StringBuilder sb = new StringBuilder();
		for (UnicodeInfo info : unicodeInfos) {
			if (info.getHighSurrogateChar() != 0) {
				sb.append(info.getLiteral());
			}
		}
		return sb.toString();
	}

	public static String getIVString(List<UnicodeInfo> unicodeInfos) {

		StringBuilder sb = new StringBuilder();
		for (UnicodeInfo info: unicodeInfos) {
			if (info.getIvsHighSurrogateChar() != 0) {
				sb.append(info.getLiteral());
			}
		}
		return sb.toString();
	}

	public static int countMongolianIVS(List<UnicodeInfo> unicodeInfos) {

		int count = 0;
		for (UnicodeInfo info: unicodeInfos) {
			if (IVSUtil.checkMongolianFreeVariationSelector(info.getIvsHighSurrogateChar())) {
				count++;
			}
		}
		return count;
	}

	public static int countJapaneseIVS(List<UnicodeInfo> unicodeInfos) {

		int count = 0;
		for (UnicodeInfo info: unicodeInfos) {
			if (IVSUtil.checkVariationSelectorSupplement(info.getIvsHighSurrogateChar())) {
				count++;
			}
		}
		return count;
	}

	public static int countOtherIVS(List<UnicodeInfo> unicodeInfos) {

		int count = 0;
		for (UnicodeInfo info: unicodeInfos) {
			if (IVSUtil.checkVariationSelector(info.getIvsHighSurrogateChar())) {
				count++;
			}
		}
		return count;
	}

	private static void setLiteral(UnicodeInfo info) {
		char[] chars = new char[info.getCharCount()];
		int idx = 0;

		if (info.getBmpChar() != 0) {
			chars[idx++] = info.getBmpChar();
		}

		if (info.getHighSurrogateChar() != 0) {
			chars[idx++] = info.getHighSurrogateChar();
		}

		if (info.getLowSurrogateChar() != 0) {
			chars[idx++] = info.getLowSurrogateChar();
		}

		if (info.getIvsHighSurrogateChar() != 0) {
			chars[idx++] = info.getIvsHighSurrogateChar();
		}

		if (info.getIvsHighSurrogateChar() != 0) {
			chars[idx++] = info.getIvsLowSurrogateChar();
		}

		info.setLiteral(new String(chars));
	}

	/**
	 * Unicode の表示上の１文字の情報
	 * @author Tetsuya
	 *
	 */
	public class UnicodeInfo {

		private char bmpChar;
		private char highSurrogateChar;
		private char lowSurrogateChar;
		private char ivsHighSurrogateChar;
		private char ivsLowSurrogateChar;
		private int charCount;
		private String literal;

		public char getBmpChar() {
			return bmpChar;
		}
		public void setBmpChar(char bmpChar) {
			this.bmpChar = bmpChar;
			this.incrementCharCount();
		}
		public char getHighSurrogateChar() {
			return highSurrogateChar;
		}
		public void setHighSurrogateChar(char highSurrogateChar) {
			this.highSurrogateChar = highSurrogateChar;
			this.incrementCharCount();
		}
		public char getLowSurrogateChar() {
			return lowSurrogateChar;
		}
		public void setLowSurrogateChar(char lowSurrogateChar) {
			this.lowSurrogateChar = lowSurrogateChar;
			this.incrementCharCount();
		}
		public char getIvsHighSurrogateChar() {
			return ivsHighSurrogateChar;
		}
		public void setIvsHighSurrogateChar(char ivsHighSurrogateChar) {
			this.ivsHighSurrogateChar = ivsHighSurrogateChar;
			this.incrementCharCount();
		}
		public char getIvsLowSurrogateChar() {
			return ivsLowSurrogateChar;
		}
		public void setIvsLowSurrogateChar(char ivsLowSurrogateChar) {
			this.ivsLowSurrogateChar = ivsLowSurrogateChar;
			this.incrementCharCount();
		}
		public int getCharCount() {
			return charCount;
		}
		public void incrementCharCount() {
			this.charCount += 1;
		}
		public String getLiteral() {
			return literal;
		}
		public void setLiteral(String literal) {
			this.literal = literal;
		}

	}

	/**
	 * コードポイントの範囲かを判定
	 * @param codepoint
	 * @return
	 */
	public static boolean isCodepoint(int codepoint) {
		return 0x0020 <= codepoint && codepoint <= 0x10FFFF;
	}
}
