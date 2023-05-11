/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Papa Bair
 */
public class FXMLOutSourcePartSceenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Stage stage;
    Parent scene;
    Part part;
    private int modId;  //This helps keep the ids attached to the object.
    
    @FXML
    private Label outSourceLbl;

    @FXML
    private RadioButton outsurcedPartRadioBtn;

    @FXML
    private TextField outsurcedIdTb;

    @FXML
    private TextField outsurcedNameTb;

    @FXML
    private TextField outsurcedPriceTb;

    @FXML
    private TextField outsurcedInvTb;

    @FXML
    private TextField outsurcedMinTb;

    @FXML
    private TextField outsurcedMaxTb;

    @FXML
    private TextField outsurcedCompanyIdTb;

    @FXML
    void onActionInhousePartRadioBtn(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part values have not been saved.  Do you wish to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent () && result.get() ==  ButtonType.OK)
        {        
            stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/FXMLAddModInHousePartScreen.fxml"));
            stage.setScene(new Scene(scene));  
            stage.show();
        }        
    }

    @FXML
    void onActionOutSourcedCancel(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/FXMLMainScreen.fxml"));
        stage.setScene(new Scene(scene));

    }

    @FXML
    void onActionOutSourcedSave(ActionEvent event) throws IOException {
        
        /**
         * This wraps the save handler in a try block that throws up a dialog box
         * if no information is input into the text fields.
         */
        try{
            
            int id = 1;

            for(Part p: Inventory.getAllParts()){
                if(p.getId() == modId){
                    id = modId;
                    break;
                }else{
                    id++;
                }
            }

            String name = outsurcedNameTb.getText();
            double price = Double.parseDouble(outsurcedPriceTb.getText());
            int stock = Integer.parseInt(outsurcedInvTb.getText());
            int min = Integer.parseInt(outsurcedMinTb.getText());
            int max = Integer.parseInt(outsurcedMaxTb.getText());
            String companyName = outsurcedCompanyIdTb.getText();

            Inventory.addPart(new OutsourcedPart(id, name, price, stock, min, max, companyName));
            Inventory.updatePart(id, part);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/FXMLMainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e)
        {               
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Exception Value: " + e.getMessage() + " values.  Please insert correct values in the textfields." + "\n" + "\n" + "Exception: " + e);
            alert.showAndWait();
        }

    }
    
    public void setPart(OutsourcedPart osPart) {

        /**
         * Brings in a Part object and populates the text field with it.
         */        
        this.part = osPart;
        
        modId = osPart.getId();
        outSourceLbl.setText("Mod Part");
        outsurcedIdTb.setText(Integer.toString(osPart.getId()));
        outsurcedNameTb.setText(osPart.getName());
        outsurcedPriceTb.setText(Double.toString(osPart.getPrice()));
        outsurcedInvTb.setText(Integer.toString(osPart.getStock()));
        outsurcedMinTb.setText(Integer.toString(osPart.getMin()));
        outsurcedMaxTb.setText(Integer.toString(osPart.getMax()));
        outsurcedCompanyIdTb.setText(osPart.getCompanyName());
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        outsurcedPartRadioBtn.setSelected(true);
    }    
    
}
