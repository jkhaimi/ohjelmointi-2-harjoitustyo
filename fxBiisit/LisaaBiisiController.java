package fxBiisit;


import java.net.URL;
import java.util.ResourceBundle;

import biisit.Biisit;
import biisit.Nimi;
import biisit.SailoException;
import biisit.Tieto;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Lisää biisin controller
 * @author admin
 * @version 23 Apr 2022
 *
 */
public class LisaaBiisiController implements ModalControllerInterface<Nimi>, Initializable {

    
    @FXML private TextField nimiArea;
    @FXML private TextField artistiArea;
    @FXML private GridPane pane;
    @FXML private ComboBoxChooser<String> cbGenre;
    @FXML private ComboBoxChooser<String> cbLaatu;
    Nimi nimi;
    Tieto tieto;
    private Biisit biisit;

    

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(Nimi oletus) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Lisää biisin
     */
    @FXML
    public void handleLisaa() {

        uusiNimi();
        ModalController.closeStage(pane);
    }

    private void uusiNimi() {
        Nimi uusi = new Nimi();
        Tieto uusi1 = new Tieto();
        uusi.rekisteroi();
        uusi1.rekisteroi();
        uusi.setNimi(nimiArea.getText());
        uusi1.setNimiNro(uusi.getTunnusNro());
        uusi.setArtisti(artistiArea.getText());
        uusi1.setGenre(cbGenre.getSelectedText());
        uusi1.setLaatu(cbLaatu.getSelectedText());
        try {
            biisit.lisaa(uusi);
            biisit.lisaa(uusi1);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia luomisessa " + e.getMessage());
            return;
        }
    }
    
    
    
    @FXML
    private void handleGenre() {
        //
    }
    @FXML
    private void handleLaatu() {
        //
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }


//===========================================================0
    
    protected void alusta() {
        //
    }

    @Override
    public Nimi getResult() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void setBiisit(Biisit b) {
        this.biisit = b;
    }

    
    public static Nimi avaa(Stage modalityStage, Nimi nimi, Biisit biisit) {
        return ModalController.showModal(LisaaBiisiController.class.getResource("LisaaBiisi.fxml"),
                "Lisaa Biisi", modalityStage, nimi, ctrl -> ((LisaaBiisiController) ctrl).setBiisit(biisit));
        
    }




    
   
    
}