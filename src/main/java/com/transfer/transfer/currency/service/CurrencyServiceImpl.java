package com.transfer.transfer.currency.service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.transfer.transfer.currency.validation.CurrencyValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private static final String OPEN_ER_API_URL = "https://open.er-api.com/v6/latest/";
    private static final String GET_REQUEST_METHOD = "GET";

    private static final String RATES_RESPONSE_KEY = "rates";
    private static final String RESULT_RESPONSE_KEY = "result";


    private static final int CONNECT_TIMEOUT_MILLISECONDS = 5000;

    private final CurrencyValidation currencyValidation;

    public CurrencyServiceImpl(CurrencyValidation currencyValidation) {
        this.currencyValidation = currencyValidation;
    }

    @Override
    public Map<String, Double> getCurrencyExchangeRates(String currency) {
        Optional<Map<String, Double>> currencyExchangeRates = Optional.empty();
        HttpURLConnection httpURLConnection = null;

        try {
            URL openErApiCurrencyUrl = new URL(OPEN_ER_API_URL + currency);
            httpURLConnection = (HttpURLConnection) openErApiCurrencyUrl.openConnection();
            httpURLConnection.setRequestMethod(GET_REQUEST_METHOD);
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MILLISECONDS);
            httpURLConnection.setReadTimeout(CONNECT_TIMEOUT_MILLISECONDS);

            int statusCode = httpURLConnection.getResponseCode();

            currencyValidation.validateStatusCode(statusCode);

            StringBuffer responseString = readResponseBodyFromInputStream(httpURLConnection.getInputStream());
            JsonObject jsonObject = new JsonParser().parse(responseString.toString()).getAsJsonObject();
            Optional<String> result = Optional.ofNullable(jsonObject.get(RESULT_RESPONSE_KEY).getAsString());

            currencyValidation.validateGetResultIsCorrect(result);

            JsonElement jsonElement = jsonObject.get(RATES_RESPONSE_KEY);

            Type mapOfStringAndDoubleType = new TypeToken<Map<String, Double>>(){}.getType();
            currencyExchangeRates = Optional.of(new Gson().fromJson(jsonElement, mapOfStringAndDoubleType));
        } catch (JsonParseException | IOException exception) {
            log.error("There was an error while trying to get data from URL Connection!", exception);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return currencyValidation.validateExchangeRatesAreReturned(currencyExchangeRates);
    }

    private StringBuffer readResponseBodyFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

        StringBuffer responseString = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            responseString.append(line);
            responseString.append('\r');
        }
        rd.close();
        return responseString;
    }
}
