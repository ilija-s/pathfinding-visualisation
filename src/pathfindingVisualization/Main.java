package pathfindingVisualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();

        Scene scene = new Scene(mainWindow, 900, 940);
        scene.getStylesheets().add("/pathfindingVisualization/style.css");
        primaryStage.setTitle("Pathfinding Visualisator");
        primaryStage.setScene(scene);
        primaryStage.show();
        mainWindow.getCanvas().requestFocus();

        mainWindow.draw();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
