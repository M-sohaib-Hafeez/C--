package Admin;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AdminBlock extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/loan_management_system";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "{(Dexter_Mimikyu_21)}";

    private BorderPane root;
    private BorderPane centerPane;
    private VBox sideBar;

    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final ObservableList<Loan> loans = FXCollections.observableArrayList();
    private final ObservableList<PoolContribution> contributions = FXCollections.observableArrayList();
    private final ObservableList<ContributionBalance> contributionBalances = FXCollections.observableArrayList();
    private final ObservableList<TransactionRow> transactions = FXCollections.observableArrayList();
    private final ObservableList<AuditLog> auditLogs = FXCollections.observableArrayList();
    private final ObservableList<Setting> settings = FXCollections.observableArrayList();
    private final ObservableList<LoanRepayment> repayments = FXCollections.observableArrayList();

    private TableView<?> currentTable;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DatabaseService.initialize(DB_URL, DB_USER, DB_PASS);
            System.out.println("Database initialized successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            System.exit(1);
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Admin Block - Loan Management System v2.0");

        root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");
        root.setTop(buildTopBar());
        centerPane = new BorderPane();
        centerPane.setPadding(new Insets(15));
        root.setCenter(centerPane);

        sideBar = buildSideBar();
        root.setLeft(sideBar);

        setHomeDashboard();

        Scene scene = new Scene(root, 1400, 900);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        // Initial data load
        refreshAll();
    }

    private BorderPane buildTopBar() {
        BorderPane top = new BorderPane();
        top.setPadding(new Insets(15));
        top.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50, #34495e); " +
                "-fx-border-color: transparent transparent #e0e0e0 transparent; " +
                "-fx-border-width: 0 0 1px 0;");

        HBox leftBox = new HBox(15);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("🏦");
        icon.setStyle("-fx-font-size: 28px; -fx-text-fill: #3498db;");

        Text title = new Text("Admin Block");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: white;");

        Text subtitle = new Text("Loan Management System v2.0");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setStyle("-fx-fill: #bdc3c7;");

        VBox titleText = new VBox(2, title, subtitle);
        titleText.setAlignment(Pos.CENTER_LEFT);

        titleBox.getChildren().addAll(icon, titleText);
        leftBox.getChildren().add(titleBox);

        top.setLeft(leftBox);

        HBox right = new HBox(12);
        right.setAlignment(Pos.CENTER_RIGHT);

        Button refresh = createStyledButton("🔄 Refresh", "#3498db");
        refresh.setOnAction(e -> refreshAll());

        Button export = createStyledButton("📤 Export Table", "#2ecc71");
        export.setOnAction(e -> exportCurrentTable());

        Button dbCheck = createStyledButton("🔍 DB Status", "#9b59b6");
        dbCheck.setOnAction(e -> checkDatabaseConnection());

        // User info
        HBox userBox = new HBox(10);
        userBox.setAlignment(Pos.CENTER_RIGHT);
        userBox.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-padding: 8 15; -fx-background-radius: 20;");

        Label userIcon = new Label("👤");
        userIcon.setStyle("-fx-font-size: 18px;");

        VBox userInfo = new VBox(2);
        Label userName = new Label("Administrator");
        userName.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");
        Label userRole = new Label("System Admin");
        userRole.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 10px;");
        userInfo.getChildren().addAll(userName, userRole);

        userBox.getChildren().addAll(userInfo, userIcon);

        right.getChildren().addAll(refresh, export, dbCheck, userBox);
        top.setRight(right);
        return top;
    }

    private VBox buildSideBar() {
        VBox v = new VBox(3);
        v.setPadding(new Insets(20, 15, 20, 15));
        v.setPrefWidth(220);
        v.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e); " +
                "-fx-border-color: transparent #e0e0e0 transparent transparent; " +
                "-fx-border-width: 0 1px 0 0;");

        Label sidebarHeader = new Label("MAIN NAVIGATION");
        sidebarHeader.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 15 5 10 5;");
        v.getChildren().add(sidebarHeader);

        Button[] menuItems = {
                createMenuButton("📊 Dashboard", e -> setHomeDashboard()),
                createMenuButton("👥 Users", e -> setUsersPane()),
                createMenuButton("💰 Investments", e -> setInvestmentsPane()),
                createMenuButton("💳 Loans", e -> setLoansPane()),
                createMenuButton("💱 Transactions", e -> setTransactionsPane()),
                createMenuButton("📝 Audit Log", e -> setAuditPane()),
                createMenuButton("⚙️ Settings", e -> setSettingsPane()),
                createMenuButton("💼 Wallets", e -> showWalletManagement())
        };

        v.getChildren().addAll(menuItems);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        v.getChildren().add(spacer);

        // Logout button
        Button logout = createMenuButton("🚪 Logout", e -> System.exit(0));
        logout.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 8; " +
                "-fx-padding: 10 15;");
        logout.setOnMouseEntered(e -> logout.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 8; " +
                "-fx-padding: 10 15;"));
        logout.setOnMouseExited(e -> logout.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 8; " +
                "-fx-padding: 10 15;"));
        v.getChildren().add(logout);

        return v;
    }

    private Button createMenuButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 20, 12, 20));
        btn.setGraphicTextGap(15);
        btn.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #ecf0f1; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 8; " +
                "-fx-border-radius: 8; " +
                "-fx-cursor: hand;");
        btn.setOnAction(handler);

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: rgba(255,255,255,0.1); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 8; " +
                "-fx-border-radius: 8; " +
                "-fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #ecf0f1; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 8; " +
                "-fx-border-radius: 8; " +
                "-fx-cursor: hand;"));
        return btn;
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPadding(new Insets(8, 16, 8, 16));
        btn.setStyle(String.format("-fx-background-color: %s; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12px; " +
                "-fx-background-radius: 6; " +
                "-fx-border-radius: 6; " +
                "-fx-cursor: hand;", color));

        btn.setOnMouseEntered(e -> btn.setStyle(String.format("-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);",
                darkenColor(color))));
        btn.setOnMouseExited(e -> btn.setStyle(String.format("-fx-background-color: %s; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12px; " +
                "-fx-background-radius: 6; " +
                "-fx-border-radius: 6; " +
                "-fx-cursor: hand;", color)));
        return btn;
    }

    private String darkenColor(String hex) {
        try {
            if (hex.startsWith("#") && hex.length() == 7) {
                int r = Integer.parseInt(hex.substring(1, 3), 16);
                int g = Integer.parseInt(hex.substring(3, 5), 16);
                int b = Integer.parseInt(hex.substring(5, 7), 16);
                r = Math.max(0, r - 30);
                g = Math.max(0, g - 30);
                b = Math.max(0, b - 30);
                return String.format("#%02x%02x%02x", r, g, b);
            }
        } catch (Exception e) {
        }
        return hex;
    }

    private void setHomeDashboard() {
        BorderPane dash = new BorderPane();
        dash.setPadding(new Insets(15));

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Text headerText = new Text("Dashboard Overview");
        headerText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        headerText.setStyle("-fx-fill: #2c3e50;");

        Label statsIcon = new Label("📈");
        statsIcon.setStyle("-fx-font-size: 28px; -fx-padding: 0 15 0 0;");

        header.getChildren().addAll(statsIcon, headerText);
        dash.setTop(header);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));

        Label usersCard = makeCard("👥 Users", "#3498db", () -> (Number)DatabaseService.count("users"));
        Label investorsCard = makeCard("💰 Investors", "#2ecc71", () -> (Number)DatabaseService.countByRole("Investor"));
        Label borrowersCard = makeCard("👨‍💼 Borrowers", "#e74c3c", () -> (Number)DatabaseService.countByRole("Borrower"));
        Label loansCard = makeCard("💳 Loans", "#f39c12", () -> (Number)DatabaseService.count("loans"));
        Label activeLoansCard = makeCard("📊 Active Loans", "#1abc9c", () -> (Number)DatabaseService.countCondition("loans", "status='Active'"));
        Label contributionsCard = makeCard("🏦 Total Contributions", "#34495e", () -> (Number)DatabaseService.sum("Pool_Contribution", "amount"));
        Label totalWallet = makeCard("💼 Platform Wallet", "#16a085", () -> (Number)DatabaseService.platformWallet());
        Label pendingLoans = makeCard("⏳ Pending Loans", "#d35400", () -> (Number)DatabaseService.countCondition("loans", "status='Pending' or status IS NULL"));

        grid.add(usersCard, 0, 0);
        grid.add(investorsCard, 1, 0);
        grid.add(borrowersCard, 2, 0);
        grid.add(loansCard, 0, 1);
        grid.add(activeLoansCard, 1, 1);
        grid.add(contributionsCard, 2, 1);
        grid.add(totalWallet, 0, 2);
        grid.add(pendingLoans, 1, 2);

        dash.setCenter(grid);
        centerPane.setCenter(dash);
    }

    private Label makeCard(String title, String color, SupplierWithException<Number> supplier) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefSize(280, 120);
        card.setStyle("-fx-background-color: white; " +
                "-fx-background-radius: 12; " +
                "-fx-border-radius: 12; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                "-fx-border-color: #e0e0e0; " +
                "-fx-border-width: 1px;");

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        String[] parts = title.split(" ", 2);
        String icon = parts[0];
        String text = parts.length > 1 ? parts[1] : "";

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");

        Label titleLabel = new Label(text);
        titleLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-font-weight: 600;");

        titleBox.getChildren().addAll(iconLabel, titleLabel);

        Label valueLabel = new Label("loading...");
        valueLabel.setStyle(String.format("-fx-text-fill: %s; -fx-font-size: 28px; -fx-font-weight: bold;", color));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(titleBox, valueLabel, spacer);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Number value = supplier.get();
                    String displayText;
                    if (value == null) {
                        displayText = "0";
                    } else if (value.doubleValue() >= 1000) {
                        displayText = String.format("%,.0f", value.doubleValue());
                    } else if (value.doubleValue() == (int)value.doubleValue()) {
                        displayText = String.format("%,d", value.intValue());
                    } else {
                        displayText = String.format("%,.2f", value.doubleValue());
                    }

                    if (title.contains("PKR") || title.contains("Balance") || title.contains("Contribution")){
                        displayText = "₨ " + displayText;
                    }

                    final String finalText = displayText;
                    Platform.runLater(() -> valueLabel.setText(finalText));
                } catch (Exception ex) {
                    Platform.runLater(() -> valueLabel.setText("error"));
                    ex.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();

        StackPane container = new StackPane(card);
        container.setPadding(new Insets(5));
        return new Label("", container);
    }

    private void setUsersPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("👥");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("User Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("Manage system users and permissions");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TableView<User> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0;";

        TableColumn<User, Number> idCol = new TableColumn<>("ID");
        idCol.setStyle(headerStyle);
        idCol.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().userId));

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setStyle(headerStyle);
        nameCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().fullName));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setStyle(headerStyle);
        roleCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().userRole));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setStyle(headerStyle);
        emailCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().email));

        TableColumn<User, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setStyle(headerStyle);
        phoneCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().phone));

        TableColumn<User, Boolean> kycCol = new TableColumn<>("KYC Verified");
        kycCol.setStyle(headerStyle);
        kycCol.setCellValueFactory(cd -> new SimpleBooleanProperty(cd.getValue().kycVerified));
        kycCol.setCellFactory(tc -> new CheckBoxTableCell<>());

        TableColumn<User, String> walletCol = new TableColumn<>("Wallet Balance");
        walletCol.setStyle(headerStyle);
        walletCol.setCellValueFactory(cd -> {
            Double balance = DatabaseService.getWalletBalance(cd.getValue().userId);
            return new SimpleStringProperty(balance != null ? String.format("₨ %,.2f", balance) : "₨ 0.00");
        });

        table.getColumns().addAll(idCol, nameCol, roleCol, emailCol, phoneCol, kycCol, walletCol);

        table.setItems(users);

        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(15, 0, 15, 0));
        controls.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");

        Button refresh = createStyledButton("🔄 Refresh", "#3498db");
        refresh.setOnAction(e -> loadUsers());

        ComboBox<String> roleFilter = new ComboBox<>();
        roleFilter.getItems().addAll("All", "Investor", "Donor", "Borrower");
        roleFilter.setValue("All");
        roleFilter.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-padding: 6 12;");
        roleFilter.valueProperty().addListener((obs, o, n) -> loadUsersFiltered(n));

        Button approveKyc = createStyledButton("✅ Toggle KYC", "#f39c12");
        approveKyc.setOnAction(e -> {
            User u = table.getSelectionModel().getSelectedItem();
            if (u == null) { alert("Select user"); return; }
            boolean newKyc = !u.kycVerified;
            if (DatabaseService.updateKyc(u.userId, newKyc)) {
                DatabaseService.writeAudit(0, "KYC set " + newKyc + " for user " + u.userId);
                loadUsers();
                alert("KYC status updated for " + u.fullName);
            } else alert("Failed to update KYC");
        });

        Button deleteUser = createStyledButton("🗑️ Delete User", "#e74c3c");
        deleteUser.setOnAction(e -> {
            User u = table.getSelectionModel().getSelectedItem();
            if (u == null) { alert("Select user"); return; }
            if (confirm("Delete user " + u.userId + " (" + u.fullName + ")" + " ?")) {
                if (DatabaseService.deleteUser(u.userId)) {
                    DatabaseService.writeAudit(0, "Deleted user " + u.userId);
                    loadUsers();
                    alert("User deleted successfully");
                } else alert("Delete failed");
            }
        });

        Button viewWallet = createStyledButton("💰 View Transactions", "#2ecc71");
        viewWallet.setOnAction(e -> {
            User u = table.getSelectionModel().getSelectedItem();
            if (u == null) { alert("Select user"); return; }
            setTransactionsPaneForUser(u.userId);
        });

        controls.getChildren().addAll(refresh, new Label("Filter:"), roleFilter, approveKyc, deleteUser, viewWallet);

        VBox container = new VBox(15, controls, table);
        pane.setCenter(container);

        centerPane.setCenter(pane);
        this.currentTable = table;
        loadUsers();
    }

    private void setInvestmentsPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("💰");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Investments Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("Manage pool contributions and balances");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TabPane tabs = new TabPane();
        tabs.setStyle("-fx-background-color: transparent; -fx-border-color: #e0e0e0; -fx-border-radius: 8;");
        tabs.setTabMinWidth(150);

        TableView<PoolContribution> tableContrib = new TableView<>();
        tableContrib.setStyle("-fx-background-color: white;");
        tableContrib.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<PoolContribution, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().contributionId));

        TableColumn<PoolContribution, Number> invCol = new TableColumn<>("Investor ID");
        invCol.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().investorId));

        TableColumn<PoolContribution, Number> amtCol = new TableColumn<>("Amount (PKR)");
        amtCol.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().amount));
        amtCol.setCellFactory(col -> new TableCell<PoolContribution, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("₨ %,.2f", item.doubleValue()));
                    setStyle("-fx-alignment: CENTER_RIGHT; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<PoolContribution, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().contributedAt.toString()));

        tableContrib.getColumns().addAll(idCol, invCol, amtCol, dateCol);
        tableContrib.setItems(contributions);

        HBox controls1 = new HBox(10);
        Button refresh1 = createStyledButton("🔄 Refresh", "#3498db");
        refresh1.setOnAction(e -> loadContributions());


        controls1.getChildren().addAll(refresh1);
        controls1.setPadding(new Insets(15));

        VBox tab1Content = new VBox(10, controls1, tableContrib);
        tab1Content.setPadding(new Insets(10));
        Tab t1 = new Tab("Contributions", tab1Content);
        t1.setClosable(false);

        TableView<ContributionBalance> tableBalances = new TableView<>();
        tableBalances.setStyle("-fx-background-color: white;");
        tableBalances.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ContributionBalance, Number> bid = new TableColumn<>("Contribution ID");
        bid.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().contributionId));

        TableColumn<ContributionBalance, Number> binv = new TableColumn<>("Investor ID");
        binv.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().investorId));

        TableColumn<ContributionBalance, Number> bContrib = new TableColumn<>("Contributed");
        bContrib.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().contributedAmount));
        bContrib.setCellFactory(col -> new TableCell<ContributionBalance, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("₨ %,.2f", item.doubleValue()));
            }
        });

        TableColumn<ContributionBalance, Number> bAlloc = new TableColumn<>("Allocated");
        bAlloc.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().amountAllocated));
        bAlloc.setCellFactory(col -> new TableCell<ContributionBalance, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("₨ %,.2f", item.doubleValue()));
            }
        });

        TableColumn<ContributionBalance, Number> bRemain = new TableColumn<>("Remaining");
        bRemain.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().remainingAmount));
        bRemain.setCellFactory(col -> new TableCell<ContributionBalance, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("₨ %,.2f", item.doubleValue()));
                    if (item.doubleValue() > 0) {
                        setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #e74c3c;");
                    }
                }
            }
        });

        TableColumn<ContributionBalance, String> bDate = new TableColumn<>("Contributed At");
        bDate.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().contributedAt.toString()));

        tableBalances.getColumns().addAll(bid, binv, bContrib, bAlloc, bRemain, bDate);
        tableBalances.setItems(contributionBalances);

        HBox controls2 = new HBox(10);
        Button refresh2 = createStyledButton("🔄 Refresh Balances", "#3498db");
        refresh2.setOnAction(e -> loadContributionBalances());
        controls2.getChildren().addAll(refresh2);
        controls2.setPadding(new Insets(15));

        VBox tab2Content = new VBox(10, controls2, tableBalances);
        tab2Content.setPadding(new Insets(10));
        Tab t2 = new Tab("Balance Overview", tab2Content);
        t2.setClosable(false);

        tabs.getTabs().addAll(t1, t2);
        pane.setCenter(tabs);

        centerPane.setCenter(pane);
        this.currentTable = tableContrib;

        loadContributions();
        loadContributionBalances();
    }

    private void setLoansPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("💳");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Loan Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("Approve, fund, and manage loans");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TableView<Loan> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0;";

        TableColumn<Loan, Number> id = new TableColumn<>("Loan ID");
        id.setStyle(headerStyle);
        id.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().loanId));

        TableColumn<Loan, Number> borrower = new TableColumn<>("Borrower ID");
        borrower.setStyle(headerStyle);
        borrower.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().borrowerId));

        TableColumn<Loan, Number> amount = new TableColumn<>("Amount (PKR)");
        amount.setStyle(headerStyle);
        amount.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().loanAmount));
        amount.setCellFactory(col -> new TableCell<Loan, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("₨ %,.2f", item.doubleValue()));
                    setStyle("-fx-alignment: CENTER_RIGHT; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<Loan, String> status = new TableColumn<>("Status");
        status.setStyle(headerStyle);
        status.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().status));
        status.setCellFactory(col -> new TableCell<Loan, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item.toLowerCase()) {
                        case "active":
                            setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-alignment: CENTER;");
                            break;
                        case "pending":
                            setStyle("-fx-text-fill: #f39c12; -fx-font-weight: bold; -fx-alignment: CENTER;");
                            break;
                        case "rejected":
                            setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER;");
                            break;
                        case "repaid":
                            setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold; -fx-alignment: CENTER;");
                            break;
                        default:
                            setStyle("-fx-alignment: CENTER;");
                    }
                }
            }
        });

        TableColumn<Loan, String> createdAt = new TableColumn<>("Created At");
        createdAt.setStyle(headerStyle);
        createdAt.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().createdAt == null ? "-" : cd.getValue().createdAt.toString()));

        TableColumn<Loan, String> dueDate = new TableColumn<>("Due Date");
        dueDate.setStyle(headerStyle);
        dueDate.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dueDate == null ? "-" : cd.getValue().dueDate.toString()));

        table.getColumns().addAll(id, borrower, amount, status, createdAt, dueDate);
        table.setItems(loans);

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(15));
        controls.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");

        Button refresh = createStyledButton("🔄 Refresh", "#3498db");
        refresh.setOnAction(e -> loadLoans());

        Button approve = createStyledButton("✅ Approve & Auto-Fund", "#2ecc71");
        approve.setOnAction(e -> {
            Loan l = table.getSelectionModel().getSelectedItem();
            if (l == null) { alert("Select loan"); return; }
            if (!confirm("Approve and auto-fund loan " + l.loanId + " for ₨ " + String.format("%,.2f", l.loanAmount) + "?")) return;

            Task<ApprovalResult> t = new Task<>() {
                @Override
                protected ApprovalResult call() throws Exception {
                    return DatabaseService.approveAndAutoFundLoan(l.loanId);
                }
            };
            t.setOnSucceeded(ev -> {
                ApprovalResult res = t.getValue();
                if (res.success) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("✅ Loan ").append(l.loanId).append(" approved successfully!\n\n");
                    sb.append("Amount funded: ₨ ").append(String.format("%,.2f", res.fundedAmount)).append("\n");
                    sb.append("Borrower wallet has been credited.\n\n");
                    sb.append("Funding allocations:\n");
                    for (AllocationDetail d : res.allocations) {
                        sb.append("  • Contribution ").append(d.contributionId)
                                .append(" → ₨ ").append(String.format("%,.2f", d.amountUsed)).append("\n");
                    }
                    if (res.remainingToFund > 0.0001) {
                        sb.append("\n⚠️ Loan partially funded. Remaining: ₨ ")
                                .append(String.format("%,.2f", res.remainingToFund))
                                .append("\nFund later when more contributions are available.");
                    }
                    showSuccessAlert("Loan Approved", sb.toString());
                    DatabaseService.writeAudit(0, "Approved & auto-funded loan " + l.loanId + " amount " + l.loanAmount);
                    loadLoans();
                    loadContributionBalances();
                    loadTransactions();
                } else {
                    showErrorAlert("Approval Failed", res.message);
                }
            });
            t.setOnFailed(ev -> showErrorAlert("Approval Failed", t.getException().getMessage()));
            new Thread(t).start();
        });

        Button markRepaid = createStyledButton("💰 Mark Repaid", "#f39c12");
        markRepaid.setOnAction(e -> {
            Loan l = table.getSelectionModel().getSelectedItem();
            if (l == null) { alert("Select loan"); return; }
            if (DatabaseService.markLoanRepaid(l.loanId)) {
                DatabaseService.writeAudit(0, "Marked loan repaid " + l.loanId);
                loadLoans();
                showSuccessAlert("Success", "Loan marked as repaid");
            } else showErrorAlert("Failed", "Could not mark loan as repaid");
        });

        Button viewRepayments = createStyledButton("📋 View Repayments", "#3498db");
        viewRepayments.setOnAction(e -> {
            Loan l = table.getSelectionModel().getSelectedItem();
            if (l == null) { alert("Select loan"); return; }
            List<LoanRepayment> rep = DatabaseService.loadRepaymentsForLoan(l.loanId);
            if (rep.isEmpty()) {
                alert("No repayments found for this loan");
            } else {
                StringBuilder sb = new StringBuilder("Repayments for Loan " + l.loanId + ":\n\n");
                for (LoanRepayment r : rep) sb.append("• ₨ ").append(String.format("%,.2f", r.amount))
                        .append(" at ").append(r.repaidAt).append("\n");
                alert(sb.toString());
            }
        });


        controls.getChildren().addAll(refresh, approve, markRepaid, viewRepayments);

        VBox container = new VBox(15, controls, table);
        pane.setCenter(container);
        centerPane.setCenter(pane);
        this.currentTable = table;

        loadLoans();
    }


    private void setTransactionsPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("💱");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Transaction History");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("View all system transactions");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TableView<TransactionRow> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0;";

        TableColumn<TransactionRow, Number> id = new TableColumn<>("ID");
        id.setStyle(headerStyle);
        id.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().transactionId));

        TableColumn<TransactionRow, Number> from = new TableColumn<>("From");
        from.setStyle(headerStyle);
        from.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().fromUserId == null ? 0 : cd.getValue().fromUserId));

        TableColumn<TransactionRow, Number> to = new TableColumn<>("To");
        to.setStyle(headerStyle);
        to.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().toUserId == null ? 0 : cd.getValue().toUserId));

        TableColumn<TransactionRow, String> type = new TableColumn<>("Type");
        type.setStyle(headerStyle);
        type.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().type));
        type.setCellFactory(col -> new TableCell<TransactionRow, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item) {
                        case "deposit":
                            setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                            break;
                        case "loan_disbursement":
                            setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold;");
                            break;
                        case "repayment":
                            setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                            break;
                        case "withdrawal":
                            setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });

        TableColumn<TransactionRow, Number> amount = new TableColumn<>("Amount (PKR)");
        amount.setStyle(headerStyle);
        amount.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().amount));
        amount.setCellFactory(col -> new TableCell<TransactionRow, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("₨ %,.2f", item.doubleValue()));
                    setStyle("-fx-alignment: CENTER_RIGHT; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<TransactionRow, String> created = new TableColumn<>("Created");
        created.setStyle(headerStyle);
        created.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().createdAt.toString()));

        table.getColumns().addAll(id, from, to, type, amount, created);
        table.setItems(transactions);

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(15));
        controls.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");

        Button refresh = createStyledButton("🔄 Refresh", "#3498db");
        refresh.setOnAction(e -> loadTransactions());

        ComboBox<String> typeFilter = new ComboBox<>();
        typeFilter.getItems().addAll("All", "deposit", "loan_disbursement", "repayment", "interest_payout", "platform_fee");
        typeFilter.setValue("All");
        typeFilter.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-padding: 6 12;");
        typeFilter.setOnAction(e -> {
            String sel = typeFilter.getValue();
            if (sel == null || sel.equals("All")) loadTransactions();
            else loadTransactionsByType(sel);
        });

        Label filterLabel = new Label("Filter by type:");
        filterLabel.setStyle("-fx-text-fill: #666; -fx-font-weight: bold;");

        controls.getChildren().addAll(refresh, filterLabel, typeFilter);

        VBox container = new VBox(15, controls, table);
        pane.setCenter(container);
        centerPane.setCenter(pane);
        this.currentTable = table;

        loadTransactions();
    }

    private void setAuditPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        // Header
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("📝");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Audit Log");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("System activity and security log");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TableView<AuditLog> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0;";

        TableColumn<AuditLog, Number> id = new TableColumn<>("ID");
        id.setStyle(headerStyle);
        id.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().logId));

        TableColumn<AuditLog, Number> uid = new TableColumn<>("User ID");
        uid.setStyle(headerStyle);
        uid.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().userId == null ? 0 : cd.getValue().userId));
        uid.setCellFactory(col -> new TableCell<AuditLog, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.intValue() == 0) {
                    setText("System");
                    setStyle("-fx-alignment: CENTER; -fx-text-fill: #7f8c8d;");
                } else {
                    setText(String.valueOf(item));
                    setStyle("-fx-alignment: CENTER; -fx-text-fill: #3498db; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<AuditLog, String> action = new TableColumn<>("Action");
        action.setStyle(headerStyle);
        action.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().action));
        action.setCellFactory(col -> new TableCell<AuditLog, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    // Color code actions
                    if (item.toLowerCase().contains("success") || item.toLowerCase().contains("approved")) {
                        setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    } else if (item.toLowerCase().contains("fail") || item.toLowerCase().contains("error")) {
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    } else if (item.toLowerCase().contains("delete") || item.toLowerCase().contains("remove")) {
                        setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
                    } else if (item.toLowerCase().contains("create") || item.toLowerCase().contains("add")) {
                        setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                    } else if (item.toLowerCase().contains("update") || item.toLowerCase().contains("edit")) {
                        setStyle("-fx-text-fill: #f39c12; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        TableColumn<AuditLog, String> ip = new TableColumn<>("IP Address");
        ip.setStyle(headerStyle);
        ip.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().ipAddress));
        ip.setCellFactory(col -> new TableCell<AuditLog, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-font-family: 'Monospaced', 'Consolas', monospace; -fx-font-size: 11px;");
                }
            }
        });

        TableColumn<AuditLog, String> ts = new TableColumn<>("Timestamp");
        ts.setStyle(headerStyle);
        ts.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().timestamp != null ? cd.getValue().timestamp.toString() : ""
        ));
        ts.setCellFactory(col -> new TableCell<AuditLog, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.isEmpty()) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-font-family: 'Monospaced', 'Consolas', monospace; -fx-font-size: 11px; -fx-text-fill: #7f8c8d;");
                }
            }
        });

        table.getColumns().addAll(id, uid, action, ip, ts);
        table.setItems(auditLogs);

        // Controls Panel
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(15));
        controls.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");

        Button refresh = createStyledButton("🔄 Refresh", "#3498db");
        refresh.setOnAction(e -> loadAuditLogs());

        Button clearOld = createStyledButton("🗑️ Clear Old Logs", "#e74c3c");
        clearOld.setOnAction(e -> {
            if (confirm("Clear audit logs older than 30 days? This action cannot be undone.")) {
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return DatabaseService.clearOldAuditLogs();
                    }
                };
                task.setOnSucceeded(ev -> {
                    if (task.getValue()) {
                        showSuccessAlert("Success", "Old audit logs cleared successfully!");
                        loadAuditLogs();
                    } else {
                        showErrorAlert("Error", "Failed to clear audit logs.");
                    }
                });
                task.setOnFailed(ev -> showErrorAlert("Error", "Failed to clear audit logs: " + task.getException().getMessage()));
                new Thread(task).start();
            }
        });

        Button exportBtn = createStyledButton("📤 Export Logs", "#2ecc71");
        exportBtn.setOnAction(e -> exportAuditLogs());

        // Filter controls
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        Label filterLabel = new Label("Filter:");
        filterLabel.setStyle("-fx-text-fill: #666; -fx-font-weight: bold;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search actions...");
        searchField.setPrefWidth(200);
        searchField.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-padding: 6 12;");

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterAuditLogs(newVal);
        });

        filterBox.getChildren().addAll(filterLabel, searchField);

        controls.getChildren().addAll(refresh, clearOld, exportBtn, new Separator(), filterBox);

        VBox container = new VBox(15, controls, table);
        pane.setCenter(container);
        centerPane.setCenter(pane);
        this.currentTable = table;

        loadAuditLogs();
    }

    private void filterAuditLogs(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            loadAuditLogs();
            return;
        }

        String searchLower = searchText.toLowerCase();
        ObservableList<AuditLog> filtered = FXCollections.observableArrayList();

        Task<List<AuditLog>> task = new Task<>() {
            @Override
            protected List<AuditLog> call() {
                return DatabaseService.loadAuditLogs();
            }
        };
        task.setOnSucceeded(e -> {
            List<AuditLog> allLogs = task.getValue();
            for (AuditLog log : allLogs) {
                if (log.action.toLowerCase().contains(searchLower) ||
                        (log.ipAddress != null && log.ipAddress.toLowerCase().contains(searchLower)) ||
                        (log.userId != null && String.valueOf(log.userId).contains(searchLower))) {
                    filtered.add(log);
                }
            }
            auditLogs.setAll(filtered);
        });
        new Thread(task).start();
    }

    private void exportAuditLogs() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Audit Logs");
        chooser.setInitialFileName("audit_logs_" + System.currentTimeMillis() + ".csv");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        java.io.File f = chooser.showSaveDialog(root.getScene().getWindow());
        if (f == null) return;

        Task<Void> exportTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                    // Header
                    pw.println("\"Log ID\",\"User ID\",\"Action\",\"IP Address\",\"Timestamp\"");

                    // Data
                    for (AuditLog log : auditLogs) {
                        String userId = log.userId == null ? "System" : String.valueOf(log.userId);
                        String action = log.action.replace("\"", "\"\"");
                        String ip = log.ipAddress == null ? "" : log.ipAddress.replace("\"", "\"\"");
                        String timestamp = log.timestamp == null ? "" : log.timestamp.toString().replace("\"", "\"\"");

                        pw.println(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                                log.logId, userId, action, ip, timestamp));
                    }
                }
                return null;
            }
        };

        exportTask.setOnSucceeded(e -> showSuccessAlert("Export Successful",
                "Audit logs exported to:\n" + f.getAbsolutePath() + "\n\n" +
                        "Rows exported: " + auditLogs.size()));
        exportTask.setOnFailed(e -> showErrorAlert("Export Failed",
                "Error: " + exportTask.getException().getMessage()));

        new Thread(exportTask).start();
    }

    private void setSettingsPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        // Header
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("⚙️");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("System Settings");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("Configure system parameters and preferences");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TableView<Setting> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(true);

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0;";

        TableColumn<Setting, String> key = new TableColumn<>("Key");
        key.setStyle(headerStyle);
        key.setPrefWidth(250);
        key.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().settingKey));
        key.setCellFactory(col -> new TableCell<Setting, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-font-weight: bold; -fx-font-family: 'Monospaced', 'Consolas', monospace;");
                }
            }
        });

        TableColumn<Setting, String> val = new TableColumn<>("Value");
        val.setStyle(headerStyle);
        val.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().settingValue));
        val.setCellFactory(TextFieldTableCell.forTableColumn());
        val.setOnEditCommit(event -> {
            Setting setting = event.getRowValue();
            String newValue = event.getNewValue();

            Task<Boolean> updateTask = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    return DatabaseService.updateSetting(setting.settingKey, newValue);
                }
            };
            updateTask.setOnSucceeded(e -> {
                if (updateTask.getValue()) {
                    setting.settingValue = newValue;
                    DatabaseService.writeAudit(0, "Updated setting: " + setting.settingKey + " = " + newValue);
                    showSuccessAlert("Success", "Setting updated: " + setting.settingKey);
                } else {
                    showErrorAlert("Failed", "Could not update setting");
                    table.refresh();
                }
            });
            updateTask.setOnFailed(e -> {
                showErrorAlert("Failed", "Error: " + updateTask.getException().getMessage());
                table.refresh();
            });
            new Thread(updateTask).start();
        });

        TableColumn<Setting, String> desc = new TableColumn<>("Description");
        desc.setStyle(headerStyle);
        desc.setCellValueFactory(cd -> {
            String keyVal = cd.getValue().settingKey;
            // Provide descriptions for common settings
            switch (keyVal.toLowerCase()) {
                case "platform_fee_percent":
                    return new SimpleStringProperty("Platform fee percentage charged on loans");
                case "minimum_loan_amount":
                    return new SimpleStringProperty("Minimum loan amount allowed");
                case "maximum_loan_amount":
                    return new SimpleStringProperty("Maximum loan amount allowed");
                case "default_interest_rate":
                    return new SimpleStringProperty("Default interest rate for loans");
                case "kyc_required":
                    return new SimpleStringProperty("Whether KYC verification is required");
                case "currency":
                    return new SimpleStringProperty("System currency (e.g., PKR, USD)");
                case "system_email":
                    return new SimpleStringProperty("System email for notifications");
                case "max_loan_tenure":
                    return new SimpleStringProperty("Maximum loan tenure in months");
                case "auto_approve_loans":
                    return new SimpleStringProperty("Auto-approve loans under amount");
                case "maintenance_mode":
                    return new SimpleStringProperty("Enable/disable maintenance mode");
                default:
                    return new SimpleStringProperty("System configuration setting");
            }
        });

        table.getColumns().addAll(key, val, desc);
        table.setItems(settings);

        // Controls Panel
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(15));
        controls.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");

        Button refresh = createStyledButton("🔄 Refresh", "#3498db");
        refresh.setOnAction(e -> loadSettings());

        Button add = createStyledButton("➕ Add Setting", "#2ecc71");
        add.setOnAction(e -> showAddSettingDialog());

        Button edit = createStyledButton("✏️ Quick Edit", "#f39c12");
        edit.setOnAction(e -> {
            Setting s = table.getSelectionModel().getSelectedItem();
            if (s == null) {
                alert("Select a setting to edit");
                return;
            }
            table.edit(table.getSelectionModel().getSelectedIndex(), val);
        });

        Button del = createStyledButton("🗑️ Delete", "#e74c3c");
        del.setOnAction(e -> {
            Setting s = table.getSelectionModel().getSelectedItem();
            if (s == null) {
                alert("Select setting");
                return;
            }
            if (confirm("Delete setting '" + s.settingKey + "' permanently?")) {
                Task<Boolean> deleteTask = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return DatabaseService.deleteSetting(s.settingKey);
                    }
                };
                deleteTask.setOnSucceeded(ev -> {
                    if (deleteTask.getValue()) {
                        DatabaseService.writeAudit(0, "Deleted setting " + s.settingKey);
                        loadSettings();
                        showSuccessAlert("Success", "Setting deleted: " + s.settingKey);
                    } else {
                        showErrorAlert("Failed", "Could not delete setting");
                    }
                });
                new Thread(deleteTask).start();
            }
        });

        Button defaultSettings = createStyledButton("🔄 Load Defaults", "#9b59b6");
        defaultSettings.setOnAction(e -> {
            if (confirm("Load default system settings? This will reset all settings to defaults.")) {
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return DatabaseService.loadDefaultSettings();
                    }
                };
                task.setOnSucceeded(ev -> {
                    if (task.getValue()) {
                        loadSettings();
                        showSuccessAlert("Success", "Default settings loaded successfully!");
                    } else {
                        showErrorAlert("Failed", "Could not load default settings");
                    }
                });
                new Thread(task).start();
            }
        });

        controls.getChildren().addAll(refresh, add, edit, del, defaultSettings);

        // Statistics Panel
        HBox statsPanel = new HBox(20);
        statsPanel.setPadding(new Insets(15));
        statsPanel.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");

        Label totalSettings = new Label("Total Settings: 0");
        totalSettings.setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold;");

        Label lastUpdated = new Label("Last Updated: --");
        lastUpdated.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");

        statsPanel.getChildren().addAll(totalSettings, lastUpdated);

        // Update stats when settings are loaded
        settings.addListener((ListChangeListener<Setting>) c -> {
            totalSettings.setText("Total Settings: " + settings.size());

            // Find most recent setting by looking at audit logs
            Task<String> task = new Task<>() {
                @Override
                protected String call() throws Exception {
                    return DatabaseService.getLastSettingUpdate();
                }
            };
            task.setOnSucceeded(ev -> {
                lastUpdated.setText("Last Updated: " + task.getValue());
            });
            new Thread(task).start();
        });

        VBox container = new VBox(15, controls, statsPanel, table);
        pane.setCenter(container);
        centerPane.setCenter(pane);
        this.currentTable = table;

        loadSettings();
    }

    private void showAddSettingDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add New Setting");
        dialog.setHeaderText("Create a new system setting");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField keyField = new TextField();
        keyField.setPromptText("setting_key (e.g., platform_fee_percent)");

        TextField valueField = new TextField();
        valueField.setPromptText("Value");

        grid.add(new Label("Key:"), 0, 0);
        grid.add(keyField, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(valueField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Validate input
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        keyField.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean isValid = !newVal.trim().isEmpty() && !valueField.getText().trim().isEmpty();
            okButton.setDisable(!isValid);
        });

        valueField.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean isValid = !newVal.trim().isEmpty() && !keyField.getText().trim().isEmpty();
            okButton.setDisable(!isValid);
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String key = keyField.getText().trim();
                String value = valueField.getText().trim();

                // Validate key format (alphanumeric and underscores only)
                if (!key.matches("^[a-zA-Z0-9_]+$")) {
                    alert("Key can only contain letters, numbers, and underscores");
                    return null;
                }

                return new Pair<>(key, value);
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            Task<Boolean> insertTask = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    return DatabaseService.insertSetting(pair.getKey(), pair.getValue());
                }
            };
            insertTask.setOnSucceeded(e -> {
                if (insertTask.getValue()) {
                    DatabaseService.writeAudit(0, "Inserted setting " + pair.getKey());
                    loadSettings();
                    showSuccessAlert("Success", "Setting added: " + pair.getKey());
                } else {
                    showErrorAlert("Failed", "Could not add setting. Key might already exist.");
                }
            });
            new Thread(insertTask).start();
        });
    }
    // ---------------- Wallet Management ----------------

    private void showWalletManagement() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("💼");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Wallet Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("View and manage all user wallets");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TableView<WalletInfo> walletTable = new TableView<>();
        walletTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        walletTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0;";

        TableColumn<WalletInfo, Number> userIdCol = new TableColumn<>("User ID");
        userIdCol.setStyle(headerStyle);
        userIdCol.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().userId));

        TableColumn<WalletInfo, String> nameCol = new TableColumn<>("Name");
        nameCol.setStyle(headerStyle);
        nameCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().userName));

        TableColumn<WalletInfo, String> roleCol = new TableColumn<>("Role");
        roleCol.setStyle(headerStyle);
        roleCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().userRole));

        TableColumn<WalletInfo, Number> balanceCol = new TableColumn<>("Balance (PKR)");
        balanceCol.setStyle(headerStyle);
        balanceCol.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().balance));
        balanceCol.setCellFactory(col -> new TableCell<WalletInfo, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("₨ %,.2f", item.doubleValue()));
                    setStyle("-fx-alignment: CENTER_RIGHT; -fx-font-weight: bold;");
                    if (item.doubleValue() > 0) {
                        setStyle("-fx-alignment: CENTER_RIGHT; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
                    } else if (item.doubleValue() < 0) {
                        setStyle("-fx-alignment: CENTER_RIGHT; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
                    }
                }
            }
        });

        TableColumn<WalletInfo, String> lastUpdatedCol = new TableColumn<>("Last Updated");
        lastUpdatedCol.setStyle(headerStyle);
        lastUpdatedCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().lastTransaction));

        walletTable.getColumns().addAll(userIdCol, nameCol, roleCol, balanceCol, lastUpdatedCol);

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(15));
        controls.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");

        Button refresh = createStyledButton("🔄 Refresh", "#3498db");
        refresh.setOnAction(e -> loadWallets(walletTable));


        controls.getChildren().addAll(refresh);

        VBox container = new VBox(15, controls, walletTable);
        pane.setCenter(container);
        centerPane.setCenter(pane);
        this.currentTable = walletTable;

        loadWallets(walletTable);
    }

    private void loadWallets(TableView<WalletInfo> table) {
        Task<List<WalletInfo>> task = new Task<>() {
            @Override
            protected List<WalletInfo> call() {
                return DatabaseService.loadAllWallets();
            }
        };
        task.setOnSucceeded(e -> {
            ObservableList<WalletInfo> wallets = FXCollections.observableArrayList(task.getValue());
            table.setItems(wallets);
        });
        new Thread(task).start();
    }

    private void setTransactionsPaneForUser(int userId) {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label icon = new Label("💰");
        icon.setStyle("-fx-font-size: 32px;");

        VBox titleBox = new VBox(2);
        Text title = new Text("Transactions for User #" + userId);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("View transaction history");
        subtitle.setStyle("-fx-fill: #666; -fx-font-size: 12px;");

        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().addAll(icon, titleBox);
        pane.setTop(header);

        TableView<TransactionRow> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String headerStyle = "-fx-background-color: #f8f9fa; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0;";

        TableColumn<TransactionRow, Number> id = new TableColumn<>("ID");
        id.setStyle(headerStyle);
        id.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().transactionId));

        TableColumn<TransactionRow, String> type = new TableColumn<>("Type");
        type.setStyle(headerStyle);
        type.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().type));

        TableColumn<TransactionRow, Number> amount = new TableColumn<>("Amount (PKR)");
        amount.setStyle(headerStyle);
        amount.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().amount));
        amount.setCellFactory(col -> new TableCell<TransactionRow, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("₨ %,.2f", item.doubleValue()));
                    setStyle("-fx-alignment: CENTER_RIGHT; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<TransactionRow, String> date = new TableColumn<>("Date");
        date.setStyle(headerStyle);
        date.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().createdAt.toString()));

        table.getColumns().addAll(id, type, amount, date);
        table.setItems(FXCollections.observableArrayList(DatabaseService.loadTransactionsForUser(userId)));

        Button backBtn = createStyledButton("← Back to Users", "#3498db");
        backBtn.setOnAction(e -> setUsersPane());

        HBox controls = new HBox(backBtn);
        controls.setPadding(new Insets(0, 0, 15, 0));

        pane.setTop(new VBox(header, controls));
        pane.setCenter(table);
        centerPane.setCenter(pane);
        this.currentTable = table;
    }

    private void exportCurrentTable() {
        if (currentTable == null) {
            showErrorAlert("Export Failed", "No active table to export");
            return;
        }
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save CSV");
        chooser.setInitialFileName("export_" + System.currentTimeMillis() + ".csv");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        java.io.File f = chooser.showSaveDialog(root.getScene().getWindow());
        if (f == null) return;

        Task<Void> exportTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                    ObservableList<? extends TableColumn<?, ?>> cols = currentTable.getColumns();
                    for (int i = 0; i < cols.size(); i++) {
                        pw.print("\"" + cols.get(i).getText() + "\"");
                        if (i < cols.size() - 1) pw.print(",");
                    }
                    pw.println();
                    for (Object rowObj : currentTable.getItems()) {
                        for (int c = 0; c < cols.size(); c++) {
                            TableColumn col = cols.get(c);
                            Object cellVal = col.getCellObservableValue(rowObj) == null ? "" : col.getCellObservableValue(rowObj).getValue();
                            pw.print("\"" + (cellVal == null ? "" : cellVal.toString().replace("\"", "\"\"")) + "\"");
                            if (c < cols.size() - 1) pw.print(",");
                        }
                        pw.println();
                    }
                }
                return null;
            }
        };

        exportTask.setOnSucceeded(e -> showSuccessAlert("Export Successful",
                "Data exported to:\n" + f.getAbsolutePath() + "\n\n" +
                        "Rows: " + currentTable.getItems().size()));
        exportTask.setOnFailed(e -> showErrorAlert("Export Failed",
                "Error: " + exportTask.getException().getMessage()));

        new Thread(exportTask).start();
    }

    private void refreshAll() {
        Task<Void> refreshTask = new Task<>() {
            @Override
            protected Void call() {
                loadUsers();
                loadLoans();
                loadContributions();
                loadContributionBalances();
                loadTransactions();
                loadAuditLogs();
                loadSettings();
                return null;
            }
        };
        refreshTask.setOnSucceeded(e -> showSuccessAlert("Refresh Complete", "All data has been refreshed"));
        new Thread(refreshTask).start();
    }

    private void checkDatabaseConnection() {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return DatabaseService.testConnection();
            }
        };
        task.setOnSucceeded(e -> {
            if (task.getValue()) {
                showSuccessAlert("Database Connection", "✅ Database connection successful!");
            } else {
                showErrorAlert("Database Connection", "❌ Database connection failed!");
            }
        });
        task.setOnFailed(e -> showErrorAlert("Database Connection", "❌ Error: " + task.getException().getMessage()));
        new Thread(task).start();
    }

    private void alert(String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            a.setTitle("Information");
            a.setHeaderText(null);
            a.showAndWait();
        });
    }

    private void showSuccessAlert(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            a.setTitle(title);
            a.setHeaderText("✅ Success");
            a.showAndWait();
        });
    }

    private void showErrorAlert(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            a.setTitle(title);
            a.setHeaderText("❌ Error");
            a.showAndWait();
        });
    }

    private boolean confirm(String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        a.setTitle("Confirmation");
        a.setHeaderText("⚠️ Please confirm");
        Optional<ButtonType> res = a.showAndWait();
        return res.isPresent() && res.get() == ButtonType.YES;
    }

    private void loadUsers() {
        Task<List<User>> t = new Task<>() {
            @Override
            protected List<User> call() {
                return DatabaseService.loadUsers();
            }
        };
        t.setOnSucceeded(e -> users.setAll(t.getValue()));
        new Thread(t).start();
    }

    private void loadUsersFiltered(String role) {
        Task<List<User>> t = new Task<>() {
            @Override
            protected List<User> call() {
                if (role == null || role.equals("All")) return DatabaseService.loadUsers();
                return DatabaseService.loadUsersByRole(role);
            }
        };
        t.setOnSucceeded(e -> users.setAll(t.getValue()));
        new Thread(t).start();
    }

    private void loadLoans() {
        Task<List<Loan>> t = new Task<>() {
            @Override
            protected List<Loan> call() {
                return DatabaseService.loadLoans();
            }
        };
        t.setOnSucceeded(e -> loans.setAll(t.getValue()));
        new Thread(t).start();
    }

    private void loadContributions() {
        Task<List<PoolContribution>> t = new Task<>() {
            @Override
            protected List<PoolContribution> call() {
                return DatabaseService.loadContributions();
            }
        };
        t.setOnSucceeded(e -> contributions.setAll(t.getValue()));
        new Thread(t).start();
    }

    private void loadContributionBalances() {
        Task<List<ContributionBalance>> t = new Task<>() {
            @Override
            protected List<ContributionBalance> call() {
                return DatabaseService.loadContributionBalances();
            }
        };
        t.setOnSucceeded(e -> contributionBalances.setAll(t.getValue()));
        new Thread(t).start();
    }


    private void loadTransactions() {
        Task<List<TransactionRow>> t = new Task<>() {
            @Override
            protected List<TransactionRow> call() {
                return DatabaseService.loadTransactions();
            }
        };
        t.setOnSucceeded(e -> transactions.setAll(t.getValue()));
        new Thread(t).start();
    }

    private void loadTransactionsByType(String type) {
        Task<List<TransactionRow>> t = new Task<>() {
            @Override
            protected List<TransactionRow> call() {
                return DatabaseService.loadTransactionsByType(type);
            }
        };
        t.setOnSucceeded(e -> transactions.setAll(t.getValue()));
        new Thread(t).start();
    }

    private void loadAuditLogs() {
        Task<List<AuditLog>> t = new Task<>() {
            @Override
            protected List<AuditLog> call() {
                return DatabaseService.loadAuditLogs();
            }
        };
        t.setOnSucceeded(e -> auditLogs.setAll(t.getValue()));
        new Thread(t).start();
    }

    private void loadSettings() {
        Task<List<Setting>> t = new Task<>() {
            @Override
            protected List<Setting> call() {
                return DatabaseService.loadSettings();
            }
        };
        t.setOnSucceeded(e -> settings.setAll(t.getValue()));
        new Thread(t).start();
    }

    @FunctionalInterface
    interface SupplierWithException<T> {
        T get() throws Exception;
    }


}