package moreredoc.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MoreRedocGuiController {

	@FXML
	private Font x1;

	@FXML
	private Color x2;

	@FXML
	private CheckBox option1;

	@FXML
	private CheckBox option2;

	@FXML
	private CheckBox option3;

	@FXML
	private Label pathCsv1;

	@FXML
	private Label pathCsv2;

	@FXML
	private Label pathOutput;

	@FXML
	private CheckBox cbXMI;

	@FXML
	private CheckBox cbArgo;

	@FXML
	private CheckBox cbStar;

	@FXML
	private CheckBox cvPng;

	@FXML
	void reset123(ActionEvent event) {
		System.out.println("reset");
		cbXMI.setSelected(false);
	}

	@FXML
	void runGenerateModel(ActionEvent event) {
		System.out.println("generate");

	}

}
