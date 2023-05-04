package fxBiisit;
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


/**
 * Lisätään alkusivulle tapahtumat
 * @author admin
 * @version 17 Mar 2022
 *
 */
public class AlkusivuController implements Initializable {

    @FXML
    void handleAvaaPaasivu() {
        
        
        ModalController.showModal (AlkusivuController.class.getResource("Paasivu.fxml"), "Biisit", null, "");   
        
        }
    
    @FXML
    void handleSuljeAlkusivu() {
        
        Dialogs.showMessageDialog("Ei osata sulkea vielä");
    }
    
    
    /**
     * @param url ei käytössä
     * @param bundle ei käytössä
     */
    public void intialize(URL url, ResourceBundle bundle) {
        //
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub     
    }
}
