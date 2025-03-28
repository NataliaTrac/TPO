package zad1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.net.URI;

public class App extends Application {

    public static void startApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather & Currency Info");

        // Główny kontener aplikacji - układ pionowy
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(240, 240, 245), CornerRadii.EMPTY, Insets.EMPTY)));

        Label titleLabel = new Label("City & Country Information Service");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.rgb(60, 60, 100));

        // Pola do wprowadzania danych
        HBox countryBox = createInputField("Country:", "Poland");
        TextField countryField = (TextField) countryBox.getChildren().get(1);

        HBox cityBox = createInputField("City:", "Warsaw");
        TextField cityField = (TextField) cityBox.getChildren().get(1);

        HBox currencyBox = createInputField("Currency Code:", "USD");
        TextField currencyField = (TextField) currencyBox.getChildren().get(1);

        // Kontener dla przycisków
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);

        // Tworzenie przycisków z różnymi kolorami tła
        Button weatherBtn = createStyledButton("Weather", "skyblue");
        Button rateBtn = createStyledButton("Currency Rate", "lightgreen");
        Button nbpRateBtn = createStyledButton("NBP Rate", "lightpink");
        Button wikiBtn = createStyledButton("Show Wiki", "lightyellow");

        buttonsBox.getChildren().addAll(weatherBtn, rateBtn, nbpRateBtn, wikiBtn);

        // Obszar tekstowy do wyświetlania wyników
        TextArea resultsArea = new TextArea();
        resultsArea.setEditable(false);
        resultsArea.setPrefHeight(300);
        resultsArea.setWrapText(true);
        resultsArea.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 12px;");

        VBox.setVgrow(resultsArea, Priority.ALWAYS);

        // Dodanie wszystkich komponentów do głównego kontenera
        root.getChildren().addAll(titleLabel, countryBox, cityBox, currencyBox,
                buttonsBox, new Separator(), resultsArea);

        // Obsługa przycisku "Weather" - pobieranie i wyświetlanie informacji o pogodzie
        weatherBtn.setOnAction(e -> {
            Service service = new Service(countryField.getText());
            String weatherJson = service.getWeather(cityField.getText());
            try {
                JSONObject json = new JSONObject(weatherJson);

                String cityName = json.getString("name");
                String country = json.getJSONObject("sys").getString("country");
                String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
                String mainWeather = json.getJSONArray("weather").getJSONObject(0).getString("main");

                // Pobranie temperatur (konwersja z Kelwinów na Celsjusze)
                double temp = json.getJSONObject("main").getDouble("temp") - 273.15;
                double feelsLike = json.getJSONObject("main").getDouble("feels_like") - 273.15;
                double tempMin = json.getJSONObject("main").getDouble("temp_min") - 273.15;
                double tempMax = json.getJSONObject("main").getDouble("temp_max") - 273.15;

                // Pobranie dodatkowych informacji
                int humidity = json.getJSONObject("main").getInt("humidity");
                int pressure = json.getJSONObject("main").getInt("pressure");
                double windSpeed = json.getJSONObject("wind").getDouble("speed");
                int cloudiness = json.getJSONObject("clouds").getInt("all");

                long sunriseTime = json.getJSONObject("sys").getLong("sunrise");
                long sunsetTime = json.getJSONObject("sys").getLong("sunset");
                java.util.Date sunrise = new java.util.Date(sunriseTime * 1000);
                java.util.Date sunset = new java.util.Date(sunsetTime * 1000);
                java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm");

                // Tworzenie sformatowanego wyniku
                StringBuilder sb = new StringBuilder();
                sb.append("Weather for ").append(cityName).append(", ").append(country).append("\n\n");
                sb.append("Current: ").append(String.format("%.1f°C", temp)).append(" (").append(mainWeather).append(")\n");
                sb.append("Description: ").append(description).append("\n");
                sb.append("Feels like: ").append(String.format("%.1f°C", feelsLike)).append("\n");
                sb.append("Min/Max: ").append(String.format("%.1f°C", tempMin)).append(" / ");
                sb.append(String.format("%.1f°C", tempMax)).append("\n\n");

                sb.append("Humidity: ").append(humidity).append("%\n");
                sb.append("Pressure: ").append(pressure).append(" hPa\n");
                sb.append("Wind speed: ").append(windSpeed).append(" m/s\n");
                sb.append("loudiness: ").append(cloudiness).append("%\n\n");

                sb.append("Sunrise: ").append(timeFormat.format(sunrise)).append("\n");
                sb.append("Sunset: ").append(timeFormat.format(sunset));

                resultsArea.setText(sb.toString());
            } catch (Exception ex) {
                resultsArea.setText("Error retrieving weather data. Please check the city and country names.");
            }
        });

        // Obsługa przycisku "Currency Rate" - pobieranie i wyświetlanie kursów walut
        // Currency Rate button handler - fetch and display exchange rates
        rateBtn.setOnAction(e -> {
            try {
                String country = countryField.getText().trim();
                String currency = currencyField.getText().trim().toUpperCase();

                if(country.isEmpty() || currency.isEmpty()) {
                    resultsArea.setText("Please enter both country name and currency code.");
                    return;
                }

                Service service = new Service(country);
                Double rate = service.getRateFor(currency);

                if (rate != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Currency Exchange Rate Information:\n\n");
                    sb.append("Country: ").append(country).append("\n");
                    sb.append("Local Currency: ").append(service.countryCurrency).append("\n");
                    sb.append("Target Currency: ").append(currency).append("\n\n");
                    sb.append("Exchange Rate: 1 ").append(service.countryCurrency);
                    sb.append(" = ").append(String.format("%.4f", rate)).append(" ").append(currency);

                    resultsArea.setText(sb.toString());
                } else {
                    resultsArea.setText("Failed to retrieve exchange rate.\n" +
                            "Make sure valid currency codes are entered (e.g. EUR, GBP, JPY).");
                }
            } catch (Exception ex) {
                resultsArea.setText("An error occurred: " + ex.getMessage() + "\n\n" +
                        "Check the currency code. It should follow ISO 4217 format,\n" +
                        "e.g., USD, EUR, GBP, JPY, CHF, etc.");
            }
        });

// NBP Rate button handler - fetch and display NBP rate
        nbpRateBtn.setOnAction(e -> {
            try {
                String country = countryField.getText().trim();

                if(country.isEmpty()) {
                    resultsArea.setText("Please enter the country name.");
                    return;
                }

                Service service = new Service(country);
                Double rate = service.getNBPRate();

                if (rate != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("NBP Exchange Rate Information:\n\n");
                    sb.append("Country: ").append(country).append("\n");
                    sb.append("Local Currency: ").append(service.countryCurrency).append("\n\n");
                    sb.append("NBP Rate: 1 ").append(service.countryCurrency);
                    sb.append(" = ").append(String.format("%.4f", rate)).append(" PLN");

                    resultsArea.setText(sb.toString());
                } else {
                    resultsArea.setText("Failed to retrieve NBP rate.\n" +
                            "Check if NBP publishes rates for " + service.countryCurrency + ".");
                }
            } catch (Exception ex) {
                resultsArea.setText("An error occurred: " + ex.getMessage());
            }
        });

        // Obsługa przycisku "Show Wiki" - otwarcie strony Wikipedii dla danego miasta
        wikiBtn.setOnAction(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                try {
                    // Tworzymy nowe okno dla wbudowanej przeglądarki
                    Stage wikiStage = new Stage();
                    wikiStage.setTitle("Wikipedia - " + city);

                    BorderPane wikiRoot = new BorderPane();

                    // Dodajemy WebView zgodnie z wymaganiami
                    try {
                        // Dodajemy niezbędny argument VM programowo
                        System.setProperty("javafx.web.allowExternalAccess", "true");

                        // Utworzenie WebView dla osadzenia strony Wiki
                        WebView webView = new WebView();
                        WebEngine webEngine = webView.getEngine();

                        // Ustawiamy URL dla strony wiki danego miasta
                        String wikiURL = "https://en.wikipedia.org/wiki/" + city.replace(" ", "_");
                        webEngine.load(wikiURL);

                        wikiRoot.setCenter(webView);
                    } catch (Throwable ex) {
                        // W przypadku problemów z modułami, informujemy użytkownika
                        Label errorLabel = new Label("Error loading WebView: " + ex.getMessage());
                        errorLabel.setPadding(new Insets(20));

                        Button externalBrowserBtn = new Button("Open in external browser");
                        externalBrowserBtn.setOnAction(evt -> {
                            try {
                                String wikiURL = "https://en.wikipedia.org/wiki/" + city.replace(" ", "_");
                                java.awt.Desktop.getDesktop().browse(new URI(wikiURL));
                            } catch (Exception e2) {
                                resultsArea.setText("Error opening browser: " + e2.getMessage());
                            }
                        });

                        VBox errorBox = new VBox(20, errorLabel, externalBrowserBtn);
                        errorBox.setAlignment(Pos.CENTER);
                        wikiRoot.setCenter(errorBox);
                    }

                    Scene wikiScene = new Scene(wikiRoot, 900, 600);
                    wikiStage.setScene(wikiScene);
                    wikiStage.show();
                } catch (Exception ex) {
                    resultsArea.setText("Error opening Wikipedia: " + ex.getMessage());
                }
            } else {
                resultsArea.setText("Please enter a city name first.");
            }
        });

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createInputField(String labelText, String defaultValue) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText);
        label.setPrefWidth(100);
        label.setFont(Font.font("Arial", 14));

        TextField field = new TextField(defaultValue);
        field.setPrefHeight(30);
        HBox.setHgrow(field, Priority.ALWAYS);
        field.setStyle("-fx-background-radius: 5;");

        box.getChildren().addAll(label, field);
        return box;
    }

    private Button createStyledButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setPrefHeight(40);
        button.setStyle(
                "-fx-background-color: " + baseColor + ";" +
                        "-fx-background-radius: 5;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1);"
        );
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: derive(" + baseColor + ", -10%);" +
                                "-fx-background-radius: 5;" +
                                "-fx-font-weight: bold;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"
                )
        );
        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: " + baseColor + ";" +
                                "-fx-background-radius: 5;" +
                                "-fx-font-weight: bold;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1);"
                )
        );
        return button;
    }
}