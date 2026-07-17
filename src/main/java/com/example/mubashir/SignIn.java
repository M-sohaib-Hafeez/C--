package com.example.mubashir;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignIn {
    private static Stage stage = new Stage();
    static String userRole = "";
    private static ToggleGroup roleToggleGroup;

    public static Stage signInStage() {
        stage.setTitle("Sign Up - Loan Management System");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + StyleHelper.LIGHT_BG + ";");

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

        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 32px; -fx-text-fill: " + StyleHelper.ACCENT_BLUE + ";");

        VBox textBox = new VBox(2);
        Text title = new Text("Create Account");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setStyle("-fx-fill: white;");

        Text subtitle = new Text("Join our loan management system");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setStyle("-fx-fill: #bdc3c7;");

        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().addAll(title, subtitle);
        titleBox.getChildren().addAll(icon, textBox);
        header.getChildren().add(titleBox);

        root.setTop(header);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        VBox center = new VBox(20);
        center.setPadding(new Insets(30));
        center.setAlignment(Pos.TOP_CENTER);
        center.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(400);

        // Email field with validation
        Label emailLabel = new Label("Email Address *");
        emailLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        TextField email = new TextField();
        email.setPromptText("you@example.com");
        email.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 12; " +
                        "-fx-font-size: 14px;"
        );

        Label emailHint = new Label("For Borrower: rollno@students.university.edu.pk | For Investor: any@gmail.com");


        Label passwordLabel = new Label("Password *");
        passwordLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        PasswordField password = new PasswordField();
        password.setPromptText("Create a strong password (min 8 characters)");
        password.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 12; " +
                        "-fx-font-size: 14px;"
        );

        // Name field with validation
        Label nameLabel = new Label("Full Name *");
        nameLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        TextField name = new TextField();
        name.setPromptText("John Doe");
        name.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 12; " +
                        "-fx-font-size: 14px;"
        );

        // Role selection
        Label roleLabel = new Label("Select Your Role *");
        roleLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        VBox roleBox = new VBox(10);
        roleBox.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 15;"
        );

        roleToggleGroup = new ToggleGroup();

        HBox borrowerOption = createRoleOption("💰 Borrower",
                "Apply for loans with competitive rates", roleToggleGroup);
        RadioButton borrowerRadio = (RadioButton) borrowerOption.getChildren().get(0);

        HBox investorOption = createRoleOption("📈 Investor",
                "Invest in loans and earn returns", roleToggleGroup);
        RadioButton investorRadio = (RadioButton) investorOption.getChildren().get(0);

        roleBox.getChildren().addAll(borrowerOption, investorOption);

        investorRadio.setSelected(true);
        userRole = "Investor";

        // Phone number field with validation
        Label phoneLabel = new Label("Phone Number *");
        phoneLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("0312-3456789 (format: XXXX-XXXXXXX)");
        phoneNumber.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 12; " +
                        "-fx-font-size: 14px;"
        );

        // Address field
        Label addressLabel = new Label("Address *");
        addressLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        TextField address = new TextField();
        address.setPromptText("House #, Street, City");
        address.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 12; " +
                        "-fx-font-size: 14px;"
        );

        // Sign up button
        Button signInButton = StyleHelper.createStyledButton("✅ Create Account", StyleHelper.ACCENT_GREEN);
        signInButton.setPrefWidth(200);

        signInButton.setOnAction(e -> {
            resetFieldStyles(email, password, name, phoneNumber, address);

            RadioButton selectedRadio = (RadioButton) roleToggleGroup.getSelectedToggle();
            if (selectedRadio != null) {
                userRole = (String) selectedRadio.getUserData();
            } else {
                userRole = "Investor";
            }

            boolean isValid = true;
            String errorMessage = "";

            if (email.getText().isEmpty() || password.getText().isEmpty() ||
                    name.getText().isEmpty() || phoneNumber.getText().isEmpty() ||
                    address.getText().isEmpty()) {
                isValid = false;
                errorMessage = "Please fill in all required fields marked with *.";
            }

            if (isValid && !isValidEmail(email.getText(), userRole)) {
                isValid = false;
                email.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 12; " +
                                "-fx-font-size: 14px;"
                );
                if (userRole.equals("Borrower")) {
                    errorMessage = "Invalid email format for Borrower.\n" +
                            "Borrower email must be in format: {rollno}@students.{university}.edu.pk\n" +
                            "Example: 24f-cs-071@students.duet.edu.pk\n" +
                            "Example: 2022-cs-123@students.ned.edu.pk";
                } else {
                    errorMessage = "Invalid email format for Investor.\n" +
                            "Investor email must be a valid Gmail address (@gmail.com)";
                }
            }

            if (isValid && !isValidPassword(password.getText())) {
                isValid = false;
                password.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 12; " +
                                "-fx-font-size: 14px;"
                );
                errorMessage = "Password must be at least 8 characters long.";
            }

            // Validate full name (should have at least two words)
            if (isValid && !isValidFullName(name.getText())) {
                isValid = false;
                name.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 12; " +
                                "-fx-font-size: 14px;"
                );
                errorMessage = "Please enter your full name (first and last name).";
            }

            // Validate phone number format
            if (isValid && !isValidPhoneNumber(phoneNumber.getText())) {
                isValid = false;
                phoneNumber.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 12; " +
                                "-fx-font-size: 14px;"
                );
                errorMessage = "Invalid phone number format.\n" +
                        "Phone number must be in format: XXXX-XXXXXXX\n" +
                        "Example: 0312-3456789";
            }

            // Check if email already exists
            if (isValid && Server.isEmailExist(email.getText())) {
                isValid = false;
                email.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 12; " +
                                "-fx-font-size: 14px;"
                );
                errorMessage = "This email address is already registered.\n" +
                        "Please use a different email or login.";
            }

            if (!isValid) {
                showErrorAlert("Validation Error", errorMessage);
                return;
            }

            // All validations passed, proceed with signup
            Main.setRightSideOfTopBar();
            stage.close();

            Server.signUp(userRole, name.getText(), email.getText(),
                    phoneNumber.getText(), address.getText(), password.getText());
            Main.userId = Server.getUserId(email.getText());
            Main.userRole = userRole;

            showSuccessAlert("Account Created",
                    "Welcome to Loan Management System!\n\n" +
                            "Your account has been created successfully.\n" +
                            "Role: " + userRole + "\n" +
                            "Name: " + name.getText() + "\n" +
                            "Email: " + email.getText());
        });

        // Login prompt
        Label loginPrompt = new Label("Already have an account?");
        loginPrompt.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        Button loginButton = new Button("Log In");
        loginButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: " + StyleHelper.ACCENT_BLUE + "; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 0; " +
                        "-fx-border-width: 0;"
        );
        loginButton.setOnAction(e -> {
            stage.close();
            LogIn.logInStage().show();
        });

        HBox loginBox = new HBox(5, loginPrompt, loginButton);
        loginBox.setAlignment(Pos.CENTER);

        // Form navigation with Enter key
        email.setOnAction(e -> password.requestFocus());
        password.setOnAction(e -> name.requestFocus());

        RadioButton[] radios = { borrowerRadio, investorRadio };
        for (RadioButton rb : radios) {
            rb.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    phoneNumber.requestFocus();
                }
            });
        }

        name.setOnAction(e -> phoneNumber.requestFocus());
        phoneNumber.setOnAction(e -> address.requestFocus());
        address.setOnAction(e -> signInButton.fire());

        // Real-time validation hints
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!isValidEmail(newValue, userRole)) {
                    email.setStyle(
                            "-fx-background-color: #fff5f5; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                    "-fx-border-radius: 6; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-size: 14px;"
                    );
                } else {
                    email.setStyle(
                            "-fx-background-color: #f5fff5; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_GREEN + "; " +
                                    "-fx-border-radius: 6; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-size: 14px;"
                    );
                }
            }
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!isValidPassword(newValue)) {
                    password.setStyle(
                            "-fx-background-color: #fff5f5; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                    "-fx-border-radius: 6; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-size: 14px;"
                    );
                } else {
                    password.setStyle(
                            "-fx-background-color: #f5fff5; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_GREEN + "; " +
                                    "-fx-border-radius: 6; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-size: 14px;"
                    );
                }
            }
        });

        phoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!isValidPhoneNumber(newValue)) {
                    phoneNumber.setStyle(
                            "-fx-background-color: #fff5f5; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                    "-fx-border-radius: 6; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-size: 14px;"
                    );
                } else {
                    phoneNumber.setStyle(
                            "-fx-background-color: #f5fff5; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_GREEN + "; " +
                                    "-fx-border-radius: 6; " +
                                    "-fx-padding: 12; " +
                                    "-fx-font-size: 14px;"
                    );
                }
            }
        });

        borrowerRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                userRole = "Borrower";
                emailHint.setText("Format: {rollno}@students.{university}.edu.pk (Example: 24F-CS-074@students.duet.edu.pk)");
                if (!email.getText().isEmpty()) {
                    if (!isValidEmail(email.getText(), "Borrower")) {
                        email.setStyle(
                                "-fx-background-color: #fff5f5; " +
                                        "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                        "-fx-border-radius: 6; " +
                                        "-fx-padding: 12; " +
                                        "-fx-font-size: 14px;"
                        );
                    } else {
                        email.setStyle(
                                "-fx-background-color: #f5fff5; " +
                                        "-fx-border-color: " + StyleHelper.ACCENT_GREEN + "; " +
                                        "-fx-border-radius: 6; " +
                                        "-fx-padding: 12; " +
                                        "-fx-font-size: 14px;"
                        );
                    }
                }
            }
        });

        investorRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                userRole = "Investor";
                emailHint.setText("Format: any valid Gmail address (@gmail.com)");
                if (!email.getText().isEmpty()) {
                    if (!isValidEmail(email.getText(), "Investor")) {
                        email.setStyle(
                                "-fx-background-color: #fff5f5; " +
                                        "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                        "-fx-border-radius: 6; " +
                                        "-fx-padding: 12; " +
                                        "-fx-font-size: 14px;"
                        );
                    } else {
                        email.setStyle(
                                "-fx-background-color: #f5fff5; " +
                                        "-fx-border-color: " + StyleHelper.ACCENT_GREEN + "; " +
                                        "-fx-border-radius: 6; " +
                                        "-fx-padding: 12; " +
                                        "-fx-font-size: 14px;"
                        );
                    }
                }
            }
        });

        form.getChildren().addAll(
                emailLabel, email, emailHint,
                passwordLabel, password,
                nameLabel, name,
                roleLabel, roleBox,
                phoneLabel, phoneNumber,
                addressLabel, address,
                signInButton, loginBox
        );

        center.getChildren().add(form);
        scrollPane.setContent(center);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 600, 750);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(750);

        return stage;
    }

    // Validation methods
    private static boolean isValidEmail(String email, String role) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        email = email.trim().toLowerCase();

        if (role.equals("Borrower")) {

            return email.matches("^[a-zA-Z0-9._%+-]+@students\\.[a-zA-Z0-9.-]+\\.edu\\.pk$");
        } else {
            return email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
        }
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    private static boolean isValidFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Check if name contains at least two words (first and last name)
        String trimmedName = name.trim();
        String[] nameParts = trimmedName.split("\\s+");
        return nameParts.length >= 2;
    }

    private static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        // Format: XXXX-XXXXXXX where X is digit
        return phone.matches("^\\d{4}-\\d{7}$");
    }

    private static void resetFieldStyles(TextField email, PasswordField password, TextField name,
                                         TextField phone, TextField address) {
        String defaultStyle = "-fx-background-color: white; " +
                "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                "-fx-border-radius: 6; " +
                "-fx-padding: 12; " +
                "-fx-font-size: 14px;";

        email.setStyle(defaultStyle);
        password.setStyle(defaultStyle);
        name.setStyle(defaultStyle);
        phone.setStyle(defaultStyle);
        address.setStyle(defaultStyle);
    }

    private static HBox createRoleOption(String label, String description, ToggleGroup group) {
        HBox option = new HBox(15);
        option.setAlignment(Pos.CENTER_LEFT);
        option.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 6; " +
                        "-fx-padding: 10; " +
                        "-fx-cursor: hand;"
        );

        String[] labelParts = label.split(" ", 2);
        String icon = labelParts[0];
        String title = labelParts[1];

        RadioButton radio = new RadioButton();
        radio.setToggleGroup(group);
        radio.setStyle("-fx-cursor: hand;");
        radio.setUserData(title);

        // Add click listener to the entire HBox to select the radio button
        option.setOnMouseClicked(e -> {
            radio.setSelected(true);
            userRole = title;
            for (Node node : ((VBox) option.getParent()).getChildren()) {
                if (node instanceof HBox) {
                    HBox hbox = (HBox) node;
                    RadioButton rb = (RadioButton) hbox.getChildren().get(0);
                    if (!rb.isSelected()) {
                        hbox.setStyle(
                                "-fx-background-color: white; " +
                                        "-fx-background-radius: 6; " +
                                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                                        "-fx-border-radius: 6; " +
                                        "-fx-padding: 10;"
                        );
                    }
                }
            }
            option.setStyle(
                    "-fx-background-color: " + StyleHelper.ACCENT_BLUE + "15; " +
                            "-fx-background-radius: 6; " +
                            "-fx-border-color: " + StyleHelper.ACCENT_BLUE + "; " +
                            "-fx-border-radius: 6; " +
                            "-fx-padding: 10;"
            );
        });

        option.setOnMouseEntered(e -> {
            RadioButton rb = (RadioButton) option.getChildren().get(0);
            if (!rb.isSelected()) {
                option.setStyle(
                        "-fx-background-color: #f8f9fa; " +
                                "-fx-background-radius: 6; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_BLUE + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 10;"
                );
            }
        });

        option.setOnMouseExited(e -> {
            RadioButton rb = (RadioButton) option.getChildren().get(0);
            if (!rb.isSelected()) {
                option.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-background-radius: 6; " +
                                "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                                "-fx-border-radius: 6; " +
                                "-fx-padding: 10;"
                );
            }
        });

        radio.setOnAction(e -> {
            userRole = title;
            for (Node node : ((VBox) option.getParent()).getChildren()) {
                if (node instanceof HBox) {
                    HBox hbox = (HBox) node;
                    RadioButton rb = (RadioButton) hbox.getChildren().get(0);
                    if (!rb.isSelected()) {
                        hbox.setStyle(
                                "-fx-background-color: white; " +
                                        "-fx-background-radius: 6; " +
                                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                                        "-fx-border-radius: 6; " +
                                        "-fx-padding: 10;"
                        );
                    }
                }
            }
            option.setStyle(
                    "-fx-background-color: " + StyleHelper.ACCENT_BLUE + "15; " +
                            "-fx-background-radius: 6; " +
                            "-fx-border-color: " + StyleHelper.ACCENT_BLUE + "; " +
                            "-fx-border-radius: 6; " +
                            "-fx-padding: 10;"
            );
        });

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 20px; -fx-padding: 0 5 0 0;");

        VBox textBox = new VBox(2);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px; -fx-wrap-text: true;");

        textBox.getChildren().addAll(titleLabel, descLabel);
        option.getChildren().addAll(radio, iconLabel, textBox);

        return option;
    }

    private static void showErrorAlert(String title, String message) {
        Stage alert = new Stage();
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        alert.setTitle(title);

        VBox pane = new VBox(15);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);
        pane.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12;"
        );

        Label icon = new Label("❌");
        icon.setStyle("-fx-font-size: 48px;");

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");

        Button ok = StyleHelper.createStyledButton("OK", StyleHelper.ACCENT_RED);
        ok.setOnAction(e -> alert.close());

        pane.getChildren().addAll(icon, label, ok);

        Scene scene = new Scene(pane, 400, 250);
        alert.setScene(scene);
        alert.show();
    }

    private static void showSuccessAlert(String title, String message) {
        Stage alert = new Stage();
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        alert.setTitle(title);

        VBox pane = new VBox(15);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);
        pane.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12;"
        );

        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 48px;");

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");

        Button ok = StyleHelper.createStyledButton("OK", StyleHelper.ACCENT_GREEN);
        ok.setOnAction(e -> alert.close());

        pane.getChildren().addAll(icon, label, ok);

        Scene scene = new Scene(pane, 450, 300);
        alert.setScene(scene);
        alert.show();
    }
}