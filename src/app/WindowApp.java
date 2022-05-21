package app;

import mvc.controller.WindowController;
import mvc.view.WindowView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WindowApp extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
        //Create View
		WindowView view = new WindowView();

        //Create Controller
		WindowController controller = new WindowController(view);

        //Show stage
        primaryStage.setTitle("SAE 201");
        primaryStage.setScene(new Scene(view.getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
