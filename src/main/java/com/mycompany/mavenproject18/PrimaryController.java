package com.mycompany.mavenproject18;

import java.awt.Desktop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.util.Locale;


import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

public class PrimaryController {

    @FXML
    private TextField pathTextField;
    @FXML
    private Hyperlink pathLink;
    @FXML
    private Button deleteButton;
    @FXML
    private Button tempButton;
    @FXML
    private ChoiceBox<String> localeChoice;
    @FXML 
    private Text pathDef;
    private ResourceBundle bundle;
    private final String defaultPath = "C:\\ProgramData\\Stardock";

    @FXML
    private void initialize() {
        pathTextField.setText(defaultPath);

        // Загрузка файлов локализации для разных языков
        ResourceBundle englishBundle = ResourceBundle.getBundle("Lang.messages", new Locale("en"));
        ResourceBundle russianBundle = ResourceBundle.getBundle("Lang.messages", new Locale("ru"));
        ResourceBundle ukrainianBundle = ResourceBundle.getBundle("Lang.messages", new Locale("uk"));


        // Добавление поддерживаемых языков в ChoiceBox
        localeChoice.setItems(FXCollections.observableArrayList("English", "Русский", "Українська"));
        localeChoice.setValue("English");
        // Обработка изменения выбранного языка
        localeChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) switch (newValue) {
                case "English":
                    bundle = englishBundle;
                    break;
                case "Русский":
                    bundle = russianBundle;
                    break;
                case "Українська":
                    bundle = ukrainianBundle;
                    break;
                default:
                    break;
            }
            updateTexts();
        });

        // По умолчанию выбран английский язык
        bundle = englishBundle;
        updateTexts();
    }

    private void updateTexts() {
        deleteButton.setText(bundle.getString("deleteButton"));
        tempButton.setText(bundle.getString("tempButton"));
        pathDef.setText(bundle.getString("pathDef"));
        pathLink.setText(bundle.getString("pathLink"));
        //choosePath.setText(bundle.getString("choosePath"));
        
    }

    @FXML
    private void choosePath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(bundle.getString("choosePath"));
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            pathTextField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleDeleteButtonAction() {
        String pathToDelete = pathTextField.getText();
        if (!pathToDelete.isEmpty()) {
            File fileToDelete = new File(pathToDelete);
            if (delete(fileToDelete)) {
                showAlert(bundle.getString("patchSuccessful"));
            }
        }
    }

    private boolean delete(File file) {
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    delete(f);
                }
            }
        }
        return file.delete();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(bundle.getString("success"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleTempButtonAction() {
        try {
            String url = "https://temp-mail.org/ru/";
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Runtime.getRuntime().exec("cmd /c start " + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
