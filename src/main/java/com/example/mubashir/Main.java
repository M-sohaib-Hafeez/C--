package com.example.mubashir;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static BorderPane root;
    private static BorderPane topBar;
    private static BorderPane homePane;
    private static Stage stage;
    public static int userId = 0;
    public static String userRole;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Loan Management System");

        setHomePane();
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + StyleHelper.LIGHT_BG + ";");
        root.setTop(getTopBar());
        root.setCenter(homePane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private static BorderPane getTopBar() {
        topBar = new BorderPane();
        topBar.setPadding(new Insets(10));
        topBar.setStyle(
                "-fx-background-color: linear-gradient(to right, " + StyleHelper.PRIMARY_COLOR + ", " + StyleHelper.SECONDARY_COLOR + "); " +
                        "-fx-border-color: transparent transparent " + StyleHelper.BORDER_COLOR + " transparent; " +
                        "-fx-border-width: 0 0 1px 0;"
        );

        HBox leftButtons = new HBox(10);
        leftButtons.setPadding(new Insets(5));
        leftButtons.setAlignment(Pos.CENTER_LEFT);

        Button transactionButton = createTopBarButton("💱 Transaction", StyleHelper.ACCENT_BLUE);
        Button payoutButton = createTopBarButton("💰 Payout System", StyleHelper.ACCENT_GREEN);
        Button back = createTopBarButton("← Back", StyleHelper.ACCENT_ORANGE);

        back.setOnAction(event -> {
            back();
            topBar.setLeft(leftButtons);
        });

        transactionButton.setOnAction(event -> {
            if (userId > 0) {
                root.setCenter(Transaction.transactionPane());
                topBar.setLeft(back);
                updateTopBarTitle("💱 Transaction Portal");
            } else {
                showMessage("Please login first!");
            }
        });

        payoutButton.setOnAction(event -> {
            if (userId > 0) {
                root.setCenter(PayoutSystem.getPayoutDashboard());
                topBar.setLeft(back);
                updateTopBarTitle("💰 Payout System");
            } else {
                showMessage("Please login first!");
            }
        });

        leftButtons.getChildren().addAll(transactionButton, payoutButton);
        topBar.setLeft(leftButtons);

        // Right side (login/signup)
        setRightSideOfTopBar();

        // Center title
        updateTopBarTitle("🏦 Loan Management System");

        return topBar;
    }

    private static Button createTopBarButton(String text, String color) {
        Button button = new Button(text);
        button.setPadding(new Insets(8, 15, 8, 15));
        button.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: " + StyleHelper.darkenColor(color) + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-cursor: hand;"
        ));

        return button;
    }

    private static void updateTopBarTitle(String titleText) {
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER);

        Label icon = new Label(titleText.split(" ")[0]);
        icon.setStyle("-fx-font-size: 24px;");

        Text title = new Text(titleText);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-fill: white;");

        titleBox.getChildren().addAll(icon, title);
        topBar.setCenter(titleBox);
    }

    private static void setHomePane() {
        homePane = new BorderPane();
        homePane.setStyle("-fx-background-color: " + StyleHelper.LIGHT_BG + ";");

        VBox centerPane = new VBox(30);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(40));

        VBox welcomeSection = new VBox(10);
        welcomeSection.setAlignment(Pos.CENTER);
        welcomeSection.setPadding(new Insets(20));
        welcomeSection.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label welcomeIcon = new Label("🏦");
        welcomeIcon.setStyle("-fx-font-size: 64px;");

        Text welcome = new Text("Welcome to Loan Management System");
        welcome.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        welcome.setStyle("-fx-fill: " + StyleHelper.TEXT_DARK + ";");

        Text subtitle = new Text("Your trusted platform for loans and investments");
        subtitle.setFont(Font.font("Segoe UI", 16));
        subtitle.setStyle("-fx-fill: #666;");

        welcomeSection.getChildren().addAll(welcomeIcon, welcome, subtitle);

        VBox buttonsSection = new VBox(20);
        buttonsSection.setAlignment(Pos.CENTER);
        buttonsSection.setPadding(new Insets(20));
        buttonsSection.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label sectionTitle = new Label("Choose Your Path");
        sectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        sectionTitle.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        GridPane buttonsGrid = new GridPane();
        buttonsGrid.setHgap(20);
        buttonsGrid.setVgap(20);
        buttonsGrid.setAlignment(Pos.CENTER);

        Button investButton = getButton("📈 Investment",
                "Invest and earn returns", StyleHelper.ACCENT_GREEN);
        Button borrowButton = getButton("💰 Borrow",
                "Get loans for your needs", StyleHelper.ACCENT_BLUE);

        buttonsGrid.add(investButton, 1, 0);
        buttonsGrid.add(borrowButton, 2, 0);

        if (userId > 0) {
            VBox statsSection = new VBox(15);
            statsSection.setAlignment(Pos.CENTER);
            statsSection.setPadding(new Insets(20));
            statsSection.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-background-radius: 12; " +
                            "-fx-border-radius: 12; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                            "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                            "-fx-border-width: 1px;"
            );

            Label statsTitle = new Label("Your Dashboard");
            statsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
            statsTitle.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

            HBox statsGrid = new HBox(20);
            statsGrid.setAlignment(Pos.CENTER);

            double balance = Server.getWalletAmount(userId);
            String welcomeName = Server.getBasicInfo(userId).getName();

            VBox balanceCard = createStatCard("💰 Balance",
                    String.format("$%.2f", balance),
                    balance > 0 ? StyleHelper.ACCENT_GREEN : StyleHelper.ACCENT_RED);

            VBox roleCard = createStatCard("👤 Role", userRole, StyleHelper.ACCENT_BLUE);

            VBox welcomeCard = createStatCard("👋 Welcome", welcomeName, StyleHelper.ACCENT_PURPLE);

            statsGrid.getChildren().addAll(balanceCard, roleCard, welcomeCard);
            statsSection.getChildren().addAll(statsTitle, statsGrid);
            buttonsSection.getChildren().addAll(sectionTitle, buttonsGrid, statsSection);
        } else {
            buttonsSection.getChildren().addAll(sectionTitle, buttonsGrid);
        }

        centerPane.getChildren().addAll(welcomeSection, buttonsSection);
        homePane.setCenter(centerPane);
    }

    private static VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setPrefSize(150, 100);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px; -fx-font-weight: 600;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle(String.format(
                "-fx-text-fill: %s; -fx-font-size: 16px; -fx-font-weight: bold;",
                color
        ));

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private static Button getButton(String buttonName, String description, String color) {
        Button button = new Button();
        button.setPrefSize(200, 120);

        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);

        String[] parts = buttonName.split(" ", 2);
        String icon = parts[0];
        String text = parts.length > 1 ? parts[1] : "";

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 32px;");

        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px; -fx-alignment: center; -fx-wrap-text: true;");

        content.getChildren().addAll(iconLabel, textLabel, descLabel);
        button.setGraphic(content);

        button.setStyle(String.format(
                "-fx-background-color: white; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 2px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3);",
                StyleHelper.TEXT_DARK, color
        ));

        button.setOnMouseEntered(e -> button.setStyle(String.format(
                "-fx-background-color: %s15; " + // 15 = 0.09 opacity
                        "-fx-text-fill: %s; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 2px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);",
                color, StyleHelper.TEXT_DARK, color
        )));

        button.setOnMouseExited(e -> button.setStyle(String.format(
                "-fx-background-color: white; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 2px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3);",
                StyleHelper.TEXT_DARK, color
        )));

        button.setOnAction(event -> {
            if (!isLoggedIn()) {
                showMessage("Please login first before accessing " + text + "!");
                return;
            }

            String requiredRole = getRequiredRole(text);
            if (!userRole.equalsIgnoreCase(requiredRole)) {
                showMessage("Access Denied",
                        "Only " + requiredRole + "s can access " + text + ".");
                return;
            }

            switch (text) {
                case "Borrow":
                    root.setCenter(Borrow.borrowPane());
                    updateTopBarTitle("💰 Borrow Dashboard");
                    break;
                case "Investment":
                    root.setCenter(Investment.investmentPane());
                    updateTopBarTitle("📈 Investment Dashboard");
                    break;
            }
        });

        return button;
    }

    private static String getRequiredRole(String buttonName) {
        switch (buttonName) {
            case "Borrow": return "Borrower";
            case "Investment": return "Investor";
            default: return "";
        }
    }

    private static boolean isLoggedIn() {
        return userId != 0;
    }

    public static void setRightSideOfTopBar() {
        HBox rightSide = new HBox(10);
        rightSide.setPadding(new Insets(5));
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        if (userId == 0) {
            Button loginButton = createTopBarButton("🔐 Login", StyleHelper.ACCENT_GREEN);
            Button signupButton = createTopBarButton("👤 Sign Up", StyleHelper.ACCENT_BLUE);

            loginButton.setOnAction(e -> LogIn.logInStage().show());
            signupButton.setOnAction(e -> SignIn.signInStage().show());

            rightSide.getChildren().addAll(loginButton, signupButton);
        } else {
            HBox userBox = new HBox(10);
            userBox.setAlignment(Pos.CENTER_RIGHT);
            userBox.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.1); " +
                            "-fx-padding: 5 15; " +
                            "-fx-background-radius: 20;"
            );

            Label userIcon = new Label("👤");
            userIcon.setStyle("-fx-font-size: 18px;");

            VBox userInfo = new VBox(2);
            Label userName = new Label(Server.getBasicInfo(userId).getName());
            userName.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");

            Label userRoleLabel = new Label(userRole);
            userRoleLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 10px;");

            userInfo.getChildren().addAll(userName, userRoleLabel);

            MenuButton accountMenu = new MenuButton();
            accountMenu.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-cursor: hand;"
            );

            Label menuIcon = new Label("▾");
            menuIcon.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            accountMenu.setGraphic(menuIcon);

            MenuItem profileItem = new MenuItem("👤 Profile");
            MenuItem logoutItem = new MenuItem("🚪 Logout");

            profileItem.setOnAction(e -> showProfile());
            logoutItem.setOnAction(e -> {
                userId = 0;
                userRole = null;
                setRightSideOfTopBar();
                back();
            });

            accountMenu.getItems().addAll(profileItem, logoutItem);

            userBox.getChildren().addAll(userInfo, userIcon, accountMenu);
            rightSide.getChildren().add(userBox);
        }

        topBar.setRight(rightSide);
    }

    private static void showProfile() {
        Stage profileStage = new Stage();
        profileStage.initModality(Modality.APPLICATION_MODAL);
        profileStage.setTitle("User Profile");

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12;"
        );

        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 64px;");

        BasicInfo info = Server.getBasicInfo(userId);
        double balance = Server.getWalletAmount(userId);

        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 15;"
        );

        Label nameLabel = createProfileField("Name:", info.getName());
        Label emailLabel = createProfileField("Email:", info.getEmail());
        Label roleLabel = createProfileField("Role:", userRole);
        Label balanceLabel = createProfileField("Balance:", String.format("$%.2f", balance));
        Label addressLabel = createProfileField("Address:", info.getAddress());
        Label phoneLabel = createProfileField("Phone:", info.getPhoneNumber());

        infoBox.getChildren().addAll(nameLabel, emailLabel, roleLabel,
                balanceLabel, addressLabel, phoneLabel);

        Button closeBtn = StyleHelper.createStyledButton("Close", StyleHelper.ACCENT_BLUE);
        closeBtn.setOnAction(e -> profileStage.close());

        content.getChildren().addAll(icon, infoBox, closeBtn);

        Scene scene = new Scene(content, 400, 500);
        profileStage.setScene(scene);
        profileStage.show();
    }

    private static Label createProfileField(String label, String value) {
        HBox field = new HBox(10);
        field.setAlignment(Pos.CENTER_LEFT);

        Label labelComp = new Label(label);
        labelComp.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-weight: bold; -fx-min-width: 80;");

        Label valueComp = new Label(value);
        valueComp.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        field.getChildren().addAll(labelComp, valueComp);
        return new Label("", field);
    }

    public static void back() {
        root.setCenter(homePane);
        updateTopBarTitle("🏦 Loan Management System");
        setRightSideOfTopBar();
    }

    private static void showMessage(String message) {
        showMessage("Information", message);
    }

    private static void showMessage(String title, String message) {
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

        Label icon = new Label(title.equals("Access Denied") ? "❌" : "ℹ️");
        icon.setStyle("-fx-font-size: 48px;");

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + "; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");

        Button ok = StyleHelper.createStyledButton("OK",
                title.equals("Access Denied") ? StyleHelper.ACCENT_RED : StyleHelper.ACCENT_BLUE);
        ok.setOnAction(e -> alert.close());

        pane.getChildren().addAll(icon, label, ok);

        Scene scene = new Scene(pane, 350, 200);
        alert.setScene(scene);
        alert.show();
    }
}