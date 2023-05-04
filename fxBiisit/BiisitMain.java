package fxBiisit;

import biisit.Biisit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author admin
 * @version 2.2.2022
 *
 */
public class BiisitMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("Paasivu.fxml"));
            final Pane root = ldr.load();
            final PaasivuController biisitCtrl = (PaasivuController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("biisit.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Biisit");
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !biisitCtrl.voikoSulkea()) event.consume();
            });
            
            Biisit biisit = new Biisit();
            biisitCtrl.setBiisit(biisit);
            Application.Parameters params = getParameters(); 
            params.getRaw().size();
            
            biisitCtrl.lueTiedosto("nimet");
            
            
            primaryStage.show();
   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}