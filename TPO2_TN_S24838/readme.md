# Task 2 – International Weather & Currency Information Desktop App (JavaFX)

## 🌐 Project Summary

This project is a desktop application built with **JavaFX**, designed to provide real-time **weather data**, **currency exchange rates**, and **encyclopedic context** (via Wikipedia) for any city worldwide. It demonstrates the integration of multiple public APIs, advanced Java I/O, and GUI development skills in a clean, user-friendly interface.

Developed as part of the _Object-Oriented Programming Techniques (TPO)_ course, the project emphasizes modular design, Java networking, JSON parsing, and JavaFX UI architecture.

---

## 🎯 Key Features

- **🌤️ Real-Time Weather Data**  
  Fetches detailed weather information including:
  - Temperature (current, feels-like, min/max)
  - Humidity, pressure, wind speed, cloudiness
  - Sunrise and sunset times  
  Powered by [OpenWeatherMap API](https://openweathermap.org/).

- **💱 Currency Exchange Rates**
  - Converts local currency to any target currency using live rates from [ExchangeRate API](https://open.er-api.com/).
  - Retrieves official conversion rate (mid-rate) from the [National Bank of Poland (NBP)](https://api.nbp.pl/).

- **🌍 Wikipedia Integration**
  - Built-in browser to preview the Wikipedia page for any city.
  - Fallback to system browser if JavaFX WebView is unavailable.

- **✨ Intuitive & Modern UI**
  - JavaFX GUI with styled components and hover effects
  - Organized layout with real-time feedback
  - Easily extensible with additional API services or features

---

## 🛠 Technology Stack

- **Language**: Java 8+
- **GUI**: JavaFX (`Application`, `WebView`, `Controls`, `Layouts`)
- **APIs**:
  - Weather: [OpenWeatherMap](https://openweathermap.org/)
  - Currency: [ExchangeRate API](https://open.er-api.com/)
  - Official Rates: [NBP Web API](https://api.nbp.pl/)
- **Data Parsing**: `org.json.JSONObject`
- **Networking**: `HttpURLConnection`
- **Others**: Date formatting, I18N currency mapping, exception handling

---

## 🗂️ Project Structure

```
Zadanie2/
├── Main.java       # Application launcher and service initialization
├── App.java        # Full JavaFX GUI setup and event handling
└── Service.java    # Handles API requests, parsing, and logic abstraction
```

---

### 💻 Running the App via IntelliJ IDEA

1. **Import the project** into IntelliJ as a Java project.
2. **Add JavaFX SDK** to Project Structure → Libraries.
3. In Run Configuration → VM Options, add:

```
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.web
```

4. Run `Main.java`.

---

### 🧪 Running via Command Line

#### 1. Compile:

```bash
javac -cp .;path/to/javafx-sdk/lib/* zad1/*.java
```

#### 2. Run:

```bash
java -cp .;path/to/javafx-sdk/lib/* --module-path path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.web zad1.Main
```

> Replace `path/to/javafx-sdk` with the actual SDK path on your system.

---

## 🔧 Customization

You can change the default values pre-filled in the GUI by editing:

```java
HBox countryBox = createInputField("Country:", "Poland");
HBox cityBox = createInputField("City:", "Warsaw");
HBox currencyBox = createInputField("Currency Code:", "USD");
```

---

## ⚠️ Error Handling

- Displays user-friendly error messages for API failures, invalid inputs, or missing data.
- Fallback mechanisms for missing currency mappings or unavailable GUI modules.
- Input validation for country, city, and currency code fields.
