package logic;

/**
 * IVSセレクタ文字列用のユーティリティクラス
 * @author Tetsuya
 * @see http://opcdiary.net/?p=27168
 */
public class IVSUtil {

    //日本の漢字の異字体セレクタはVariation Selector Supplementの範囲だけ使用される
    public static final char VariationSelectorSupplementHi = '\uDB40';
    public static final char VariationSelectorSupplementLoStart = '\uDD00';
    public static final char VariationSelectorSupplementLoEnd = '\uDDEF';
    //それ以外だと以下が使われている可能性がある
    public static final char VariationSelectorStart = '\uFE00';
    public static final char VariationSelectorEnd = '\uFE0F';
    //モンゴル文字用
    public static final char MongolianFreeVariationSelectorStart = '\u180B';
    public static final char MongolianFreeVariationSelectorEnd = '\u180d';

    /**
     *  文字列中の指定位置の文字がIVSのセレクタ文字列か判断する。
     * @param s  s 内の評価する文字の位置。
     * @param index s 内の評価する文字の位置。
     * @return s の index の位置にある文字がセレクタ文字列の場合は true。それ以外の場合は false。
     */
    public static boolean isVariationSelector(String s, int index)
    {
        return checkSelector(s.toCharArray()[index]);
    }

    /**
     * 文字がIVSのセレクタ文字列か判断する。
     * @param c 評価するUNICODE文字
     * @return 文字がセレクタ文字列の場合は true。それ以外の場合は false。
     */
    public static boolean isVariationSelector(char c)
    {
        return checkSelector(c);
    }

    /**
     * 文字がIVSのセレクタ文字列か判断する。
     * @param c 評価するUNICODE文字
     * @return 文字がセレクタ文字列の場合は true。それ以外の場合は false。
     */
    private static boolean checkSelector(char c)
    {
        if (c == VariationSelectorSupplementHi
            || (VariationSelectorSupplementLoStart <= c && c <= VariationSelectorSupplementLoEnd)
            || (VariationSelectorStart <= c && c <= VariationSelectorEnd)
            || (MongolianFreeVariationSelectorStart <= c && c <= MongolianFreeVariationSelectorEnd))
        {
            return true;
        }
        return false;
    }

    /**
     * 文字がCJKでよく使われるIVSのセレクタ文字列か判断する。
     * @param c 評価するUNICODE文字
     * @return 文字がセレクタ文字列の場合は true。それ以外の場合は false。
     */
	public static boolean checkVariationSelectorSupplement(char c)
    {
        if (c == VariationSelectorSupplementHi
            || (VariationSelectorSupplementLoStart <= c && c <= VariationSelectorSupplementLoEnd))
        {
            return true;
        }
        return false;
    }

    /**
     * 文字がIVSのセレクタ文字列か判断する。
     * @param c 評価するUNICODE文字
     * @return 文字がセレクタ文字列の場合は true。それ以外の場合は false。
     */
	public static boolean checkVariationSelector(char c)
    {
        if (VariationSelectorStart <= c && c <= VariationSelectorEnd)
        {
            return true;
        }
        return false;
    }

    /**
     * 文字がモンゴル語用のIVSのセレクタ文字列か判断する。
     * @param c 評価するUNICODE文字
     * @return 文字がセレクタ文字列の場合は true。それ以外の場合は false。
     */
	public static boolean checkMongolianFreeVariationSelector(char c)
    {
        if (MongolianFreeVariationSelectorStart <= c && c <= MongolianFreeVariationSelectorEnd)
        {
            return true;
        }
        return false;
    }
}
