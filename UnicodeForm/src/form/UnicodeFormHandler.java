package form;


import java.io.UnsupportedEncodingException;
import java.util.List;

import logic.UnicodeUtil;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

class UnicodeFormHandler {

	private UnicodeForm _form = null;

	/**
	 * コンストラクタ
	 * @param form
	 */
	public UnicodeFormHandler(UnicodeForm form) {
		this._form = form;
	}

	/**
	 * 「文字 → Unicode」のテキストボックス変更イベント処理
	 * @author Tetsuya
	 *
	 */
	protected class ModifyText_textInStr implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
            try {
                String inStr = _form.getTextInStr().getText();
                _form.getTextOutUTF8().setText(UnicodeUtil.decodeUTF8(inStr));
                _form.getTextOutUTF16().setText(UnicodeUtil.decodeUTF16(inStr));
                _form.getTextOutUTF32().setText(UnicodeUtil.decodeUTF32(inStr));
                _form.getTextOutStrLength().setText(String.valueOf(inStr.length()));
                _form.getTextOutCodepointCount().setText(String.valueOf(inStr.codePointCount(0, inStr.length())));
                List<UnicodeUtil.UnicodeInfo> infos = UnicodeUtil.createUnicodeList(inStr);
                _form.getTextOutIVSCount().setText(String.valueOf(infos.size()));
                _form.getTextOutSurrogatePairStrings().setText(UnicodeUtil.getSurrogatePairString(infos));
                _form.getTextOutIVSStrings().setText(UnicodeUtil.getIVString(infos));
                _form.getTextOutMongolianIVSCount().setText(String.valueOf(UnicodeUtil.countMongolianIVS(infos)));
                _form.getTextOutJapaneseIVSCount().setText(String.valueOf(UnicodeUtil.countJapaneseIVS(infos)));
                _form.getTextOutOtherIVSCount().setText(String.valueOf(UnicodeUtil.countOtherIVS(infos)));
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
		}
	}

	/**
	 * 「Unicode → 文字」のテキストボックス変更イベント処理
	 * @author Tetsuya
	 */
	protected class ModifyText_textInUnicode implements ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
			// 「Unicode → 文字」のテキストボックスの Unicode をエンコードしてテキストボックスに設定
			setStringTextFromUnicode();
		}
	}

	/**
	 * 「Unicode → 文字」のエンコードラジオボタン選択イベント処理
	 * @author Tetsuya
	 *
	 */
	protected class Selected_rdUTFEncode implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}

		@Override
        public void widgetSelected(SelectionEvent e) {
			// 「Unicode → 文字」のテキストボックスの Unicode をエンコードしてテキストボックスに設定
			setStringTextFromUnicode();
        }
	}

	/**
	 * 「Unicode → 文字」のテキストボックスの Unicode をエンコードしてテキストボックスに設定
	 */
	private void setStringTextFromUnicode() {

        Text textInUnicode = _form.getTextInUnicode();
        Text textOutStr = _form.getTextOutStr();

        if (_form.getRdUTF16().getSelection()) {
            textOutStr.setText(UnicodeUtil.encodeUTF16(textInUnicode.getText()));
        } else if (_form.getRdUTF8().getSelection()) {
            textOutStr.setText(UnicodeUtil.encodeUTF8(textInUnicode.getText()));
        } else if (_form.getRdUTF32().getSelection()) {
            textOutStr.setText(UnicodeUtil.encodeUTF32(textInUnicode.getText()));
        }
	}

	/**
	 * 「Unicodeコードポイント→サロゲートペア」のテキストボックス変更イベント処理
	 * @author Tetsuya
	 *
	 */
	protected class ModifyText_textInCodepoint implements ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
            String[] surrogatePair =
                    UnicodeUtil.convCodepoint2SurrogateParir(_form.getTextInCodepoint().getText());
            _form.getTextOutHighSurrogate().setText(surrogatePair[0]);
            _form.getTextOutLowSurrogate().setText(surrogatePair[1]);
		}
	}

	/**
	 * 「サロゲートペア→Unicodeコードポイント」のテキストボックス変更イベント処理
	 * @author Tetsuya
	 *
	 */
	protected class ModifyText_TextInSurrogatePair implements ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
            _form.getTextOutCodepoint().setText(UnicodeUtil.convSurrogatePair2Codepoint(
                    _form.getTextInHighSurrogate().getText(), _form.getTextInLowSurrogate().getText()));
		}
	}
}
