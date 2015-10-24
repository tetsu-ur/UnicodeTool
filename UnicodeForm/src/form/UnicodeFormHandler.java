package form;


import java.io.UnsupportedEncodingException;
import java.util.List;

import logic.UnicodeUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

class UnicodeFormHandler {

	private UnicodeForm _form = null;

	private static int MAX_HIGHT = 620;

	/**
	 * コンストラクタ
	 * @param form
	 */
	public UnicodeFormHandler(UnicodeForm form) {
		this._form = form;
	}

	/**
	 * フォームのリサイズイベント処理
	 * @author Tetsuya
	 *
	 */
	protected class ControlAdapter_settings extends ControlAdapter {
		@Override
		public void controlResized(ControlEvent e) {
			// フォームの高さが一定以上ならそれ以上広げない
            if (e.getSource() instanceof Shell) {
                Shell shell = (Shell) e.getSource();
                if (shell.getSize().y >= MAX_HIGHT) {
                    shell.setSize(new Point(shell.getSize().x, MAX_HIGHT));
                    return;
                }
            }
		}
	}

	/**
	 * フォームクローズイベント処理
	 * @author Tetsuya
	 *
	 */
	protected class DisposedListener_widgetDisposed implements DisposeListener {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			// アプリケーション設定をファイルに書き出す
			_form.getSettings().save();
		}
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
                // 入力された文字をデコードして16進文字列で表示
                _form.getTextOutUTF8().setText(UnicodeUtil.decodeUTF8(inStr));
                _form.getTextOutUTF16().setText(UnicodeUtil.decodeUTF16(inStr));
                _form.getTextOutUTF32().setText(UnicodeUtil.decodeUTF32(inStr));
                // 各種方法で文字列長を表示
                _form.getTextOutStrLength().setText(String.valueOf(inStr.length()));
                _form.getTextOutCodepointCount().setText(String.valueOf(inStr.codePointCount(0, inStr.length())));
                List<UnicodeUtil.UnicodeInfo> infos = UnicodeUtil.createUnicodeList(inStr);
                _form.getTextOutIVSCount().setText(String.valueOf(infos.size()));
                // サロゲートペアやIVS情報を表示
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

        // 16進数以外の文字が入力されたらエラー
		if (!inUnicode.matches("^[0-9A-Fa-f]*$")) {
			textOutStr.setText("");
			textOutBom.setText("");
			setError(_form.getTextInUnicode(), true);
			return;
		}

		// 選択されたラジオボタンに対応したエンコードを行う
        if (_form.getRdUTF16().getSelection()) {
        	encodeStr = UnicodeUtil.encodeUTF(inUnicode, "UTF-16");
            if (inUnicode.startsWith(UnicodeUtil.UTF16_BOM_HEX.replaceAll(" ", ""))) {
                bomStr = UnicodeUtil.UTF16_BOM_HEX;
            }
        } else if (_form.getRdUTF8().getSelection()) {
        	encodeStr = UnicodeUtil.encodeUTF(inUnicode, "UTF-8");
            if (inUnicode.startsWith(UnicodeUtil.UTF8_BOM_HEX.replaceAll(" ", ""))) {
                bomStr = UnicodeUtil.UTF8_BOM_HEX;
            }
        } else if (_form.getRdUTF32().getSelection()) {
        	encodeStr = UnicodeUtil.encodeUTF(inUnicode, "UTF-32");
            if (inUnicode.startsWith(UnicodeUtil.UTF32_BOM_HEX.replaceAll(" ", ""))) {
                bomStr = UnicodeUtil.UTF32_BOM_HEX;
            }
        }
        textOutStr.setText(encodeStr);
        textOutBom.setText(bomStr);
		setError(_form.getTextInUnicode(), false);
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
			String highSurrogate = _form.getTextInHighSurrogate().getText();
			String lowSurrogate = _form.getTextInLowSurrogate().getText();
			Text textCodepoint = _form.getTextOutCodepoint();

			// 16進数以外の文字列が入力されたらエラー
			if (!highSurrogate.matches(UnicodeUtil.REG_HEX) || !lowSurrogate.matches(UnicodeUtil.REG_HEX)) {
				textCodepoint.setText("");
				setError((Control)e.getSource() , true);
				return;
			}

            try {
				textCodepoint.setText(UnicodeUtil.convSurrogatePair2Codepoint(highSurrogate, lowSurrogate));
			} catch (Exception e1) {
				e1.printStackTrace();
				setError((Control)e.getSource(), true);
				textCodepoint.setText("");
				return;
			}
			setError((Control)e.getSource(), false);
		}
	}

	private void setError(Control control, boolean isError) {
		int foreColorVal = isError ? SWT.COLOR_WHITE : SWT.COLOR_BLACK;
		int backColorVal = isError ? SWT.COLOR_RED : SWT.COLOR_WHITE;

		control.setForeground(SWTResourceManager.getColor(foreColorVal));
		control.setBackground(SWTResourceManager.getColor(backColorVal));
	}
}
