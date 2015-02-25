package form;


import java.io.UnsupportedEncodingException;
import java.util.List;

import logic.UnicodeUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

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
        String inUnicode = _form.getTextInUnicode().getText().toUpperCase().replaceAll(" ", "");
        String encodeStr = "";
        String bomStr = "";
        Text textOutStr = _form.getTextOutStr();
        Text textOutBom = _form.getTextOutBom();
//        _form.getTextOutBom().setText("");

        if (_form.getRdUTF16().getSelection()) {
        	encodeStr = UnicodeUtil.encodeUTF16(inUnicode);
            if (inUnicode.startsWith(UnicodeUtil.UTF16_BOM_HEX.replaceAll(" ", ""))) {
                bomStr = UnicodeUtil.UTF16_BOM_HEX;
            }
        } else if (_form.getRdUTF8().getSelection()) {
        	encodeStr = UnicodeUtil.encodeUTF8(inUnicode);
            if (inUnicode.startsWith(UnicodeUtil.UTF8_BOM_HEX.replaceAll(" ", ""))) {
                bomStr = UnicodeUtil.UTF8_BOM_HEX;
            }
        } else if (_form.getRdUTF32().getSelection()) {
        	encodeStr = UnicodeUtil.encodeUTF32(inUnicode);
            if (inUnicode.startsWith(UnicodeUtil.UTF32_BOM_HEX.replaceAll(" ", ""))) {
                bomStr = UnicodeUtil.UTF32_BOM_HEX;
            }
        }
        textOutStr.setText(encodeStr);
        textOutBom.setText(bomStr);
	}

	/**
	 * 「Unicodeコードポイント→サロゲートペア」のテキストボックス変更イベント処理
	 * @author Tetsuya
	 *
	 */
	protected class ModifyText_textInCodepoint implements ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {

			String codepointText = _form.getTextInCodepoint().getText();
			if (codepointText.length() < 5) {
				_form.getTextOutHighSurrogate().setText("");
				_form.getTextOutLowSurrogate().setText("");
				setError(_form.getTextInCodepoint(), false);
				return;
			}

			String highSurrogate = "";
			String lowSurrogate = "";

            try {

				String[] surrogatePair = UnicodeUtil.convCodepoint2SurrogatePair(codepointText);
				highSurrogate = surrogatePair[0];
				lowSurrogate = surrogatePair[1];
				setError(_form.getTextInCodepoint(), false);

			} catch (Exception e1) {

				e1.printStackTrace();
				setError(_form.getTextInCodepoint(), true);
			}

            _form.getTextOutHighSurrogate().setText(highSurrogate);
            _form.getTextOutLowSurrogate().setText(lowSurrogate);
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

	private void setError(Control control, boolean isError) {
		int foreColorVal = isError ? SWT.COLOR_WHITE : SWT.COLOR_BLACK;
		int backColorVal = isError ? SWT.COLOR_RED : SWT.COLOR_WHITE;

		control.setForeground(SWTResourceManager.getColor(foreColorVal));
		control.setBackground(SWTResourceManager.getColor(backColorVal));
	}
}
