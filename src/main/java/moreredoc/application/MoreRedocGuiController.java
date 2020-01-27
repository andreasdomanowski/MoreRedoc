package moreredoc.application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MoreRedocGuiController {
	
	private String resultCsvKeywordsPath = null;
	private String resultCsvTextPath = null;
	private String resultCsvOutputPath = null;


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
    private CheckBox cbXMI;

    @FXML
    private CheckBox cbArgo;

    @FXML
    private CheckBox cbStar;

    @FXML
    private CheckBox cvPng;

    @FXML
    private TextField pathCsvKeywords;

    @FXML
    private TextField pathCsvText;

    @FXML
    private TextField pathOutputFolder;
    
    @FXML
    private Button buttonRun;

    @FXML
    private Button buttonSelectCsvKeywords;

    @FXML
    private Button buttonSelectCsvText;

    @FXML
    private Button buttonSelectOutputFolder;

    @FXML
    void reset(ActionEvent event) {

    }

    @FXML
    void runGenerateModel(ActionEvent event) throws Exception {
    	if(resultCsvKeywordsPath != null && resultCsvOutputPath != null && resultCsvTextPath != null) {
			Stage dialog = new Stage();

			dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
			dialog.initModality(Modality.APPLICATION_MODAL); 
			dialog.showAndWait();
    		new Thread() {
    			public void run() {
    				try {

    					
    					buttonRun.setDisable(true);
						MoreRedocLogicStarter.runMoreRedocLogic(resultCsvKeywordsPath, resultCsvTextPath, resultCsvOutputPath);
						buttonRun.setDisable(false);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}.start();
    	}
    }

    @FXML
    void selectCsvKeywords(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	String path = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow()).getAbsolutePath();
    	
    	pathCsvKeywords.setText(path);
    	resultCsvKeywordsPath = path;
    }

    @FXML
    void selectCsvText(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	String path = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow()).getAbsolutePath();
    	
    	pathCsvText.setText(path);
    	resultCsvTextPath = path;
    }

    @FXML
    void selectOutputFolder(ActionEvent event) {
    	DirectoryChooser dirChooser = new DirectoryChooser();
    	String path = dirChooser.showDialog(((Node) event.getSource()).getScene().getWindow()).getAbsolutePath();
    	
    	pathOutputFolder.setText(path);
    	resultCsvOutputPath = path;
    }

}
