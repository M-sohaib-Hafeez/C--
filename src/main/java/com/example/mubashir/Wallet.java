package com.example.mubashir;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Wallet {
    public static BorderPane walletPane() {
        BorderPane walletPane = new BorderPane();
        walletPane.setPadding(new Insets(30));
        walletPane.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + StyleHelper.BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);

        Label walletIcon = new Label("💰");
        walletIcon.setStyle("-fx-font-size: 64px;");

        Label walletLabel = new Label("Total Wallet Balance");
        walletLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        walletLabel.setStyle("-fx-text-fill: " + StyleHelper.TEXT_DARK + ";");

        Label walletBalance = new Label();
        double balance = Server.getWalletAmount(Main.userId);
        walletBalance.setText(String.format("$%.2f", balance));
        walletBalance.setFont(Font.font("Segoe UI", FontWeight.BOLD, 48));

        if (balance > 0) {
            walletBalance.setStyle("-fx-text-fill: " + StyleHelper.ACCENT_GREEN + ";");
        } else {
            walletBalance.setStyle("-fx-text-fill: " + StyleHelper.ACCENT_RED + ";");
        }

        Label infoLabel = new Label("This balance can be used for loans, and investments");
        infoLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");

        content.getChildren().addAll(walletIcon, walletLabel, walletBalance, infoLabel);
        walletPane.setCenter(content);

        return walletPane;
    }
}