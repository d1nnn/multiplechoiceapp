package com.multiplechoice.Controllers.Chapter;

import java.net.URL;
import java.util.ResourceBundle;

import com.multiplechoice.Models.Chapter.Chapter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChapterController implements Initializable {
@FXML
TextField txfQuantity;

@FXML
Label txtNameChapter;

public Chapter xuLyChapter;

public void setText(String string) {
	txtNameChapter.setText(string);
} 
public TextField getTextfield() {
	return txfQuantity;
}

public void clickAdd() {
	int number=0;
	try {
		number=Integer.parseInt(txfQuantity.getText());
		number++;
		txfQuantity.setText(String.valueOf(number));
	} catch (Exception e) {
		// TODO: handle exception
	}
	
}


public void clickSub() {
	int number=0;
	try {
		number=Integer.parseInt(txfQuantity.getText());
		if(number==0) {
			txfQuantity.setText("0");
		}
		else {
			number--;
			txfQuantity.setText(String.valueOf(number));
		}
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
