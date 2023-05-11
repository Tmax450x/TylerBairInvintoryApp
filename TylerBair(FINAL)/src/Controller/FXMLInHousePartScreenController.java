/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Papa Bair
 */

public class FXMLInHousePartScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Stage stage;
    Parent scene;
    Part part;
    private int modId;  //This helps keep the ids attached to the object.
    
    @FXML
    private Label inHouseLBL;
    
    @FXML
    private RadioButton inhouseRB;

    @FXML
    private TextField inhousePartIdTb;

    @FXML
    public TextField inhousePartNameTb;

    @FXML
    private TextField inhousePartPriceTb;

    @FXML
    private TextField inhousePartInvTb;

    @FXML
    private TextField inhousePartMinTb;

    @FXML
    private TextField inhousePartMaxTb;

    @FXML
    private TextField inhousePartMachiIdTb;

    @FXML
    void onActionInHousePartCancel(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part values have not been saved.  Do you wish to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent () && result.get() ==  ButtonType.OK)
        {        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/FXMLMainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }         
    }

    @FXML
    void onActionInHousePartSave(ActionEvent event) throws IOException {
        
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

            String name = inhousePartNameTb.getText();
            double price = Double.parseDouble(inhousePartPriceTb.getText());
            int stock = Integer.parseInt(inhousePartInvTb.getText());
            int min = Integer.parseInt(inhousePartMinTb.getText());
            int max = Integer.parseInt(inhousePartMaxTb.getText());
            int machineId = Integer.parseInt(inhousePartMachiIdTb.getText());

            Inventory.addPart(new InhousePart(id, name, price, stock, min, max, machineId));
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

    @FXML
    void onActionOutSourcedRadio(ActionEvent event) throws IOException {
        
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/FXMLAddModOutSourcePartSceen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    public void setPart(InhousePart ihPart) {
        
        /**
         * Brings in a Part object and populates the text field with it.
         */
        this.part = ihPart;
        
        modId = ihPart.getId();
        inHouseLBL.setText("Mod Part");
        inhousePartIdTb.setText(Integer.toString(ihPart.getId()));
        inhousePartNameTb.setText(ihPart.getName());
        inhousePartPriceTb.setText(Double.toString(ihPart.getPrice()));
        inhousePartInvTb.setText(Integer.toString(ihPart.getStock()));
        inhousePartMinTb.setText(Integer.toString(ihPart.getMin()));
        inhousePartMaxTb.setText(Integer.toString(ihPart.getMax()));
        inhousePartMachiIdTb.setText(Integer.toString(ihPart.getMachineId()));
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
        inhouseRB.setSelected(true);
        
    }    
    
}
