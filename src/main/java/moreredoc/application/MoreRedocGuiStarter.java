package moreredoc.application;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MoreRedocGuiStarter extends Application {

	private Stage primaryStage;
	private VBox rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MoreRedoc");

		initRootLayout();

	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));

			MoreRedocGuiController controller = new MoreRedocGuiController();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MoreRedocGuiStarter.class.getResource("MoreRedocMainView.fxml"));
			loader.setController(controller);
			rootLayout = (VBox) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
