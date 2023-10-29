package com.multiplechoice.Controllers.Login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.multiplechoice.Controllers.Student.StudentMainScreenController;
import com.multiplechoice.Controllers.Teacher.HomeTeacherController;
import com.multiplechoice.Listeners.UserListener;
import com.multiplechoice.Models.User.User;
import com.multiplechoice.Models.User.UserModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	Label txtNote;

	@FXML
	PasswordField txfPass;

	@FXML
	TextField txfUser;

	User user;
	HomeTeacherController homeTeacherController = new HomeTeacherController();

	public void clickLogin() throws IOException {
		if (check()) {
			// đong cửa sổ hiện bằng cách từ con gọi cha, cha ẩn cửa sổ
			if(user.isAdmin()) {
				txtNote.getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home/HomeTeacher.fxml"));
				try {

					Parent root = loader.load();
					HomeTeacherController controller = loader.getController();
					controller.setUserListener(new UserListener() {
						@Override
						public User GetUser() {
							return user;
						}

						@Override
						public void handle(Event event) {

						}
					});
					Scene scene = new Scene(root);
					Stage stage = (Stage)txtNote.getScene().getWindow();
					stage.setScene(scene);
					stage.show();
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home/StudentMainScreen.fxml"));

				Parent root = loader.load();
				StudentMainScreenController controller = loader.getController();
				controller.setUserListener(new UserListener() {
                @Override
                public void handle(Event event) {

                }

                @Override
                public User GetUser() {
                    return user;
                }
            });
				controller.addExamList();
				Scene scene = new Scene(root);
				Stage stage = (Stage)txtNote.getScene().getWindow();
				stage.setScene(scene);
				stage.setMaximized(true);
				stage.show();
			}
		} else {
			txtNote.setVisible(true);
			txtNote.setText("Sai Tên người dùng hoặc Mật khẩu!!");
		}

	}



	public void clickReset() {
		txfPass.setText(null);
		txfUser.setText(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		txtNote.setVisible(false);

//Kêt nối với bảng User
//		try {
//			ResultSet resultSet = user.executeQuery("select * from tblUser");
//			while (resultSet.next()) {
//				userDemo usarDemo = new userDemo(resultSet.getString("id"), resultSet.getString("userName"),
//						resultSet.getString("pass"),resultSet.getBoolean("isAdmin"));
//				// System.out.println(resultSet.getString("id")+"
//				// "+resultSet.getString("userName"));
//				list.add(usarDemo);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		// khi thay đổi user hoac pass thi thong báo sẽ tắt
		txfPass.textProperty().addListener((observable, oldValue, newValue) -> {
			txtNote.setVisible(false);
		});
		txfUser.textProperty().addListener((observable, oldValue, newValue) -> {
			txtNote.setVisible(false);
		});
	}

	public boolean check() {
		try {
			user = UserModel.Login(txfUser.getText(), txfPass.getText());
			if(user != null)
				return true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Lỗi không lấy được người dùng: " + e);
		}
		return false;
	}
}
