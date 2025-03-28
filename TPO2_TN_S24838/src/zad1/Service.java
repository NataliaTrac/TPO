package zad1; /**
 *
 *  @author Traczewska Natalia S24838
 *
 */


import org.json.JSONObject;
import java.io.*;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Service {

    private String country;
    String countryCurrency;
    private static final Map<String, String> countryCurrencyMap = new HashMap<>();


    static {
        countryCurrencyMap.put("united states", "USD");
        countryCurrencyMap.put("united arab emirates", "AED");
        countryCurrencyMap.put("afghanistan", "AFN");
        countryCurrencyMap.put("albania", "ALL");
        countryCurrencyMap.put("armenia", "AMD");
        countryCurrencyMap.put("netherlands antilles", "ANG");
        countryCurrencyMap.put("angola", "AOA");
        countryCurrencyMap.put("argentina", "ARS");
        countryCurrencyMap.put("australia", "AUD");
        countryCurrencyMap.put("aruba", "AWG");
        countryCurrencyMap.put("azerbaijan", "AZN");
        countryCurrencyMap.put("bosnia and herzegovina", "BAM");
        countryCurrencyMap.put("barbados", "BBD");
        countryCurrencyMap.put("bangladesh", "BDT");
        countryCurrencyMap.put("bulgaria", "BGN");
        countryCurrencyMap.put("bahrain", "BHD");
        countryCurrencyMap.put("burundi", "BIF");
        countryCurrencyMap.put("bermuda", "BMD");
        countryCurrencyMap.put("brunei", "BND");
        countryCurrencyMap.put("bolivia", "BOB");
        countryCurrencyMap.put("brazil", "BRL");
        countryCurrencyMap.put("bahamas", "BSD");
        countryCurrencyMap.put("bhutan", "BTN");
        countryCurrencyMap.put("botswana", "BWP");
        countryCurrencyMap.put("belarus", "BYN");
        countryCurrencyMap.put("belize", "BZD");
        countryCurrencyMap.put("canada", "CAD");
        countryCurrencyMap.put("congo", "CDF");
        countryCurrencyMap.put("switzerland", "CHF");
        countryCurrencyMap.put("chile", "CLP");
        countryCurrencyMap.put("china", "CNY");
        countryCurrencyMap.put("colombia", "COP");
        countryCurrencyMap.put("costa rica", "CRC");
        countryCurrencyMap.put("cuba", "CUP");
        countryCurrencyMap.put("cape verde", "CVE");
        countryCurrencyMap.put("czech republic", "CZK");
        countryCurrencyMap.put("djibouti", "DJF");
        countryCurrencyMap.put("denmark", "DKK");
        countryCurrencyMap.put("dominican republic", "DOP");
        countryCurrencyMap.put("algeria", "DZD");
        countryCurrencyMap.put("egypt", "EGP");
        countryCurrencyMap.put("eritrea", "ERN");
        countryCurrencyMap.put("ethiopia", "ETB");
        countryCurrencyMap.put("eurozone", "EUR");
        countryCurrencyMap.put("fiji", "FJD");
        countryCurrencyMap.put("falkland islands", "FKP");
        countryCurrencyMap.put("faroe islands", "FOK");
        countryCurrencyMap.put("united kingdom", "GBP");
        countryCurrencyMap.put("georgia", "GEL");
        countryCurrencyMap.put("guernsey", "GGP");
        countryCurrencyMap.put("ghana", "GHS");
        countryCurrencyMap.put("gibraltar", "GIP");
        countryCurrencyMap.put("gambia", "GMD");
        countryCurrencyMap.put("guinea", "GNF");
        countryCurrencyMap.put("guatemala", "GTQ");
        countryCurrencyMap.put("guyana", "GYD");
        countryCurrencyMap.put("hong kong", "HKD");
        countryCurrencyMap.put("honduras", "HNL");
        countryCurrencyMap.put("croatia", "HRK");
        countryCurrencyMap.put("haiti", "HTG");
        countryCurrencyMap.put("hungary", "HUF");
        countryCurrencyMap.put("indonesia", "IDR");
        countryCurrencyMap.put("israel", "ILS");
        countryCurrencyMap.put("isle of man", "IMP");
        countryCurrencyMap.put("india", "INR");
        countryCurrencyMap.put("iraq", "IQD");
        countryCurrencyMap.put("iran", "IRR");
        countryCurrencyMap.put("iceland", "ISK");
        countryCurrencyMap.put("jersey", "JEP");
        countryCurrencyMap.put("jamaica", "JMD");
        countryCurrencyMap.put("jordan", "JOD");
        countryCurrencyMap.put("japan", "JPY");
        countryCurrencyMap.put("kenya", "KES");
        countryCurrencyMap.put("kyrgyzstan", "KGS");
        countryCurrencyMap.put("cambodia", "KHR");
        countryCurrencyMap.put("kiribati", "KID");
        countryCurrencyMap.put("comoros", "KMF");
        countryCurrencyMap.put("south korea", "KRW");
        countryCurrencyMap.put("kuwait", "KWD");
        countryCurrencyMap.put("cayman islands", "KYD");
        countryCurrencyMap.put("kazakhstan", "KZT");
        countryCurrencyMap.put("laos", "LAK");
        countryCurrencyMap.put("lebanon", "LBP");
        countryCurrencyMap.put("sri lanka", "LKR");
        countryCurrencyMap.put("liberia", "LRD");
        countryCurrencyMap.put("lesotho", "LSL");
        countryCurrencyMap.put("libya", "LYD");
        countryCurrencyMap.put("morocco", "MAD");
        countryCurrencyMap.put("moldova", "MDL");
        countryCurrencyMap.put("madagascar", "MGA");
        countryCurrencyMap.put("north macedonia", "MKD");
        countryCurrencyMap.put("myanmar", "MMK");
        countryCurrencyMap.put("mongolia", "MNT");
        countryCurrencyMap.put("macau", "MOP");
        countryCurrencyMap.put("mauritania", "MRU");
        countryCurrencyMap.put("mauritius", "MUR");
        countryCurrencyMap.put("maldives", "MVR");
        countryCurrencyMap.put("malawi", "MWK");
        countryCurrencyMap.put("mexico", "MXN");
        countryCurrencyMap.put("malaysia", "MYR");
        countryCurrencyMap.put("mozambique", "MZN");
        countryCurrencyMap.put("namibia", "NAD");
        countryCurrencyMap.put("nigeria", "NGN");
        countryCurrencyMap.put("nicaragua", "NIO");
        countryCurrencyMap.put("norway", "NOK");
        countryCurrencyMap.put("nepal", "NPR");
        countryCurrencyMap.put("new zealand", "NZD");
        countryCurrencyMap.put("oman", "OMR");
        countryCurrencyMap.put("panama", "PAB");
        countryCurrencyMap.put("peru", "PEN");
        countryCurrencyMap.put("papua new guinea", "PGK");
        countryCurrencyMap.put("philippines", "PHP");
        countryCurrencyMap.put("pakistan", "PKR");
        countryCurrencyMap.put("poland", "PLN");
        countryCurrencyMap.put("paraguay", "PYG");
        countryCurrencyMap.put("qatar", "QAR");
        countryCurrencyMap.put("romania", "RON");
        countryCurrencyMap.put("serbia", "RSD");
        countryCurrencyMap.put("russia", "RUB");
        countryCurrencyMap.put("rwanda", "RWF");
        countryCurrencyMap.put("saudi arabia", "SAR");
        countryCurrencyMap.put("solomon islands", "SBD");
        countryCurrencyMap.put("seychelles", "SCR");
        countryCurrencyMap.put("sudan", "SDG");
        countryCurrencyMap.put("sweden", "SEK");
        countryCurrencyMap.put("singapore", "SGD");
        countryCurrencyMap.put("saint helena", "SHP");
        countryCurrencyMap.put("sierra leone", "SLE");
        countryCurrencyMap.put("somalia", "SOS");
        countryCurrencyMap.put("suriname", "SRD");
        countryCurrencyMap.put("south sudan", "SSP");
        countryCurrencyMap.put("sao tome and principe", "STN");
        countryCurrencyMap.put("syria", "SYP");
        countryCurrencyMap.put("eswatini", "SZL");
        countryCurrencyMap.put("thailand", "THB");
        countryCurrencyMap.put("tajikistan", "TJS");
        countryCurrencyMap.put("turkmenistan", "TMT");
        countryCurrencyMap.put("tunisia", "TND");
        countryCurrencyMap.put("tonga", "TOP");
        countryCurrencyMap.put("turkey", "TRY");
        countryCurrencyMap.put("trinidad and tobago", "TTD");
        countryCurrencyMap.put("tuvalu", "TVD");
        countryCurrencyMap.put("taiwan", "TWD");
        countryCurrencyMap.put("tanzania", "TZS");
        countryCurrencyMap.put("ukraine", "UAH");
        countryCurrencyMap.put("uganda", "UGX");
        countryCurrencyMap.put("uruguay", "UYU");
        countryCurrencyMap.put("uzbekistan", "UZS");
        countryCurrencyMap.put("venezuela", "VES");
        countryCurrencyMap.put("vietnam", "VND");
        countryCurrencyMap.put("vanuatu", "VUV");
        countryCurrencyMap.put("samoa", "WST");
        countryCurrencyMap.put("central african republic", "XAF");
        countryCurrencyMap.put("east caribbean", "XCD");
        countryCurrencyMap.put("international monetary fund", "XDR");
        countryCurrencyMap.put("west african", "XOF");
        countryCurrencyMap.put("french polynesia", "XPF");
        countryCurrencyMap.put("yemen", "YER");
        countryCurrencyMap.put("south africa", "ZAR");
        countryCurrencyMap.put("zambia", "ZMW");
        countryCurrencyMap.put("zimbabwe", "ZWL");
        countryCurrencyMap.put("austria", "EUR");
        countryCurrencyMap.put("belgium", "EUR");
        countryCurrencyMap.put("cyprus", "EUR");
        countryCurrencyMap.put("estonia", "EUR");
        countryCurrencyMap.put("finland", "EUR");
        countryCurrencyMap.put("france", "EUR");
        countryCurrencyMap.put("germany", "EUR");
        countryCurrencyMap.put("greece", "EUR");
        countryCurrencyMap.put("ireland", "EUR");
        countryCurrencyMap.put("italy", "EUR");
        countryCurrencyMap.put("latvia", "EUR");
        countryCurrencyMap.put("lithuania", "EUR");
        countryCurrencyMap.put("luxembourg", "EUR");
        countryCurrencyMap.put("malta", "EUR");
        countryCurrencyMap.put("monaco", "EUR");
        countryCurrencyMap.put("netherlands", "EUR");
        countryCurrencyMap.put("portugal", "EUR");
        countryCurrencyMap.put("san marino", "EUR");
        countryCurrencyMap.put("slovakia", "EUR");
        countryCurrencyMap.put("slovenia", "EUR");
        countryCurrencyMap.put("spain", "EUR");
        countryCurrencyMap.put("vatican city", "EUR");
        countryCurrencyMap.put("puerto rico", "USD");
        countryCurrencyMap.put("american samoa", "USD");
        countryCurrencyMap.put("guam", "USD");
        countryCurrencyMap.put("us virgin islands", "USD");
        countryCurrencyMap.put("northern mariana islands", "USD");
        countryCurrencyMap.put("andorra", "EUR");
        countryCurrencyMap.put("greenland", "DKK");
        countryCurrencyMap.put("liechtenstein", "CHF");
        countryCurrencyMap.put("montenegro", "EUR");
        countryCurrencyMap.put("north korea", "KPW");
        countryCurrencyMap.put("palau", "USD");
        countryCurrencyMap.put("marshall islands", "USD");
        countryCurrencyMap.put("micronesia", "USD");
        countryCurrencyMap.put("saint kitts and nevis", "XCD");
        countryCurrencyMap.put("saint lucia", "XCD");
        countryCurrencyMap.put("saint vincent and the grenadines", "XCD");
        countryCurrencyMap.put("nauru", "AUD");
        countryCurrencyMap.put("timor-leste", "USD");
        countryCurrencyMap.put("kosovo", "EUR");
        countryCurrencyMap.put("south ossetia", "RUB");
        countryCurrencyMap.put("abkhazia", "RUB");
        countryCurrencyMap.put("cook islands", "NZD");
        countryCurrencyMap.put("niue", "NZD");
        countryCurrencyMap.put("vatican", "EUR");
    }

    public Service(String country) {
        this.country = country.toLowerCase();
        this.countryCurrency = getCurrencyCode(this.country);
    }

    public String getWeather(String city) {
        String apiKey = "";
        String urlString = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s,%s&appid=%s",
                city, country, apiKey);
        return fetchData(urlString);
    }

    public Double getRateFor(String currencyCode) {
        String urlString = "https://open.er-api.com/v6/latest/" + countryCurrency;
        String response = fetchData(urlString);
        if (response != null) {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("rates")) {
                JSONObject rates = jsonObject.getJSONObject("rates");
                if (rates.has(currencyCode)) {
                    return rates.getDouble(currencyCode);
                } else {
                    System.err.println("Currency code not found in the rates.");
                }
            } else {
                System.err.println("Rates key not found in the JSON response.");
            }
        }
        return null;
    }

    public Double getNBPRate() {
        if ("PLN".equalsIgnoreCase(countryCurrency)) {
            return 1.0;
        }
        String[] tables = {"a", "b", "c"};
        for (String table : tables) {
            String urlString = "https://api.nbp.pl/api/exchangerates/rates/" + table + "/" + countryCurrency + "/?format=json";
            String response = fetchData(urlString);
            if (response != null) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("rates")) {
                    return jsonObject.getJSONArray("rates").getJSONObject(0).getDouble("mid");
                }
            }
        }
        return null;
    }

    private String getCurrencyCode(String country) {
        return countryCurrencyMap.getOrDefault(country.toLowerCase(), "USD");
    }

    private String fetchData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readJsonFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}