package form;

import java.io.UnsupportedEncodingException;

import logic.UnicodeUtil;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class UnicodeForm {
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

	private UnicodeFormSettings settings = new UnicodeFormSettings();
	private Button rdUTF8;
	private Button rdUTF16;
	private Button rdUTF32;

	private UnicodeFormHandler handler = new UnicodeFormHandler(this);

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
		shlUnicode = new Shell();
		shlUnicode.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				settings.save();
			}
		});
		shlUnicode.setSize(646, 478);
		shlUnicode.setText("Unicodeツール");
		shlUnicode.setLayout(new FormLayout());

		Group grpStrToUnicode = new Group(shlUnicode, SWT.NONE);
		grpStrToUnicode.setLayout(new FormLayout());
		FormData fd_grpStrToUnicode = new FormData();
		fd_grpStrToUnicode.left = new FormAttachment(0, 10);
		fd_grpStrToUnicode.right = new FormAttachment(100, -10);
		fd_grpStrToUnicode.bottom = new FormAttachment(0, 161);
		fd_grpStrToUnicode.top = new FormAttachment(0, 10);
		grpStrToUnicode.setLayoutData(fd_grpStrToUnicode);
		grpStrToUnicode.setText("文字→Unicode");

		textInStr = new Text(grpStrToUnicode, SWT.BORDER);
		textInStr.setData(textInStr);
		textInStr.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try {
					textOutUTF8.setText(
							UnicodeUtil.decodeUTF8(textInStr.getText()));
					textOutUTF16.setText(
							UnicodeUtil.decodeUTF16(textInStr.getText()));
					textOutUTF32.setText(
							UnicodeUtil.decodeUTF32(textInStr.getText()));
				} catch (UnsupportedEncodingException ex) {
					ex.printStackTrace();
				}
			}
		});
		FormData fd_textInStr = new FormData();
		fd_textInStr.right = new FormAttachment(100, -10);
		fd_textInStr.top = new FormAttachment(0, 7);
		fd_textInStr.left = new FormAttachment(0, 7);
		textInStr.setLayoutData(fd_textInStr);

		grpUnicodeToStr = new Group(shlUnicode, SWT.NONE);
		FormData fd_grpUnicodeToStr = new FormData();
		fd_grpUnicodeToStr.top = new FormAttachment(grpStrToUnicode, 6);
		fd_grpUnicodeToStr.left = new FormAttachment(grpStrToUnicode, 0, SWT.LEFT);
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
		grpUnicodeToStr.setLayout(new FormLayout());
		fd_grpUnicodeToStr.right = new FormAttachment(100, -10);
		grpUnicodeToStr.setLayoutData(fd_grpUnicodeToStr);
		grpUnicodeToStr.setText("Unicode→文字");

		Group grpCodepointToSurrrogatePair = new Group(shlUnicode, SWT.NONE);
		FormData fd_grpCodepointToSurrrogatePair = new FormData();
		fd_grpCodepointToSurrrogatePair.top = new FormAttachment(grpUnicodeToStr, 6);
		fd_grpCodepointToSurrrogatePair.right = new FormAttachment(grpStrToUnicode, 0, SWT.RIGHT);

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
		textOutStr.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutStr = new FormData();
		fd_textOutStr.right = new FormAttachment(textInUnicode, 0, SWT.RIGHT);
		fd_textOutStr.top = new FormAttachment(label_2, 6);
		fd_textOutStr.left = new FormAttachment(0, 10);
		textOutStr.setLayoutData(fd_textOutStr);
		grpUnicodeToStr.setTabList(new Control[]{textInUnicode, rdUTF8, rdUTF16, rdUTF32, textOutStr});
		fd_grpCodepointToSurrrogatePair.height = 40;
		fd_grpCodepointToSurrrogatePair.left = new FormAttachment(0, 10);
		grpCodepointToSurrrogatePair.setLayoutData(fd_grpCodepointToSurrrogatePair);
		grpCodepointToSurrrogatePair.setText("Unicodeコードポイント→サロゲートペア");

		Group grpSurrogatePairToCodepoint = new Group(shlUnicode, SWT.NONE);
		FormData fd_grpSurrogatePairToCodepoint = new FormData();
		fd_grpSurrogatePairToCodepoint.top = new FormAttachment(grpCodepointToSurrrogatePair, 6);
		fd_grpSurrogatePairToCodepoint.right = new FormAttachment(grpStrToUnicode, 0, SWT.RIGHT);
		fd_grpSurrogatePairToCodepoint.bottom = new FormAttachment(100, -10);
		fd_grpSurrogatePairToCodepoint.left = new FormAttachment(0, 10);

		textInCodepoint = new Text(grpCodepointToSurrrogatePair, SWT.BORDER);
		textInCodepoint.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String[] surrogatePair =
						UnicodeUtil.convCodepoint2SurrogateParir(textInCodepoint.getText());
				textOutHighSurrogate.setText(surrogatePair[0]);
				textOutLowSurrogate.setText(surrogatePair[1]);
			}
		});
		textInCodepoint.setBounds(102, 25, 72, 24);

		Label label = new Label(grpCodepointToSurrrogatePair, SWT.NONE);
		label.setBounds(206, 28, 84, 18);
		label.setText("上位サロゲート");

		Label label_1 = new Label(grpCodepointToSurrrogatePair, SWT.NONE);
		label_1.setText("下位サロゲート");
		label_1.setBounds(396, 28, 84, 18);

		textOutHighSurrogate = new Text(grpCodepointToSurrrogatePair, SWT.BORDER);
		textOutHighSurrogate.setEditable(false);
		textOutHighSurrogate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutHighSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textOutHighSurrogate.setBounds(296, 25, 72, 24);

		textOutLowSurrogate = new Text(grpCodepointToSurrrogatePair, SWT.BORDER);
		textOutLowSurrogate.setEditable(false);
		textOutLowSurrogate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutLowSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textOutLowSurrogate.setBounds(486, 25, 72, 24);

		label_6 = new Label(grpCodepointToSurrrogatePair, SWT.NONE);
		label_6.setText("コードポイント");
		label_6.setBounds(10, 28, 84, 18);
		grpSurrogatePairToCodepoint.setLayout(new FormLayout());
		grpSurrogatePairToCodepoint.setLayoutData(fd_grpSurrogatePairToCodepoint);
		grpSurrogatePairToCodepoint.setText("サロゲートペア→Unicodeコードポイント");

		label_3 = new Label(grpSurrogatePairToCodepoint, SWT.NONE);
		FormData fd_label_3 = new FormData();
		fd_label_3.left = new FormAttachment(0, 10);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("上位サロゲート");

		textInHighSurrogate = new Text(grpSurrogatePairToCodepoint, SWT.BORDER);
		textInHighSurrogate.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				textOutCodepoint.setText(UnicodeUtil.convSurrogatePair2Codepoint(
						textInHighSurrogate.getText(), textInLowSurrogate.getText()));
			}
		});
		FormData fd_textInHighSurrogate = new FormData();
		fd_textInHighSurrogate.top = new FormAttachment(label_3, -3, SWT.TOP);
		fd_textInHighSurrogate.left = new FormAttachment(label_3, 6);
		textInHighSurrogate.setLayoutData(fd_textInHighSurrogate);
		textInHighSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textInHighSurrogate.setBackground(SWTResourceManager.getColor(255, 255, 255));

		label_4 = new Label(grpSurrogatePairToCodepoint, SWT.NONE);
		fd_label_3.top = new FormAttachment(label_4, 0, SWT.TOP);
		FormData fd_label_4 = new FormData();
		fd_label_4.top = new FormAttachment(0, 15);
		fd_label_4.left = new FormAttachment(0, 202);
		label_4.setLayoutData(fd_label_4);
		label_4.setText("下位サロゲート");

		textInLowSurrogate = new Text(grpSurrogatePairToCodepoint, SWT.BORDER);
		textInLowSurrogate.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				textOutCodepoint.setText(UnicodeUtil.convSurrogatePair2Codepoint(
						textInHighSurrogate.getText(), textInLowSurrogate.getText()));
			}
		});
		FormData fd_textInLowSurrogate = new FormData();
		fd_textInLowSurrogate.top = new FormAttachment(0, 12);
		fd_textInLowSurrogate.left = new FormAttachment(0, 292);
		textInLowSurrogate.setLayoutData(fd_textInLowSurrogate);
		textInLowSurrogate.setForeground(SWTResourceManager.getColor(0, 0, 0));
		textInLowSurrogate.setBackground(SWTResourceManager.getColor(255, 255, 255));

		textOutCodepoint = new Text(grpSurrogatePairToCodepoint, SWT.BORDER);
		textOutCodepoint.setEditable(false);
		textOutCodepoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_textOutCodepoint = new FormData();
		fd_textOutCodepoint.top = new FormAttachment(label_3, -3, SWT.TOP);
		textOutCodepoint.setLayoutData(fd_textOutCodepoint);

		label_5 = new Label(grpSurrogatePairToCodepoint, SWT.NONE);
		fd_textOutCodepoint.left = new FormAttachment(label_5, 12);
		label_5.setText("コードポイント");
		FormData fd_label_5 = new FormData();
		fd_label_5.top = new FormAttachment(label_3, 0, SWT.TOP);
		fd_label_5.left = new FormAttachment(textInLowSurrogate, 26);
		label_5.setLayoutData(fd_label_5);
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
}
