package icet.edu.erp.controller.supplier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AddSupplierFormController {

    @FXML
    private JFXButton btnDone;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
	    //TODO
    }

}
