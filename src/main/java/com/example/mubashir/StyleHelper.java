package com.example.mubashir;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StyleHelper {

    public static final String PRIMARY_COLOR = "#2c3e50";
    public static final String SECONDARY_COLOR = "#34495e";
    public static final String ACCENT_BLUE = "#3498db";
    public static final String ACCENT_GREEN = "#2ecc71";
    public static final String ACCENT_RED = "#e74c3c";
    public static final String ACCENT_ORANGE = "#f39c12";
    public static final String ACCENT_PURPLE = "#9b59b6";
    public static final String LIGHT_BG = "#f8f9fa";
    public static final String TEXT_DARK = "#2c3e50";
    public static final String TEXT_LIGHT = "#ecf0f1";
    public static final String BORDER_COLOR = "#e0e0e0";


    public static Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPadding(new Insets(8, 16, 8, 16));
        btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-cursor: hand;",
                color
        ));
        btn.setOnMouseEntered(e -> btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);",
                darkenColor(color)
        )));

        btn.setOnMouseExited(e -> btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-cursor: hand;",
                color
        )));

        return btn;
    }


    public static Button createMenuButton(String text, String color) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 20, 12, 20));
        btn.setGraphicTextGap(15);
        btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand;",
                color, TEXT_LIGHT
        ));

        return btn;
    }

    public static VBox createCard(String title, String icon, String value, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefSize(250, 120);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-font-weight: 600;");

        titleBox.getChildren().addAll(iconLabel, titleLabel);

        Label valueLabel = new Label(value);
        valueLabel.setStyle(String.format("-fx-text-fill: %s; -fx-font-size: 28px; -fx-font-weight: bold;", color));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(titleBox, valueLabel, spacer);
        return card;
    }


    public static String darkenColor(String hex) {
        try {
            if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }

            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);

            // Darken by 20%
            r = (int)(r * 0.8);
            g = (int)(g * 0.8);
            b = (int)(b * 0.8);

            // Ensure values stay within bounds
            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

            return String.format("#%02x%02x%02x", r, g, b);
        } catch (Exception e) {
            return hex; // Return original if parsing fails
        }
    }


    public static HBox createFormField(String labelText, Control control, double width) {
        HBox field = new HBox(10);
        field.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText + ":");
        label.setStyle("-fx-text-fill: " + TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600; -fx-min-width: 120;");

        control.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
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

    public static VBox createStyledForm(String title) {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-width: 1px;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: " + TEXT_DARK + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        form.getChildren().add(titleLabel);
        return form;
    }


    public static HBox createFormField(String labelText, Control control) {
        HBox field = new HBox(10);
        field.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText + ":");
        label.setStyle("-fx-text-fill: " + TEXT_DARK + "; -fx-font-size: 14px; -fx-font-weight: 600; -fx-min-width: 150;");

        control.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 8; " +
                        "-fx-font-size: 14px;"
        );

        field.getChildren().addAll(label, control);
        return field;
    }
}