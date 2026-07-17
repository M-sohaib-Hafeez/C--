package com.example.mubashir;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogIn {
    private static Stage stage = new Stage();

    public static Stage logInStage() {
        stage.setTitle("Login - Loan Management System");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + StyleHelper.LIGHT_BG + ";");

        // Header
        HBox header = new HBox(15);
        header.setPadding(new Insets(20));
        header.setStyle(
                "-fx-background-color: linear-gradient(to right, " + StyleHelper.PRIMARY_COLOR + ", " + StyleHelper.SECONDARY_COLOR + "); " +
                        "-fx-border-color: transparent transparent " + StyleHelper.BORDER_COLOR + " transparent; " +
                        "-fx-border-width: 0 0 1px 0;"
        );
        header.setAlignment(Pos.CENTER);

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER);

        Label icon = new Label("🔐");
        icon.setStyle("-fx-font-size: 32px; -fx-text-fill: " + StyleHelper.ACCENT_BLUE + ";");

        VBox textBox = new VBox(2);
        Text title = new Text("Welcome Back");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setStyle("-fx-fill: white;");

        Text subtitle = new Text("Login to your account");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setStyle("-fx-fill: #bdc3c7;");

        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().addAll(title, subtitle);
        titleBox.getChildren().addAll(icon, textBox);
        header.getChildren().add(titleBox);

        root.setTop(header);

        // Main content
        VBox center = new VBox(20);
        center.setPadding(new Insets(30));
        center.setAlignment(Pos.CENTER);
        center.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        // Form
        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(350);

        Label emailLabel = new Label("Email Address");
        emailLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        TextField email = new TextField();
        email.setPromptText("Enter your email address");
        email.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 12; " +
                        "-fx-font-size: 14px;"
        );

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        PasswordField password = new PasswordField();
        password.setPromptText("Enter your password");
        password.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 12; " +
                        "-fx-font-size: 14px;"
        );

        Button logInButton = StyleHelper.createStyledButton("🔐 Login", StyleHelper.ACCENT_GREEN);
        logInButton.setPrefWidth(200);

        logInButton.setOnAction(e -> {
            if(Server.isEmailExist(email.getText())) {
                if(!Server.logIn(email.getText(), password.getText())) {
                    password.setStyle(
                            "-fx-background-color: white; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                    "-fx-border-radius: 6; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-size: 14px;"
                    );
                } else {
                    stage.close();
                    Main.userId = Server.getUserId(email.getText());
                    Main.userRole = Server.getUserRole(email.getText());
                    Main.setRightSideOfTopBar();
                }
            } else {
                email.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 12; " +
                                "-fx-font-size: 14px;"
                );
            }
        });

        Label signupPrompt = new Label("Don't have an account?");
        signupPrompt.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        Button signupButton = new Button("Sign Up");
        signupButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: " + StyleHelper.ACCENT_BLUE + "; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 0; " +
                        "-fx-border-width: 0;"
        );
        signupButton.setOnAction(e -> {
            stage.close();
            SignIn.signInStage().show();
        });
            email.setOnAction(e -> {
               password.requestFocus();
            });
        email.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                password.requestFocus();
            }
        });
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                email.requestFocus();
            }
        });
        password.setOnAction(event -> {
            logInButton.fire();
        });
        HBox signupBox = new HBox(5, signupPrompt, signupButton);
        signupBox.setAlignment(Pos.CENTER);

        form.getChildren().addAll(emailLabel, email, passwordLabel, password, logInButton, signupBox);
        center.getChildren().add(form);

        root.setCenter(center);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        return stage;
    }
}