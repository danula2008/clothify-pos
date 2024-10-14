package icet.edu.erp.controller.product;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class AddProductFormController {

    @FXML
    private JFXButton btnDone;

    @FXML
    private JFXButton btnEditUploadImg;

    @FXML
    private JFXButton btnUploadImage;

    @FXML
    private JFXComboBox<?> cmbCategory;

    @FXML
    private Group grpShowUploadImg;

    @FXML
    private Group grpUploadImg;

    @FXML
    private ImageView imgShowImg;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtProductSize;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        //TODO
    }

}
