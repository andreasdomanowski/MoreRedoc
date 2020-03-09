package moreredoc.application;


import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MoreRedocGuiController {
	
	private static Logger logger = Logger.getLogger(MoreRedocGuiController.class);

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
		Window primaryStage = ((Node) event.getSource()).getScene().getWindow();
		//Stage dialog = new Stage();
//		dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
//		dialog.initModality(Modality.APPLICATION_MODAL); 
//		dialog.showAndWait();
		
//        final Stage dialog = new Stage();
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.initOwner(primaryStage);
//        VBox dialogVbox = new VBox(20);
//        dialogVbox.getChildren().add(new Text("This is a Dialog"));
//        Scene dialogScene = new Scene(dialogVbox, 300, 200);
//        dialog.setScene(dialogScene);
//        dialog.show();
        
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        //veil is only visible when alert window is showing
        veil.visibleProperty().bind(a.showingProperty());

        a.setContentText("The main window should be decorated with a veil.");
        a.setX(primaryStage.getX() + 200); // This is only for showing main window
        a.showAndWait();
    }

    @FXML
    void runGenerateModel(ActionEvent event) throws Exception {
    	if(resultCsvKeywordsPath != null && resultCsvOutputPath != null && resultCsvTextPath != null) {
			
			Window primaryStage = ((Node) event.getSource()).getScene().getWindow();
			//Stage dialog = new Stage();
//			dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
//			dialog.initModality(Modality.APPLICATION_MODAL); 
//			dialog.showAndWait();
			
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("This is a Dialog"));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
			
    		new Thread() {
    			@Override
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
