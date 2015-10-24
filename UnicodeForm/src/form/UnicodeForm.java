package form;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class UnicodeForm {


	@SuppressWarnings("unused")
	private DataBindingContext m_bindingContext;

	protected Shell shlUnicode;
	private Group grpUnicodeToStr;
	private Text textInStr;
	private Text textOutUTF8;
	private Text textOutUTF16;
	private Text textOutUTF32;
	private Label lblTf;
	private Label lblUtf_1;
	private Text textInCodepoint;
	private Text textOutHighSurrogate;
	private Text textOutLowSurrogate;
	private Text textInUnicode;
	private Label label_2;
	private Text textOutStr;
	private Label label_3;
	private Text textInHighSurrogate;
	private Label label_4;
	private Text textInLowSurrogate;
	private Text textOutCodepoint;
	private Label label_5;
	private Label label_6;
	private Composite cmpInfos;

	private UnicodeFormSettings settings = new UnicodeFormSettings();
	private Button rdUTF8;
	private Button rdUTF16;
	private Button rdUTF32;

	private UnicodeFormHandler handler = new UnicodeFormHandler(this);
	private Text textOutStrLength;
	private Label lblStringcodepointcount;
	private Text textOutCodepointCount;
	private Label lblIvsCount;
	private Text textOutIVSCount;
	private Text textOutMongolianIVSCount;
	private Text textOutJapaneseIVSCount;
	private Label label_9;
	private Text textOutSurrogatePairStrings;
	private Text textOutOtherIVSCount;
	private Label lblIvs;
	private Text textOutIVSStrings;
	private Label lblBom;
	private Text textOutBom;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					UnicodeForm window = new UnicodeForm();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlUnicode.open();
		shlUnicode.layout();
		while (!shlUnicode.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlUnicode = new Shell(Display.getDefault(), SWT.CLOSE | SWT.RESIZE | SWT.MIN | SWT.TITLE | SWT.SYSTEM_MODAL);
		shlUnicode.setMinimumSize(new Point(730, 620));
		shlUnicode.addControlListener(handler.new ControlAdapter_settings());
		shlUnicode.addDisposeListener(handler.new DisposedListener_widgetDisposed());
		shlUnicode.setSize(730, 630);
		shlUnicode.setText("Unicodeツール");
		shlUnicode.setLayout(new FormLayout());

		Group grpStrToUnicode = new Group(shlUnicode, SWT.NONE);
		grpStrToUnicode.setLayout(new FormLayout());
		FormData fd_grpStrToUnicode = new FormData();
		fd_grpStrToUnicode.height = 200;
		fd_grpStrToUnicode.bottom = new FormAttachment(0, 318);
		fd_grpStrToUnicode.top = new FormAttachment(0, 10);
		fd_grpStrToUnicode.left = new FormAttachment(0, 10);
		fd_grpStrToUnicode.right = new FormAttachment(100, -10);
		grpStrToUnicode.setLayoutData(fd_grpStrToUnicode);
		grpStrToUnicode.setText("文字→Unicode");

		textInStr = new Text(grpStrToUnicode, SWT.BORDER | SWT.V_SCROLL);
		textInStr.setData(textInStr);
		textInStr.addModifyListener(handler.new ModifyText_textInStr());
		FormData fd_textInStr = new FormData();
		fd_textInStr.height = 50;
		fd_textInStr.right = new FormAttachment(100, -10);
		fd_textInStr.top = new FormAttachment(0, 7);
		fd_textInStr.left = new FormAttachment(0, 7);
		textInStr.setLayoutData(fd_textInStr);

		grpUnicodeToStr = new Group(shlUnicode, SWT.NONE);
		FormData fd_grpUnicodeToStr = new FormData();
		fd_grpUnicodeToStr.top = new FormAttachment(grpStrToUnicode, 6);
		fd_grpUnicodeToStr.left = new FormAttachment(grpStrToUnicode, 0, SWT.LEFT);
		fd_grpUnicodeToStr.right = new FormAttachment(100, -10);
		fd_grpUnicodeToStr.height = 100;

		textOutUTF8 = new Text(grpStrToUnicode, SWT.BORDER);
		textOutUTF8.setEditable(false);
		textOutUTF8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutUTF8 = new FormData();
		fd_textOutUTF8.right = new FormAttachment(100, -10);
		fd_textOutUTF8.top = new FormAttachment(textInStr, 6);
		textOutUTF8.setLayoutData(fd_textOutUTF8);

		Label lblUtf = new Label(grpStrToUnicode, SWT.NONE);
		fd_textOutUTF8.left = new FormAttachment(lblUtf, 69);
		FormData fd_lblUtf = new FormData();
		fd_lblUtf.top = new FormAttachment(textInStr, 9);
		fd_lblUtf.left = new FormAttachment(0, 14);
		lblUtf.setLayoutData(fd_lblUtf);
		lblUtf.setText("UTF-8");

		textOutUTF16 = new Text(grpStrToUnicode, SWT.BORDER);
		textOutUTF16.setEditable(false);
		textOutUTF16.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutUTF16 = new FormData();
		fd_textOutUTF16.top = new FormAttachment(textInStr, 36);
		fd_textOutUTF16.right = new FormAttachment(100, -10);
		textOutUTF16.setLayoutData(fd_textOutUTF16);

		textOutUTF32 = new Text(grpStrToUnicode, SWT.BORDER);
		textOutUTF32.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutUTF32.setEditable(false);
		FormData fd_textOutUTF32 = new FormData();
		fd_textOutUTF32.top = new FormAttachment(textOutUTF16, 6);
		fd_textOutUTF32.right = new FormAttachment(100, -10);
		textOutUTF32.setLayoutData(fd_textOutUTF32);

		lblTf = new Label(grpStrToUnicode, SWT.NONE);
		fd_textOutUTF16.left = new FormAttachment(lblTf, 62);
		FormData fd_lblTf = new FormData();
		fd_lblTf.top = new FormAttachment(lblUtf, 12);
		fd_lblTf.left = new FormAttachment(0, 14);
		lblTf.setLayoutData(fd_lblTf);
		lblTf.setText("UTF-16");

		lblUtf_1 = new Label(grpStrToUnicode, SWT.NONE);
		fd_textOutUTF32.left = new FormAttachment(lblUtf_1, 62);
		FormData fd_lblUtf_1 = new FormData();
		fd_lblUtf_1.top = new FormAttachment(lblTf, 12);
		fd_lblUtf_1.left = new FormAttachment(0, 14);
		lblUtf_1.setLayoutData(fd_lblUtf_1);
		lblUtf_1.setText("UTF-32");

		label_9 = new Label(grpStrToUnicode, SWT.NONE);
		label_9.setText("サロゲートペア文字列");
		FormData fd_label_9 = new FormData();
		fd_label_9.left = new FormAttachment(textInStr, 0, SWT.LEFT);
		label_9.setLayoutData(fd_label_9);

		textOutSurrogatePairStrings = new Text(grpStrToUnicode, SWT.BORDER);
		fd_label_9.top = new FormAttachment(textOutSurrogatePairStrings, 3, SWT.TOP);
		textOutSurrogatePairStrings.setEditable(false);
		textOutSurrogatePairStrings.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutSurrogatePairStrings = new FormData();
		fd_textOutSurrogatePairStrings.top = new FormAttachment(textOutUTF32, 73);
		fd_textOutSurrogatePairStrings.right = new FormAttachment(100, -10);
		fd_textOutSurrogatePairStrings.left = new FormAttachment(label_9, 17);
		textOutSurrogatePairStrings.setLayoutData(fd_textOutSurrogatePairStrings);

		lblIvs = new Label(grpStrToUnicode, SWT.NONE);
		lblIvs.setText("IVS文字列");
		FormData fd_lblIvs = new FormData();
		fd_lblIvs.top = new FormAttachment(label_9, 12);
		fd_lblIvs.left = new FormAttachment(textInStr, 0, SWT.LEFT);
		lblIvs.setLayoutData(fd_lblIvs);

		textOutIVSStrings = new Text(grpStrToUnicode, SWT.BORDER);
		textOutIVSStrings.setEditable(false);
		textOutIVSStrings.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutIVSStrings = new FormData();
		fd_textOutIVSStrings.right = new FormAttachment(100, -10);
		fd_textOutIVSStrings.left = new FormAttachment(lblIvs, 80);
		fd_textOutIVSStrings.top = new FormAttachment(textOutSurrogatePairStrings, 6);
		textOutIVSStrings.setLayoutData(fd_textOutIVSStrings);
		grpUnicodeToStr.setLayout(new FormLayout());
		grpUnicodeToStr.setLayoutData(fd_grpUnicodeToStr);
		grpUnicodeToStr.setText("Unicode→文字");

		Group grpCodepointToSurrrogatePair = new Group(shlUnicode, SWT.NONE);
		FormData fd_grpCodepointToSurrrogatePair = new FormData();
		fd_grpCodepointToSurrrogatePair.top = new FormAttachment(grpUnicodeToStr, 6);
		fd_grpCodepointToSurrrogatePair.left = new FormAttachment(grpStrToUnicode, 0, SWT.LEFT);
		fd_grpCodepointToSurrrogatePair.right = new FormAttachment(100, -10);

		rdUTF8 = new Button(grpUnicodeToStr, SWT.RADIO);
		rdUTF8.addSelectionListener(handler.new Selected_rdUTFEncode());
		FormData fd_rdUTF8 = new FormData();
		rdUTF8.setLayoutData(fd_rdUTF8);
		rdUTF8.setText("UTF-8");

		rdUTF16 = new Button(grpUnicodeToStr, SWT.RADIO);
		rdUTF16.addSelectionListener(handler.new Selected_rdUTFEncode());
		rdUTF16.setSelection(true);
		rdUTF16.setText("UTF-16");
		FormData fd_rdUTF16 = new FormData();
		fd_rdUTF16.left = new FormAttachment(rdUTF8, 16);
		fd_rdUTF16.top = new FormAttachment(rdUTF8, 0, SWT.TOP);
		rdUTF16.setLayoutData(fd_rdUTF16);

		rdUTF32 = new Button(grpUnicodeToStr, SWT.RADIO);
		rdUTF32.addSelectionListener(handler.new Selected_rdUTFEncode());
		rdUTF32.setText("UTF-32");
		FormData fd_rdUTF32 = new FormData();
		fd_rdUTF32.left = new FormAttachment(rdUTF16, 16);
		fd_rdUTF32.top = new FormAttachment(rdUTF8, 0, SWT.TOP);
		rdUTF32.setLayoutData(fd_rdUTF32);

		textInUnicode = new Text(grpUnicodeToStr, SWT.BORDER);
		textInUnicode.addModifyListener(handler.new ModifyText_textInUnicode());
		FormData fd_textInUnicode = new FormData();
		fd_textInUnicode.top = new FormAttachment(0, 10);
		fd_textInUnicode.left = new FormAttachment(0, 10);
		fd_textInUnicode.right = new FormAttachment(100, -10);
		textInUnicode.setLayoutData(fd_textInUnicode);

		label_2 = new Label(grpUnicodeToStr, SWT.NONE);
		fd_rdUTF8.left = new FormAttachment(label_2, 36);
		fd_rdUTF8.top = new FormAttachment(label_2, 0, SWT.TOP);
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(textInUnicode, 9);
		fd_label_2.left = new FormAttachment(textInUnicode, 0, SWT.LEFT);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("エンコード");

		textOutStr = new Text(grpUnicodeToStr, SWT.BORDER);
		textOutStr.setEditable(false);
		textOutStr.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutStr = new FormData();
		fd_textOutStr.top = new FormAttachment(label_2, 10);
		fd_textOutStr.right = new FormAttachment(textInUnicode, 0, SWT.RIGHT);
		fd_textOutStr.left = new FormAttachment(0, 10);
		textOutStr.setLayoutData(fd_textOutStr);

		lblBom = new Label(grpUnicodeToStr, SWT.NONE);
		lblBom.setText("BOM");
		FormData fd_lblBom = new FormData();
		fd_lblBom.bottom = new FormAttachment(rdUTF8, 0, SWT.BOTTOM);
		fd_lblBom.left = new FormAttachment(rdUTF32, 59);
		lblBom.setLayoutData(fd_lblBom);

		textOutBom = new Text(grpUnicodeToStr, SWT.BORDER);
		textOutBom.setEditable(false);
		textOutBom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutBom = new FormData();
		fd_textOutBom.top = new FormAttachment(rdUTF8, -3, SWT.TOP);
		fd_textOutBom.left = new FormAttachment(lblBom, 17);
		fd_textOutBom.width = 100;
		textOutBom.setLayoutData(fd_textOutBom);
		grpUnicodeToStr.setTabList(new Control[]{textInUnicode, rdUTF8, rdUTF16, rdUTF32, textOutStr});
		fd_grpCodepointToSurrrogatePair.height = 40;
		grpCodepointToSurrrogatePair.setLayoutData(fd_grpCodepointToSurrrogatePair);
		grpCodepointToSurrrogatePair.setText("Unicodeコードポイント→サロゲートペア");

		Group grpSurrogatePairToCodepoint = new Group(shlUnicode, SWT.NONE);
		FormData fd_grpSurrogatePairToCodepoint = new FormData();
		fd_grpSurrogatePairToCodepoint.top = new FormAttachment(grpCodepointToSurrrogatePair, 6);
		fd_grpSurrogatePairToCodepoint.right = new FormAttachment(grpStrToUnicode, 0, SWT.RIGHT);
		fd_grpSurrogatePairToCodepoint.left = new FormAttachment(0, 10);
		fd_grpSurrogatePairToCodepoint.height = 40;

		cmpInfos = new Composite(grpStrToUnicode, SWT.NONE);
		FormData fd_cmpInfos = new FormData();
		fd_cmpInfos.width = 650;
		fd_cmpInfos.top = new FormAttachment(textOutUTF32, 6);
		fd_cmpInfos.left = new FormAttachment(textInStr, 0, SWT.LEFT);
		cmpInfos.setLayoutData(fd_cmpInfos);
		cmpInfos.setLayout(null);

		Label lblStringlength = new Label(cmpInfos, SWT.NONE);
		lblStringlength.setBounds(5, 8, 75, 18);
		lblStringlength.setText("String.length");

		textOutStrLength = new Text(cmpInfos, SWT.BORDER | SWT.RIGHT);
		textOutStrLength.setEditable(false);
		textOutStrLength.setBounds(123, 5, 36, 24);
		textOutStrLength.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		lblStringcodepointcount = new Label(cmpInfos, SWT.NONE);
		lblStringcodepointcount.setBounds(194, 8, 129, 18);
		lblStringcodepointcount.setText("String.codePointCount");

		textOutCodepointCount = new Text(cmpInfos, SWT.BORDER | SWT.RIGHT);
		textOutCodepointCount.setEditable(false);
		textOutCodepointCount.setBounds(367, 5, 36, 24);
		textOutCodepointCount.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		lblIvsCount = new Label(cmpInfos, SWT.NONE);
		lblIvsCount.setBounds(433, 8, 117, 18);
		lblIvsCount.setText("IVSを考慮した文字数");

		textOutIVSCount = new Text(cmpInfos, SWT.BORDER | SWT.RIGHT);
		textOutIVSCount.setEditable(false);
		textOutIVSCount.setBounds(587, 5, 36, 24);
		textOutIVSCount.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		Label label_7 = new Label(cmpInfos, SWT.NONE);
		label_7.setBounds(6, 38, 132, 18);
		label_7.setText("モンゴル自由字形選択子");

		textOutMongolianIVSCount = new Text(cmpInfos, SWT.BORDER | SWT.RIGHT);
		textOutMongolianIVSCount.setEditable(false);
		textOutMongolianIVSCount.setBounds(187, 35, 36, 24);
		textOutMongolianIVSCount.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		Label lblNewLabel = new Label(cmpInfos, SWT.NONE);
		lblNewLabel.setBounds(265, 38, 72, 18);
		lblNewLabel.setText("漢字用選択子");

		textOutJapaneseIVSCount = new Text(cmpInfos, SWT.BORDER | SWT.RIGHT);
		textOutJapaneseIVSCount.setEditable(false);
		textOutJapaneseIVSCount.setBounds(377, 35, 36, 24);
		textOutJapaneseIVSCount.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		Label label_8 = new Label(cmpInfos, SWT.NONE);
		label_8.setBounds(454, 38, 72, 18);
		label_8.setText("その他選択子");

		textOutOtherIVSCount = new Text(cmpInfos, SWT.BORDER | SWT.RIGHT);
		textOutOtherIVSCount.setEditable(false);
		textOutOtherIVSCount.setBounds(548, 35, 36, 24);
		textOutOtherIVSCount.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		textInCodepoint = new Text(grpCodepointToSurrrogatePair, SWT.BORDER);
		textInCodepoint.setTextLimit(6);
		textInCodepoint.addModifyListener(handler. new ModifyText_textInCodepoint());
		textInCodepoint.setBounds(110, 25, 72, 24);

		Label label = new Label(grpCodepointToSurrrogatePair, SWT.NONE);
		label.setBounds(210, 28, 84, 18);
		label.setText("上位サロゲート");

		Label label_1 = new Label(grpCodepointToSurrrogatePair, SWT.NONE);
		label_1.setText("下位サロゲート");
		label_1.setBounds(410, 28, 84, 18);

		textOutHighSurrogate = new Text(grpCodepointToSurrrogatePair, SWT.BORDER);
		textOutHighSurrogate.setEditable(false);
		textOutHighSurrogate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutHighSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textOutHighSurrogate.setBounds(310, 25, 60, 24);

		textOutLowSurrogate = new Text(grpCodepointToSurrrogatePair, SWT.BORDER);
		textOutLowSurrogate.setEditable(false);
		textOutLowSurrogate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutLowSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textOutLowSurrogate.setBounds(510, 25, 60, 24);

		label_6 = new Label(grpCodepointToSurrrogatePair, SWT.NONE);
		label_6.setText("コードポイント");
		label_6.setBounds(10, 28, 84, 18);
		grpSurrogatePairToCodepoint.setLayout(null);
		grpSurrogatePairToCodepoint.setLayoutData(fd_grpSurrogatePairToCodepoint);
		grpSurrogatePairToCodepoint.setText("サロゲートペア→Unicodeコードポイント");

		label_3 = new Label(grpSurrogatePairToCodepoint, SWT.NONE);
		label_3.setBounds(10, 28, 84, 18);
		label_3.setText("上位サロゲート");

		textInHighSurrogate = new Text(grpSurrogatePairToCodepoint, SWT.BORDER);
		textInHighSurrogate.setTextLimit(4);
		textInHighSurrogate.setBounds(120, 25, 60, 24);
		textInHighSurrogate.addModifyListener(handler. new ModifyText_TextInSurrogatePair());
		textInHighSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textInHighSurrogate.setBackground(SWTResourceManager.getColor(255, 255, 255));

		label_4 = new Label(grpSurrogatePairToCodepoint, SWT.NONE);
		label_4.setBounds(210, 28, 84, 18);
		label_4.setText("下位サロゲート");

		textInLowSurrogate = new Text(grpSurrogatePairToCodepoint, SWT.BORDER);
		textInLowSurrogate.setTextLimit(4);
		textInLowSurrogate.setBounds(320, 25, 72, 24);
		textInLowSurrogate.addModifyListener(handler. new ModifyText_TextInSurrogatePair());
		textInLowSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textInLowSurrogate.setBackground(SWTResourceManager.getColor(255, 255, 255));

		textOutCodepoint = new Text(grpSurrogatePairToCodepoint, SWT.BORDER);
		textOutCodepoint.setBounds(528, 25, 72, 24);
		textOutCodepoint.setEditable(false);
		textOutCodepoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		label_5 = new Label(grpSurrogatePairToCodepoint, SWT.NONE);
		label_5.setBounds(420, 28, 84, 18);
		label_5.setText("コードポイント");
		m_bindingContext = initDataBindings();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeLocationShlUnicodeObserveWidget = WidgetProperties.location().observe(shlUnicode);
		IObservableValue unicodeFormLocationSettingsObserveValue = PojoProperties.value("unicodeFormLocation").observe(settings);
		bindingContext.bindValue(observeLocationShlUnicodeObserveWidget, unicodeFormLocationSettingsObserveValue, null, null);
		//
		return bindingContext;
	}

	protected Text getTextInUnicode() {
		return textInUnicode;
	}
	protected Button getRdUTF8() {
		return rdUTF8;
	}
	protected Button getRdUTF16() {
		return rdUTF16;
	}
	protected Button getRdUTF32() {
		return rdUTF32;
	}
	protected Text getTextOutStr() {
		return textOutStr;
	}
	protected Text getTextOutStrLength() {
		return textOutStrLength;
	}
	protected Text getTextOutCodepointCount() {
		return textOutCodepointCount;
	}
	protected Text getTextOutIVSCount() {
		return textOutIVSCount;
	}
	protected Text getTextInStr() {
		return textInStr;
	}
	protected Text getTextOutUTF8() {
		return textOutUTF8;
	}
	protected Text getTextOutUTF16() {
		return textOutUTF16;
	}
	protected Text getTextOutUTF32() {
		return textOutUTF32;
	}
	public Text getTextOutIVSStrings() {
		return textOutIVSStrings;
	}
	public Text getTextOutSurrogatePairStrings() {
		return textOutSurrogatePairStrings;
	}
	protected Text getTextOutMongolianIVSCount() {
		return textOutMongolianIVSCount;
	}
	protected Text getTextOutJapaneseIVSCount() {
		return textOutJapaneseIVSCount;
	}
	protected Text getTextOutOtherIVSCount() {
		return textOutOtherIVSCount;
	}
	protected Text getTextInCodepoint() {
		return textInCodepoint;
	}
	protected Text getTextOutHighSurrogate() {
		return textOutHighSurrogate;
	}
	protected Text getTextOutLowSurrogate() {
		return textOutLowSurrogate;
	}
	protected Text getTextInHighSurrogate() {
		return textInHighSurrogate;
	}
	protected Text getTextInLowSurrogate() {
		return textInLowSurrogate;
	}
	protected Text getTextOutCodepoint() {
		return textOutCodepoint;
	}
	protected Text getTextOutBom() {
		return textOutBom;
	}

	public UnicodeFormSettings getSettings() {
		return settings;
	}

	public void setSettings(UnicodeFormSettings settings) {
		this.settings = settings;
	}
}
