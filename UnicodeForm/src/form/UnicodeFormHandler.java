package form;


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
}
