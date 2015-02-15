package form;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.graphics.Point;

public class UnicodeFormSettings {

	/**
	 * コンストラクタ
	 */
	public UnicodeFormSettings() {
		this.load();
	}

	private Point unicodeFormLocation;

	/**
	 * @return the unicodeFormLocation
	 */
	public Point getUnicodeFormLocation() {
		return unicodeFormLocation;
	}

	/**
	 * @param unicodeFormLocation the unicodeFormLocation to set
	 */
	public void setUnicodeFormLocation(Point unicodeFormLocation) {
		this.unicodeFormLocation = unicodeFormLocation;
	}

	/**
	 * オブジェクトの値をプロパティファイルに保存
	 */
	public void save() {
		try {
			Properties prop = new Properties();

			prop.setProperty("UnicodeFormLocation",
					String.valueOf(this.getUnicodeFormLocation().x) + ", " +
					String.valueOf(this.getUnicodeFormLocation().y));

			prop.store(new FileOutputStream("applicationSetting.properties"), null);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {

		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("applicationSetting.properties"));

			String string = prop.getProperty("UnicodeFormLocation", "0,0");
            this.setUnicodeFormLocation(cnvPointFromString(string));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 「x, y」形式の文字列を Point オブジェクトに変換
	 *
	 * @param coordinate
	 * @return
	 */
	private Point cnvPointFromString(String coordinate) {
		String[] xy = coordinate.split(",");
		int x = Integer.parseInt(xy[0].trim());
		int y = Integer.parseInt(xy[1].trim());
		return new Point(x, y);
	}
}
