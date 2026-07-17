package com.example.mubashir;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Transaction {
    private static BorderPane root;
    private static VBox mainContent;

    public static BorderPane transactionPane() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + StyleHelper.LIGHT_BG + ";");

        // Header
        HBox header = createHeader("Transaction Portal");
        root.setTop(header);

        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        root.setCenter(mainContent);

        // Start with confirmation
        showConfirmationPane();

        return root;
    }

    private static HBox createHeader(String title) {
        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle(
                "-fx-background-color: linear-gradient(to right, " + StyleHelper.PRIMARY_COLOR + ", " + StyleHelper.SECONDARY_COLOR + "); " +
                        "-fx-border-color: transparent transparent " + StyleHelper.BORDER_COLOR + " transparent; " +
                        "-fx-border-width: 0 0 1px 0;"
        );
        header.setAlignment(Pos.CENTER_LEFT);

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("💱");
        icon.setStyle("-fx-font-size: 28px; -fx-text-fill: " + StyleHelper.ACCENT_BLUE + ";");

        Text titleText = new Text(title);
        titleText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleText.setStyle("-fx-fill: white;");

        Text subtitle = new Text("Secure Money Transfers");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setStyle("-fx-fill: #bdc3c7;");

        VBox textBox = new VBox(2, titleText, subtitle);
        textBox.setAlignment(Pos.CENTER_LEFT);

        titleBox.getChildren().addAll(icon, textBox);
        header.getChildren().add(titleBox);

        return header;
    }

    private static void showConfirmationPane() {
        mainContent.getChildren().clear();

        VBox confirmationBox = new VBox(20);
        confirmationBox.setPadding(new Insets(30));
        confirmationBox.setAlignment(Pos.CENTER);
        confirmationBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label icon = new Label("🔒");
        icon.setStyle("-fx-font-size: 64px;");

        Label title = new Label("Identity Verification Required");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        Label subtitle = new Label("Please verify your identity to continue");
        subtitle.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER);

        String email = Server.getUserEmail(Main.userId);
        TextField emailField = createReadOnlyField(email);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 250;"
        );

        Button confirmBtn = StyleHelper.createStyledButton("✅ Verify Identity", StyleHelper.ACCENT_GREEN);
        confirmBtn.setOnAction(e -> {
            if (Server.logIn(email, passwordField.getText())) {
                showTransactionOptions();
            } else {
                passwordField.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 4; " +
                                "-fx-padding: 10; " +
                                "-fx-font-size: 14px; " +
                                "-fx-pref-width: 250;"
                );
            }
        });

        form.getChildren().addAll(
                createFormField("Email", emailField),
                createFormField("Password", passwordField),
                confirmBtn
        );

        confirmationBox.getChildren().addAll(icon, title, subtitle, form);
        mainContent.getChildren().add(confirmationBox);
    }

    private static void showTransactionOptions() {
        mainContent.getChildren().clear();

        VBox optionsBox = new VBox(20);
        optionsBox.setPadding(new Insets(30));
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label icon = new Label("💱");
        icon.setStyle("-fx-font-size: 64px;");

        Label title = new Label("Select Transaction Type");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        VBox buttonsBox = new VBox(15);
        buttonsBox.setAlignment(Pos.CENTER);

        Button depositBtn = createOptionButton("💰 Deposit Money",
                "Add funds to your wallet", StyleHelper.ACCENT_GREEN);
        depositBtn.setOnAction(e -> showDepositForm());

        Button withdrawBtn = createOptionButton("💸 Withdraw Money",
                "Withdraw funds from your wallet", StyleHelper.ACCENT_RED);
        withdrawBtn.setOnAction(e -> showWithdrawForm());

        buttonsBox.getChildren().addAll(depositBtn, withdrawBtn);

        optionsBox.getChildren().addAll(icon, title, buttonsBox);
        mainContent.getChildren().add(optionsBox);
    }

    private static Button createOptionButton(String text, String subtitle, String color) {
        Button btn = new Button();
        btn.setPrefSize(300, 80);

        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);

        Label mainText = new Label(text);
        mainText.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label subText = new Label(subtitle);
        subText.setStyle("-fx-text-fill: rgba(255,255,255,0.8); -fx-font-size: 12px;");

        content.getChildren().addAll(mainText, subText);
        btn.setGraphic(content);

        btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 20;",
                color
        ));

        btn.setOnMouseEntered(e -> btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 20; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 3);",
                StyleHelper.darkenColor(color)
        )));

        btn.setOnMouseExited(e -> btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 20;",
                color
        )));

        return btn;
    }

    private static void showDepositForm() {
        mainContent.getChildren().clear();

        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(30));
        formBox.setAlignment(Pos.TOP_CENTER);
        formBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("💰");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Label title = new Label("Deposit Funds");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        Label subtitle = new Label("Add money to your wallet");
        subtitle.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER);

        double currentBalance = Server.getWalletAmount(Main.userId);
        Label balanceLabel = new Label("Current Balance: $" + String.format("%.2f", currentBalance));
        balanceLabel.setStyle("-fx-text-fill: " + StyleHelper.ACCENT_GREEN + "; -fx-font-size: 16px; -fx-font-weight: bold;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to deposit");
        amountField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 250;"
        );

        TextField accountField = new TextField();
        accountField.setPromptText("Bank account number");
        accountField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 250;"
        );

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);

        Button submitBtn = StyleHelper.createStyledButton("💰 Deposit Now", StyleHelper.ACCENT_GREEN);
        Button backBtn = StyleHelper.createStyledButton("← Back", StyleHelper.ACCENT_BLUE);

        submitBtn.setOnAction(e -> {
            try {
                int amount = Integer.parseInt(amountField.getText());
                if (amount > 0) {
                    Server.depositAmount(amount, Main.userId);
                    showSuccessAlert("Deposit Successful",
                            "Successfully deposited $" + amount +
                                    "\nYour new balance will be updated shortly.");
                    amountField.clear();
                    accountField.clear();
                } else {
                    amountField.setStyle(
                            "-fx-background-color: white; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                    "-fx-border-radius: 4; " +
                                    "-fx-padding: 10; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-pref-width: 250;"
                    );
                }
            } catch (NumberFormatException ex) {
                amountField.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 4; " +
                                "-fx-padding: 10; " +
                                "-fx-font-size: 14px; " +
                                "-fx-pref-width: 250;"
                );
            }
        });

        backBtn.setOnAction(e -> showTransactionOptions());

        buttons.getChildren().addAll(submitBtn, backBtn);

        form.getChildren().addAll(balanceLabel,
                createFormField("Amount ($)", amountField),
                createFormField("Account Number", accountField),
                buttons);

        formBox.getChildren().addAll(header, form);
        mainContent.getChildren().add(formBox);
    }

    private static void showWithdrawForm() {
        mainContent.getChildren().clear();

        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(30));
        formBox.setAlignment(Pos.TOP_CENTER);
        formBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("💸");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Label title = new Label("Withdraw Funds");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        Label subtitle = new Label("Withdraw money from your wallet");
        subtitle.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER);

        double currentBalance = Server.getWalletAmount(Main.userId);
        Label balanceLabel = new Label("Available Balance: $" + String.format("%.2f", currentBalance));
        balanceLabel.setStyle("-fx-text-fill: " +
                (currentBalance > 0 ? StyleHelper.ACCENT_GREEN : StyleHelper.ACCENT_RED) +
                "; -fx-font-size: 16px; -fx-font-weight: bold;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to withdraw");
        amountField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 250;"
        );

        TextField accountField = new TextField();
        accountField.setPromptText("Bank account number");
        accountField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 250;"
        );

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);

        Button submitBtn = StyleHelper.createStyledButton("💸 Withdraw Now", StyleHelper.ACCENT_RED);
        Button backBtn = StyleHelper.createStyledButton("← Back", StyleHelper.ACCENT_BLUE);

        submitBtn.setOnAction(e -> {
            try {
                int amount = Integer.parseInt(amountField.getText());
                if (amount <= 0) {
                    amountField.setStyle(
                            "-fx-background-color: white; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                    "-fx-border-radius: 4; " +
                                    "-fx-padding: 10; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-pref-width: 250;"
                    );
                } else if (amount > currentBalance) {
                    showErrorAlert("Insufficient Balance",
                            "You cannot withdraw $" + amount +
                                    "\nAvailable balance: $" + currentBalance);
                } else {
                    Server.withdrawAmount(amount, Main.userId);
                    showSuccessAlert("Withdrawal Successful",
                            "Successfully withdrew $" + amount +
                                    "\nPlease allow 1-2 business days for processing.");
                    amountField.clear();
                    accountField.clear();
                }
            } catch (NumberFormatException ex) {
                amountField.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 4; " +
                                "-fx-padding: 10; " +
                                "-fx-font-size: 14px; " +
                                "-fx-pref-width: 250;"
                );
            }
        });

        backBtn.setOnAction(e -> showTransactionOptions());

        buttons.getChildren().addAll(submitBtn, backBtn);

        form.getChildren().addAll(balanceLabel,
                createFormField("Amount ($)", amountField),
                createFormField("Account Number", accountField),
                buttons);

        formBox.getChildren().addAll(header, form);
        mainContent.getChildren().add(formBox);
    }

    private static TextField createReadOnlyField(String text) {
        TextField field = new TextField(text);
        field.setEditable(false);
        field.setStyle(
                "-fx-background-color: #f5f5f5; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px;"
        );
        return field;
    }

    private static HBox createFormField(String labelText, Control control) {
        HBox field = new HBox(10);
        field.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText + ":");
        label.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600; -fx-min-width: 120;");

        field.getChildren().addAll(label, control);
        return field;
    }

    private static void showSuccessAlert(String title, String message) {
        Stage alert = new Stage();
        alert.initModality(Modality.APPLICATION_MODAL);
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

        Scene scene = new Scene(pane, 400, 250);
        alert.setScene(scene);
        alert.show();
    }

    private static void showErrorAlert(String title, String message) {
        Stage alert = new Stage();
        alert.initModality(Modality.APPLICATION_MODAL);
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
}