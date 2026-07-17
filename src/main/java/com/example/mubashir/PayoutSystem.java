package com.example.mubashir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class PayoutSystem {

    public static BorderPane getPayoutDashboard() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + StyleHelper.LIGHT_BG + ";");

        // Header
        HBox header = createHeader("Payout System");
        root.setTop(header);

        // Main content
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        // Show appropriate view based on user role
        if (Main.userRole.equals("Borrower")) {
            mainContent.getChildren().add(getBorrowerPayoutView());
        } else if (Main.userRole.equals("Investor")) {
            mainContent.getChildren().add(getInvestorPayoutView());
        } else {
            mainContent.getChildren().add(getDefaultView());
        }

        root.setCenter(mainContent);

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
        icon.setStyle("-fx-font-size: 28px; -fx-text-fill: " + StyleHelper.ACCENT_GREEN + ";");

        Text titleText = new Text(title);
        titleText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleText.setStyle("-fx-fill: white;");

        Text subtitle = new Text("Payment & Earnings Management");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setStyle("-fx-fill: #bdc3c7;");

        VBox textBox = new VBox(2, titleText, subtitle);
        textBox.setAlignment(Pos.CENTER_LEFT);

        titleBox.getChildren().addAll(icon, textBox);
        header.getChildren().add(titleBox);

        return header;
    }

    private static VBox getBorrowerPayoutView() {
        VBox view = new VBox(20);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("💳");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Loan Repayment Center");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("Manage your loan repayments");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        VBox content = getLoanRepaymentPanel();
        content.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        view.getChildren().addAll(header, content);
        return view;
    }

    private static VBox getInvestorPayoutView() {
        VBox view = new VBox(20);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("📈");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Investment Earnings");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("View your investment returns and payouts");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);

        VBox content = getInvestorPayoutsPanel();
        content.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        view.getChildren().addAll(header, content);
        return view;
    }


    private static VBox getDefaultView() {
        VBox view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setAlignment(Pos.CENTER);
        view.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label icon = new Label("🔒");
        icon.setStyle("-fx-font-size: 64px;");

        Label title = new Label("Access Restricted");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        Label message = new Label("Payout system is only available for:\n• Borrowers (Loan Repayment)\n• Investors (Earnings View)\n• Administrators (Platform Earnings)");
        message.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");

        view.getChildren().addAll(icon, title, message);
        return view;
    }

    private static VBox getLoanRepaymentPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);

        Text title = new Text("Active Loans for Repayment");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        // Get active loans
        List<LoanSummary> activeLoans = Server.getActiveLoans(Main.userId);

        if (activeLoans.isEmpty()) {
            VBox noLoansBox = new VBox(15);
            noLoansBox.setAlignment(Pos.CENTER);

            Label icon = new Label("✅");
            icon.setStyle("-fx-font-size: 48px;");

            Label message = new Label("You have no active loans to repay.\nAll your loans are either paid or pending approval.");
            message.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");

            noLoansBox.getChildren().addAll(icon, message);
            panel.getChildren().addAll(title, noLoansBox);
            return panel;
        }

        // Create loan cards
        VBox loansContainer = new VBox(15);

        for (LoanSummary loan : activeLoans) {
            VBox loanCard = new VBox(10);
            loanCard.setPadding(new Insets(15));
            loanCard.setStyle(
                    "-fx-background-color: #f8f9fa; " +
                            "-fx-background-radius: 8; " +
                            "-fx-border-color: #e0e0e0; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 8;"
            );

            HBox header = new HBox(10);
            header.setAlignment(Pos.CENTER_LEFT);

            Label loanIcon = new Label("📋");
            loanIcon.setStyle("-fx-font-size: 24px;");

            VBox loanInfo = new VBox(5);

            Label loanId = new Label("Loan #" + loan.getLoanId());
            loanId.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 16px; -fx-font-weight: bold;");

            Label amountLabel = new Label("Amount: $" + loan.getAmount());
            amountLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

            Label interestLabel = new Label("Interest Rate: " + loan.getInterestRate() + "%");
            interestLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

            Label dueLabel = new Label("Due Date: " + loan.getDueDate());
            dueLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

            loanInfo.getChildren().addAll(loanId, amountLabel, interestLabel, dueLabel);
            header.getChildren().addAll(loanIcon, loanInfo);

            Button repayBtn = StyleHelper.createStyledButton("💳 Make Repayment", StyleHelper.ACCENT_GREEN);
            repayBtn.setOnAction(e -> showRepaymentDialog(loan));

            loanCard.getChildren().addAll(header, repayBtn);
            loansContainer.getChildren().add(loanCard);
        }

        panel.getChildren().addAll(title, loansContainer);
        return panel;
    }

    private static void showRepaymentDialog(LoanSummary loan) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Repay Loan #" + loan.getLoanId());

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12;"
        );

        Label icon = new Label("💳");
        icon.setStyle("-fx-font-size: 48px;");

        Label title = new Label("Repay Loan #" + loan.getLoanId());
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        VBox loanDetails = new VBox(10);
        loanDetails.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 15;"
        );

        double totalWithInterest = loan.getAmount() * (1 + loan.getInterestRate() / 100);

        Label amountLabel = new Label("Loan Amount: $" + loan.getAmount());
        amountLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        Label interestLabel = new Label("Interest Rate: " + loan.getInterestRate() + "%");
        interestLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        Label totalLabel = new Label("Total with Interest: $" + String.format("%.2f", totalWithInterest));
        totalLabel.setStyle("-fx-text-fill: " + StyleHelper.ACCENT_GREEN + "; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label dueLabel = new Label("Due Date: " + loan.getDueDate());
        dueLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        loanDetails.getChildren().addAll(amountLabel, interestLabel, totalLabel, dueLabel);

        VBox form = new VBox(15);

        Label amountPrompt = new Label("Repayment Amount:");
        amountPrompt.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to repay");
        amountField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px;"
        );

        Label passwordPrompt = new Label("Confirm Password:");
        passwordPrompt.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px;"
        );

        form.getChildren().addAll(amountPrompt, amountField, passwordPrompt, passwordField);

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);

        Button submitBtn = StyleHelper.createStyledButton("✅ Submit Repayment", StyleHelper.ACCENT_GREEN);
        Button cancelBtn = StyleHelper.createStyledButton("❌ Cancel", StyleHelper.ACCENT_RED);

        submitBtn.setOnAction(e -> {
            try {
                int amount = Integer.parseInt(amountField.getText());
                String password = passwordField.getText();

                if (Server.logIn(Server.getUserEmail(Main.userId), password)) {
                    Server.processLoanRepayment(loan.getLoanId(), amount);
                    showSuccessAlert("Repayment Successful",
                            "Repayment of $" + amount + " processed successfully!\n" +
                                    "Thank you for your payment.");
                    dialog.close();
                } else {
                    passwordField.setStyle(
                            "-fx-background-color: white; " +
                                    "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                    "-fx-border-radius: 4; " +
                                    "-fx-padding: 10; " +
                                    "-fx-font-size: 14px;"
                    );
                }
            } catch (NumberFormatException ex) {
                amountField.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: " + StyleHelper.ACCENT_RED + "; " +
                                "-fx-border-radius: 4; " +
                                "-fx-padding: 10; " +
                                "-fx-font-size: 14px;"
                );
            }
        });

        cancelBtn.setOnAction(e -> dialog.close());

        buttons.getChildren().addAll(submitBtn, cancelBtn);

        content.getChildren().addAll(icon, title, loanDetails, form, buttons);

        Scene scene = new Scene(content, 450, 550);
        dialog.setScene(scene);
        dialog.show();
    }

    private static VBox getInvestorPayoutsPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(20));

        Text title = new Text("Your Investment Earnings");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        // Get payout history
        List<InvestmentSummary> payouts = Server.getInvestorPayouts(Main.userId);

        if (payouts.isEmpty()) {
            VBox noPayoutsBox = new VBox(15);
            noPayoutsBox.setAlignment(Pos.CENTER);

            Label icon = new Label("📊");
            icon.setStyle("-fx-font-size: 48px;");

            Label message = new Label("No earnings yet.\nYour earnings will appear here once loans start repaying.");
            message.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");

            noPayoutsBox.getChildren().addAll(icon, message);
            panel.getChildren().addAll(title, noPayoutsBox);
            return panel;
        }

        // Create table
        TableView<InvestmentSummary> table = new TableView<>();
        ObservableList<InvestmentSummary> data = FXCollections.observableArrayList(payouts);

        table.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8;"
        );

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-border-color: " + StyleHelper.BORDER_COLOR + ";";

        TableColumn<InvestmentSummary, Integer> loanIdCol = new TableColumn<>("Loan ID");
        loanIdCol.setStyle(headerStyle);
        loanIdCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getLoanId()).asObject());

        TableColumn<InvestmentSummary, String> principalCol = new TableColumn<>("Principal");
        principalCol.setStyle(headerStyle);
        principalCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("$%.2f", cellData.getValue().getPrincipalShare())));

        TableColumn<InvestmentSummary, String> interestCol = new TableColumn<>("Interest Earned");
        interestCol.setStyle(headerStyle);
        interestCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("$%.2f", cellData.getValue().getInterestEarned())));
        interestCol.setCellFactory(col -> new TableCell<InvestmentSummary, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-text-fill: " + StyleHelper.ACCENT_GREEN + "; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<InvestmentSummary, String> dateCol = new TableColumn<>("Payment Date");
        dateCol.setStyle(headerStyle);
        dateCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getPaidAt().toString()));

        table.getColumns().addAll(loanIdCol, principalCol, interestCol, dateCol);
        table.setItems(data);
        table.setPrefHeight(300);

        // Calculate totals
        float totalInterest = 0;
        float totalPrincipal = 0;
        for (InvestmentSummary payout : payouts) {
            totalInterest += payout.getInterestEarned();
            totalPrincipal += payout.getPrincipalShare();
        }

        VBox summaryBox = new VBox(10);
        summaryBox.setPadding(new Insets(15));
        summaryBox.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #e0e0e0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 8;"
        );

        Label summaryTitle = new Label("Earnings Summary");
        summaryTitle.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label totalInvestedLabel = new Label("Total Principal Invested: $" + String.format("%.2f", totalPrincipal));
        totalInvestedLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        Label totalEarnedLabel = new Label("Total Interest Earned: $" + String.format("%.2f", totalInterest));
        totalEarnedLabel.setStyle("-fx-text-fill: " + StyleHelper.ACCENT_GREEN + "; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label countLabel = new Label("Total Payouts: " + payouts.size());
        countLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        summaryBox.getChildren().addAll(summaryTitle, totalInvestedLabel, totalEarnedLabel, countLabel);

        panel.getChildren().addAll(title, table, summaryBox);

        return panel;
    }

    private static VBox getPlatformEarningsPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);

        Text title = new Text("Platform Earnings Overview");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        VBox infoBox = new VBox(15);
        infoBox.setAlignment(Pos.CENTER);

        Label icon = new Label("🏢");
        icon.setStyle("-fx-font-size: 64px;");

        Label infoLabel = new Label(
                "Platform earnings are automatically calculated from:\n\n" +
                        "• 25% of all loan interest payments\n" +
                        "• System-generated revenue\n" +
                        "• Transaction fees (if applicable)\n\n" +
                        "All platform earnings are stored in the system wallet\n" +
                        "and can be viewed in the Admin Block dashboard."
        );
        infoLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");


        infoBox.getChildren().addAll(icon, infoLabel);
        panel.getChildren().addAll(title, infoBox);

        return panel;
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
}