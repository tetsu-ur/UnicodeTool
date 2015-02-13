package form;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.graphics.Point;

public class MainFormSettings {

	/**
	 * コンストラクタ
	 */
	public MainFormSettings() {
		this.load();
	}
	
	private Point mainFormLocation;
	
	/**
	 * @return the mainFormLocation
	 */
	public Point getMainFormLocation() {
		return mainFormLocation;
	}

	/**
	 * @param mainFormLocation the mainFormLocation to set
	 */
	public void setMainFormLocation(Point mainFormLocation) {
		this.mainFormLocation = mainFormLocation;
	}
	
	/**
	 * オブジェクトの値をプロパティファイルに保存
	 */
	public void save() {
		try {
			Properties prop = new Properties();
			
			prop.setProperty("MainFormLocation", 
					String.valueOf(this.getMainFormLocation().x) + ", " +
					String.valueOf(this.getMainFormLocation().y));
			
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
			
			String string = prop.getProperty("MainFormLocation", "0,0");
            this.setMainFormLocation(cnvPointFromString(string));
			
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
