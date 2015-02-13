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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindow {
	private DataBindingContext m_bindingContext;

	protected Shell shlUnicode;
	private Text textInputString;
	private Text textOutputUTF16Code;
	private Group grpUTF162Str;
	private Text textInputUTF16Code;
	private Text textOutputString;
	private Group grpUnicode2SurrogatePair;
	private Text textInputCodepoint;
	private Text textOutputHighSurrogate;
	private Text textOutputLowSurrogate;
	private Label label_1;
	private Group grpuincode;
	private Label label_2;
	private Text textInputLowSurrogate;
	private Label label_3;
	private Text textInputHignSurrogate;
	private Text textOutputCodepoint;

	private MainFormSettings settings = new MainFormSettings();
	private Text textOutputUTF8Code;
	private Label lblUtf_1;
	private Text text;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
		shlUnicode.setSize(new Point(600, 379));
		shlUnicode.setImeInputMode(SWT.ALPHA);
		shlUnicode.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				settings.save();
			}
		});
		shlUnicode.setMinimumSize(new Point(600, 275));
		shlUnicode.setSize(600, 392);
		shlUnicode.setText("Unicodeツール");
		shlUnicode.setLayout(new FormLayout());
		
		//settings.setMainFormLocation(new Point(100, 100));
		
		Group grpStr2UTF16 = new Group(shlUnicode, SWT.NONE);
		grpStr2UTF16.setLayout(new FormLayout());
		FormData fd_grpStr2UTF16 = new FormData();
		fd_grpStr2UTF16.top = new FormAttachment(0, 10);
		fd_grpStr2UTF16.right = new FormAttachment(100, -10);
		fd_grpStr2UTF16.left = new FormAttachment(0, 9);
		grpStr2UTF16.setLayoutData(fd_grpStr2UTF16);
		grpStr2UTF16.setText("文字→UTF16");
		
		textInputString = new Text(grpStr2UTF16, SWT.BORDER);
		FormData fd_textInputString = new FormData();
		fd_textInputString.right = new FormAttachment(0, 114);
		fd_textInputString.top = new FormAttachment(0, 10);
		fd_textInputString.left = new FormAttachment(0, 10);
		textInputString.setLayoutData(fd_textInputString);
		textInputString.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textInputString.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					textOutputUTF16Code.setText(
							UnicodeUtil.decodeUTF16(textInputString.getText()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		textOutputUTF16Code = new Text(grpStr2UTF16, SWT.BORDER);
		FormData fd_textOutputUTF16Code = new FormData();
		fd_textOutputUTF16Code.top = new FormAttachment(0, 41);
		fd_textOutputUTF16Code.right = new FormAttachment(100, -16);
		fd_textOutputUTF16Code.left = new FormAttachment(0, 186);
		textOutputUTF16Code.setLayoutData(fd_textOutputUTF16Code);
		textOutputUTF16Code.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutputUTF16Code.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textOutputUTF16Code.setEditable(false);
		
		grpUTF162Str = new Group(shlUnicode, SWT.NONE);
		fd_grpStr2UTF16.bottom = new FormAttachment(grpUTF162Str, -6);
		
		textOutputUTF8Code = new Text(grpStr2UTF16, SWT.BORDER);
		FormData fd_textOutputUTF8Code = new FormData();
		fd_textOutputUTF8Code.left = new FormAttachment(0, 186);
		fd_textOutputUTF8Code.right = new FormAttachment(100, -16);
		fd_textOutputUTF8Code.bottom = new FormAttachment(textInputString, 0, SWT.BOTTOM);
		textOutputUTF8Code.setLayoutData(fd_textOutputUTF8Code);
		textOutputUTF8Code.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textOutputUTF8Code.setEditable(false);
		textOutputUTF8Code.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		Label lblUtf = new Label(grpStr2UTF16, SWT.NONE);
		FormData fd_lblUtf = new FormData();
		fd_lblUtf.top = new FormAttachment(0, 44);
		fd_lblUtf.left = new FormAttachment(0, 126);
		lblUtf.setLayoutData(fd_lblUtf);
		lblUtf.setText("UTF16");
		
		lblUtf_1 = new Label(grpStr2UTF16, SWT.NONE);
		lblUtf_1.setText("UTF8");
		FormData fd_lblUtf_1 = new FormData();
		fd_lblUtf_1.left = new FormAttachment(0, 126);
		fd_lblUtf_1.bottom = new FormAttachment(lblUtf, -12);
		lblUtf_1.setLayoutData(fd_lblUtf_1);
		grpUTF162Str.setText("UTF16→文字");
		grpUTF162Str.setLayout(new FormLayout());
		FormData fd_grpUTF162Str = new FormData();
		fd_grpUTF162Str.top = new FormAttachment(0, 141);
		fd_grpUTF162Str.right = new FormAttachment(100, -10);
		fd_grpUTF162Str.left = new FormAttachment(0, 9);
		grpUTF162Str.setLayoutData(fd_grpUTF162Str);
		
		textInputUTF16Code = new Text(grpUTF162Str, SWT.BORDER);
		textInputUTF16Code.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textInputUTF16Code.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
                textOutputString.setText(
                    UnicodeUtil.encodeUTF16(textInputUTF16Code.getText()));
			}
		});
		FormData fd_textInputUTF16Code = new FormData();
		fd_textInputUTF16Code.right = new FormAttachment(0, 114);
		fd_textInputUTF16Code.top = new FormAttachment(0, 10);
		fd_textInputUTF16Code.left = new FormAttachment(0, 10);
		textInputUTF16Code.setLayoutData(fd_textInputUTF16Code);
		
		textOutputString = new Text(grpUTF162Str, SWT.BORDER);
		textOutputString.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutputString.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textOutputString.setEditable(false);
		FormData fd_textOutputString = new FormData();
		fd_textOutputString.left = new FormAttachment(textInputUTF16Code, 6);
		fd_textOutputString.right = new FormAttachment(100, -10);
		fd_textOutputString.top = new FormAttachment(textInputUTF16Code, 0, SWT.TOP);
		textOutputString.setLayoutData(fd_textOutputString);
		grpUTF162Str.setTabList(new Control[]{textInputUTF16Code, textOutputString});
		
		grpUnicode2SurrogatePair = new Group(shlUnicode, SWT.NONE);
		fd_grpUTF162Str.bottom = new FormAttachment(grpUnicode2SurrogatePair, -6);
		
		Label lblUtf_2 = new Label(grpStr2UTF16, SWT.NONE);
		FormData fd_lblUtf_2 = new FormData();
		fd_lblUtf_2.top = new FormAttachment(0, 74);
		fd_lblUtf_2.left = new FormAttachment(lblUtf, 0, SWT.LEFT);
		lblUtf_2.setLayoutData(fd_lblUtf_2);
		lblUtf_2.setText("UTF-32");
		
		text = new Text(grpStr2UTF16, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		text.setEditable(false);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(textOutputUTF16Code, 0, SWT.RIGHT);
		fd_text.top = new FormAttachment(textOutputUTF16Code, 6);
		fd_text.left = new FormAttachment(textOutputUTF16Code, 0, SWT.LEFT);
		text.setLayoutData(fd_text);
		grpUnicode2SurrogatePair.setText("Unicodeコードポイント→サロゲートペア");
		grpUnicode2SurrogatePair.setLayout(new FormLayout());
		FormData fd_grpUnicode2SurrogatePair = new FormData();
		fd_grpUnicode2SurrogatePair.top = new FormAttachment(0, 213);
		fd_grpUnicode2SurrogatePair.right = new FormAttachment(grpStr2UTF16, 0, SWT.RIGHT);
		fd_grpUnicode2SurrogatePair.left = new FormAttachment(0, 9);
		grpUnicode2SurrogatePair.setLayoutData(fd_grpUnicode2SurrogatePair);
		
		textInputCodepoint = new Text(grpUnicode2SurrogatePair, SWT.BORDER);
		textInputCodepoint.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textInputCodepoint.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				String[] surrogatePair = 
						UnicodeUtil.convCodepoint2SurrogateParir(textInputCodepoint.getText());
				textOutputHighSurrogate.setText(surrogatePair[0]);
				textOutputLowSurrogate.setText(surrogatePair[1]);
			}
		});
		FormData fd_textInputCodepoint = new FormData();
		fd_textInputCodepoint.right = new FormAttachment(0, 114);
		fd_textInputCodepoint.top = new FormAttachment(0, 10);
		fd_textInputCodepoint.left = new FormAttachment(0, 10);
		textInputCodepoint.setLayoutData(fd_textInputCodepoint);
		
		textOutputHighSurrogate = new Text(grpUnicode2SurrogatePair, SWT.BORDER);
		textOutputHighSurrogate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutputHighSurrogate.setEditable(false);
		textOutputHighSurrogate.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		FormData fd_textOutputHighSurrogate = new FormData();
		fd_textOutputHighSurrogate.bottom = new FormAttachment(textInputCodepoint, 0, SWT.BOTTOM);
		textOutputHighSurrogate.setLayoutData(fd_textOutputHighSurrogate);
		
		textOutputLowSurrogate = new Text(grpUnicode2SurrogatePair, SWT.BORDER);
		textOutputLowSurrogate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutputLowSurrogate.setEditable(false);
		textOutputLowSurrogate.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		FormData fd_textOutputLowSurrogate = new FormData();
		fd_textOutputLowSurrogate.top = new FormAttachment(textInputCodepoint, 0, SWT.TOP);
		textOutputLowSurrogate.setLayoutData(fd_textOutputLowSurrogate);
		
		Label label = new Label(grpUnicode2SurrogatePair, SWT.NONE);
		fd_textOutputHighSurrogate.left = new FormAttachment(0, 231);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 15);
		fd_label.right = new FormAttachment(textOutputHighSurrogate, -5);
		label.setLayoutData(fd_label);
		label.setText("上位サロゲート");
		
		label_1 = new Label(grpUnicode2SurrogatePair, SWT.NONE);
		fd_textOutputLowSurrogate.left = new FormAttachment(label_1, 6);
		label_1.setText("下位サロゲート");
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(0, 15);
		fd_label_1.left = new FormAttachment(textOutputHighSurrogate, 14);
		label_1.setLayoutData(fd_label_1);
		
		grpuincode = new Group(shlUnicode, SWT.NONE);
		fd_grpUnicode2SurrogatePair.bottom = new FormAttachment(100, -90);
		grpuincode.setText("サロゲートペア→Uincodeコードポイント");
		grpuincode.setLayout(new FormLayout());
		FormData fd_grpuincode = new FormData();
		fd_grpuincode.top = new FormAttachment(grpUnicode2SurrogatePair, 6);
		fd_grpuincode.left = new FormAttachment(grpStr2UTF16, 0, SWT.LEFT);
		fd_grpuincode.right = new FormAttachment(grpStr2UTF16, 0, SWT.RIGHT);
		fd_grpuincode.bottom = new FormAttachment(100, -23);
		grpStr2UTF16.setTabList(new Control[]{textInputString, textOutputUTF16Code});
		grpuincode.setLayoutData(fd_grpuincode);
		
		label_2 = new Label(grpuincode, SWT.NONE);
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(0, 15);
		fd_label_2.left = new FormAttachment(0, 9);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("上位サロゲート");
		
		textInputLowSurrogate = new Text(grpuincode, SWT.BORDER);
		textInputLowSurrogate.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textInputLowSurrogate.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				textOutputCodepoint.setText(UnicodeUtil.convSurrogatePair2Codepoint(
						textInputHignSurrogate.getText(), textInputLowSurrogate.getText()));
			}
		});
		FormData fd_textInputLowSurrogate = new FormData();
		textInputLowSurrogate.setLayoutData(fd_textInputLowSurrogate);
		
		label_3 = new Label(grpuincode, SWT.NONE);
		fd_textInputLowSurrogate.left = new FormAttachment(label_3, 8);
		FormData fd_label_3 = new FormData();
		fd_label_3.top = new FormAttachment(label_2, 0, SWT.TOP);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("下位サロゲート");
		
		textInputHignSurrogate = new Text(grpuincode, SWT.BORDER);
		textInputHignSurrogate.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		textInputHignSurrogate.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				textOutputCodepoint.setText(UnicodeUtil.convSurrogatePair2Codepoint(
						textInputHignSurrogate.getText(), textInputLowSurrogate.getText()));
			}
		});
		fd_textInputLowSurrogate.top = new FormAttachment(textInputHignSurrogate, 0, SWT.TOP);
		fd_label_3.left = new FormAttachment(textInputHignSurrogate, 17);
		FormData fd_textInputHignSurrogate = new FormData();
		fd_textInputHignSurrogate.top = new FormAttachment(0, 8);
		fd_textInputHignSurrogate.left = new FormAttachment(label_2, 7);
		textInputHignSurrogate.setLayoutData(fd_textInputHignSurrogate);
		
		textOutputCodepoint = new Text(grpuincode, SWT.BORDER);
		textOutputCodepoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		textOutputCodepoint.setEditable(false);
		textOutputCodepoint.setFont(SWTResourceManager.getFont("Monospace", 11, SWT.NORMAL));
		FormData fd_textOutputCodepoint = new FormData();
		fd_textOutputCodepoint.right = new FormAttachment(100, -23);
		fd_textOutputCodepoint.top = new FormAttachment(textInputLowSurrogate, 0, SWT.TOP);
		fd_textOutputCodepoint.left = new FormAttachment(textInputLowSurrogate, 21);
		textOutputCodepoint.setLayoutData(fd_textOutputCodepoint);
		grpuincode.setTabList(new Control[]{textInputHignSurrogate, textInputLowSurrogate, textOutputCodepoint});
		m_bindingContext = initDataBindings();

	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeLocationShlUnicodeObserveWidget = WidgetProperties.location().observe(shlUnicode);
		IObservableValue mainFormLocationSettingsObserveValue = PojoProperties.value("mainFormLocation").observe(settings);
		bindingContext.bindValue(observeLocationShlUnicodeObserveWidget, mainFormLocationSettingsObserveValue, null, null);
		//
		return bindingContext;
	}
}
