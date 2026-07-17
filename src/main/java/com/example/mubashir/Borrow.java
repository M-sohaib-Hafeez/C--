package com.example.mubashir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.List;


public class Borrow {
    private static BorderPane root;
    private static BorderPane centerPane;
    private static VBox mainContent;

    public static BorderPane borrowPane() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + StyleHelper.LIGHT_BG + ";");
        HBox header = createHeader("Borrower Dashboard");
        root.setTop(header);
        centerPane = new BorderPane();
        centerPane.setPadding(new Insets(15));
        root.setCenter(centerPane);

        VBox sidebar = createSidebar();
        root.setLeft(sidebar);


        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollContent = new ScrollPane();
        scrollContent.setFitToWidth(true);
        scrollContent.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        scrollContent.setContent(mainContent);

        setDashboardView();

        centerPane.setCenter(scrollContent);

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

        Label icon = new Label("💰");
        icon.setStyle("-fx-font-size: 28px; -fx-text-fill: " + StyleHelper.ACCENT_BLUE + ";");

        Text titleText = new Text(title);
        titleText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleText.setStyle("-fx-fill: white;");

        Text subtitle = new Text("Loan Management System");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setStyle("-fx-fill: #bdc3c7;");

        VBox textBox = new VBox(2, titleText, subtitle);
        textBox.setAlignment(Pos.CENTER_LEFT);

        titleBox.getChildren().addAll(icon, textBox);
        header.getChildren().add(titleBox);

        return header;
    }

    private static VBox createSidebar() {
        VBox sidebar = new VBox(3);
        sidebar.setPadding(new Insets(20, 10, 20, 10));
        sidebar.setPrefWidth(180);
        sidebar.setStyle(
                "-fx-background-color: linear-gradient(to bottom, " + StyleHelper.PRIMARY_COLOR + ", " + StyleHelper.SECONDARY_COLOR + "); " +
                        "-fx-border-color: transparent " + StyleHelper.BORDER_COLOR + " transparent transparent; " +
                        "-fx-border-width: 0 1px 0 0;"
        );

        Label sidebarHeader = new Label("BORROWER NAVIGATION");
        sidebarHeader.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 15 5 10 5;");
        sidebar.getChildren().add(sidebarHeader);

        Button dashboardBtn = createSidebarButton("📊 Dashboard", e -> setDashboardView());
        Button formBtn = createSidebarButton("📝 Loan Application", e -> showBorrowerForm());
        Button historyBtn = createSidebarButton("📋 Loan History", e -> showLoanHistory());
        Button policiesBtn = createSidebarButton("📚 Policies", e -> showPolicies());
        Button walletBtn = createSidebarButton("💰 Wallet", e -> showWallet());

        sidebar.getChildren().addAll(dashboardBtn, formBtn, historyBtn, policiesBtn, walletBtn);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().add(spacer);

        Button backBtn = StyleHelper.createMenuButton("← Back to Home", StyleHelper.ACCENT_RED);
        backBtn.setOnAction(e -> Main.back());
        sidebar.getChildren().add(backBtn);

        return sidebar;
    }

    private static Button createSidebarButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 20, 12, 20));
        btn.setGraphicTextGap(15);
        btn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: " + StyleHelper.TEXT_LIGHT + "; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand;"
        );
        btn.setOnAction(handler);

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: rgba(255,255,255,0.1); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: " + StyleHelper.TEXT_LIGHT + "; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand;"
        ));
        return btn;
    }

    private static void setDashboardView() {
        mainContent.getChildren().clear();

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("📊");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Dashboard Overview");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("View your loan status and statistics");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        double balance = Server.getWalletAmount(Main.userId);
        List<String> loans = Server.getBorrowerFormSubmitMessagelist(Main.userId);
        int activeLoans = 0;
        for (String loan : loans) {
            if (loan.contains("Active")) activeLoans++;
        }

        VBox balanceCard = StyleHelper.createCard("Wallet Balance", "💰", String.format("$%.2f", balance), StyleHelper.ACCENT_GREEN);
        VBox loansCard = StyleHelper.createCard("Total Loans", "📋", String.valueOf(loans.size()), StyleHelper.ACCENT_BLUE);
        VBox activeCard = StyleHelper.createCard("Active Loans", "⚡", String.valueOf(activeLoans), StyleHelper.ACCENT_ORANGE);

        grid.add(balanceCard, 0, 0);
        grid.add(loansCard, 1, 0);
        grid.add(activeCard, 2, 0);

        // Quick Actions
        VBox actionsBox = new VBox(15);
        actionsBox.setPadding(new Insets(20));
        actionsBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label actionsTitle = new Label("Quick Actions");
        actionsTitle.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button applyLoanBtn = StyleHelper.createStyledButton("📝 Apply for New Loan", StyleHelper.ACCENT_BLUE);
        applyLoanBtn.setOnAction(e -> showBorrowerForm());

        Button viewHistoryBtn = StyleHelper.createStyledButton("📋 View Loan History", StyleHelper.ACCENT_GREEN);
        viewHistoryBtn.setOnAction(e -> showLoanHistory());

        Button goToWalletBtn = StyleHelper.createStyledButton("💰 Go to Wallet", StyleHelper.ACCENT_ORANGE);
        goToWalletBtn.setOnAction(e -> showWallet());

        HBox actionButtons = new HBox(15, applyLoanBtn, viewHistoryBtn, goToWalletBtn);
        actionButtons.setAlignment(Pos.CENTER);

        actionsBox.getChildren().addAll(actionsTitle, actionButtons);

        mainContent.getChildren().addAll(header, grid, actionsBox);
    }

    private static void showBorrowerForm() {
        mainContent.getChildren().clear();

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("📝");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Loan Application");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("Apply for a new loan");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        // Create scrollable container for the form
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        scrollPane.setPadding(new Insets(0));

        BorderPane form = borrowerForm();
        scrollPane.setContent(form);

        VBox container = new VBox(20, header, scrollPane);
        container.setPadding(new Insets(20));
        mainContent.getChildren().add(container);
    }

    private static void showLoanHistory() {
        mainContent.getChildren().clear();

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("📋");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Loan History");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("View your loan applications and status");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        List<String> loans = Server.getBorrowerFormSubmitMessagelist(Main.userId);
        TableView<String> table = getSubmittedFormsTable(loans);
        table.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8;"
        );

        VBox tableContainer = new VBox(10);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        tableContainer.getChildren().addAll(header, table);
        mainContent.getChildren().add(tableContainer);
    }

    private static void showPolicies() {
        mainContent.getChildren().clear();

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("📚");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Borrowing Policies");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("Terms and conditions for borrowers");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        VBox policiesBox = new VBox(20);
        policiesBox.setPadding(new Insets(20));
        policiesBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        String[] policies = {
                "1. Loan Eligibility: Applicants must be at least 18 years old and have a valid ID.",
                "2. Interest Rates: Standard interest rate is 4% per annum for all loans.",
                "3. Repayment Period: Loans must be repaid within 1-12 months as agreed.",
                "4. Credit Assessment: All applications undergo creditworthiness assessment.",
                "5. Late Payments: Late repayments incur additional 2% monthly penalty.",
                "6. Default: Failure to repay may result in legal action and credit score impact.",
                "7. Prepayment: Early repayment is allowed without penalties.",
                "8. Documentation: All loans require proper documentation and verification."
        };

        for (String policy : policies) {
            Label policyLabel = new Label(policy);
            policyLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-wrap-text: true;");
            policyLabel.setPrefWidth(600);
            policiesBox.getChildren().add(policyLabel);
        }

        mainContent.getChildren().addAll(header, policiesBox);
    }

    private static void showWallet() {
        mainContent.getChildren().clear();

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("💰");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Wallet Balance");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("View and manage your wallet");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        BorderPane walletPane = Wallet.walletPane();
        walletPane.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        VBox container = new VBox(20, header, walletPane);
        container.setPadding(new Insets(20));
        mainContent.getChildren().add(container);
    }

    private static BorderPane borrowerForm() {
        BorderPane form = new BorderPane();
        form.setPadding(new Insets(20));
        form.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        BasicInfo info = Server.getBasicInfo(Main.userId);

        VBox formContent = new VBox(15);
        formContent.setPrefWidth(700); // Set a minimum width

        // Personal Info Section
        VBox personalInfo = StyleHelper.createStyledForm("Personal Information");

        TextField email = createReadOnlyField(info.getEmail());
        TextField name = createReadOnlyField(info.getName());
        TextField phone = createReadOnlyField(info.getPhoneNumber());
        TextField address = createReadOnlyField(info.getAddress());

        personalInfo.getChildren().addAll(
                createFormFieldWithWidth("Email", email, 200),
                createFormFieldWithWidth("Name", name, 200),
                createFormFieldWithWidth("Phone", phone, 200),
                createFormFieldWithWidth("Address", address, 200)
        );

        VBox loanDetails = StyleHelper.createStyledForm("Loan Details");

        TextField amount = new TextField();
        amount.setPromptText("Enter amount (e.g., 5000)");
        amount.setPrefWidth(200);

        TextField rate = new TextField(Server.baseInterestRate()+"%");
        rate.setEditable(false);
        rate.setPrefWidth(200);

        TextField tenure = new TextField();
        tenure.setPromptText("Enter months (1-12)");
        tenure.setPrefWidth(200);

        loanDetails.getChildren().addAll(
                createFormFieldWithWidth("Loan Amount ($)", amount, 200),
                createFormFieldWithWidth("Interest Rate", rate, 200),
                createFormFieldWithWidth("Tenure (Months)", tenure, 200)
        );

        // Security Section
        VBox securitySection = StyleHelper.createStyledForm("Security Verification");

        PasswordField password = new PasswordField();
        password.setPromptText("Enter your password to confirm");
        password.setPrefWidth(200);

        securitySection.getChildren().add(createFormFieldWithWidth("Password", password, 200));

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button submit = StyleHelper.createStyledButton("✅ Submit Application", StyleHelper.ACCENT_GREEN);
        Button cancel = StyleHelper.createStyledButton("❌ Cancel", StyleHelper.ACCENT_RED);

        submit.setOnAction(e -> {
            if (Server.logIn(info.getEmail(), password.getText())) {
                int amountEntered = 0;
                int tenureEntered = 0;
                boolean valid = true;

                try {
                    amountEntered = Integer.parseInt(amount.getText());
                    if (amountEntered <= 0) {
                        amount.setStyle("-fx-border-color: " + StyleHelper.ACCENT_RED + ";");
                        valid = false;
                    }
                } catch (Exception es) {
                    amount.setStyle("-fx-border-color: " + StyleHelper.ACCENT_RED + ";");
                    valid = false;
                }

                try {
                    tenureEntered = Integer.parseInt(tenure.getText());
                    if (tenureEntered < 1 || tenureEntered > 12) {
                        tenure.setStyle("-fx-border-color: " + StyleHelper.ACCENT_RED + ";");
                        valid = false;
                    }
                } catch (Exception es) {
                    tenure.setStyle("-fx-border-color: " + StyleHelper.ACCENT_RED + ";");
                    valid = false;
                }

                if (valid) {
                    Server.submitBorrowForm(amountEntered, tenureEntered, Main.userId);
                    showSuccessAlert("Application Submitted",
                            "Your loan application for $" + amountEntered +
                                    " has been submitted successfully!\n" +
                                    "Application ID will be provided upon approval.");
                    setDashboardView();
                }
            } else {
                password.setStyle("-fx-border-color: " + StyleHelper.ACCENT_RED + ";");
            }
        });

        cancel.setOnAction(e -> setDashboardView());

        buttonBox.getChildren().addAll(submit, cancel);

        formContent.getChildren().addAll(personalInfo, loanDetails, securitySection, buttonBox);
        form.setCenter(formContent);

        return form;
    }

    private static TextField createReadOnlyField(String text) {
        TextField field = new TextField(text);
        field.setEditable(false);
        field.setStyle(
                "-fx-background-color: #f5f5f5; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 8; " +
                        "-fx-font-size: 14px;"
        );
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

    public static TableView<String> getSubmittedFormsTable(List<String> formsList) {
        ObservableList<String> observableList = FXCollections.observableArrayList(formsList);

        TableView<String> table = new TableView<>();
        table.setItems(observableList);
        table.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + ";"
        );

        TableColumn<String, String> col = new TableColumn<>("Submitted Forms");
        col.setStyle("-fx-background-color: #f8f9fa; -fx-font-weight: bold;");
        col.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue())
        );
        col.setPrefWidth(600);
        table.getColumns().add(col);
        return table;
    }

    // Add this helper method to Borrow.java
    private static HBox createFormFieldWithWidth(String labelText, Control control, double width) {
        HBox field = new HBox(10);
        field.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText + ":");
        label.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600; -fx-min-width: 150;");

        control.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 8; " +
                        "-fx-font-size: 14px;"
        );

        if (control instanceof TextField) {
            ((TextField) control).setPrefWidth(width);
        }

        field.getChildren().addAll(label, control);
        return field;
    }
}