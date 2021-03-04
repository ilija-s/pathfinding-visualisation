package pathfinderVisualisatior;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainWindow mainWindow = new MainWindow();

        primaryStage.setTitle("Pathfinder Visualisator");
        primaryStage.setScene(new Scene(mainWindow, 600, 640));
        primaryStage.show();
        mainWindow.getCanvas().requestFocus();

        mainWindow.draw();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
