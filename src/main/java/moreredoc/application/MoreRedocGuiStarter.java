package moreredoc.application;

import javax.swing.*;

public class MoreRedocGuiStarter {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MoreRedocGui gui = new MoreRedocGui();
		gui.setVisible(true);
		gui.setResizable(false);
	}

}
