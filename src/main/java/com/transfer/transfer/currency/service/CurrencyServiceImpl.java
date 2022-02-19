package com.transfer.transfer.currency.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.transfer.transfer.currency.validation.exception.CurrencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private static final String CURRENCY_ERROR_RESPONSE_TEXT = "Currency code could not be extracted for: ";
    private static final String RATES_RESPONSE_KEY = "rates";
    private static final String RESULT_RESPONSE_KEY = "result";
    private static final String ERROR_RESULT_KEY = "error";

    private static final int SUCCESS_HTTP_REQUEST_CODE = 200;
    private static final int CONNECT_TIMEOUT_MILLISECONDS = 5000;

    @Override
    public Optional<Map<String, Double>> getCurrencyExchangeRates(String currency) {
        Optional<Map<String, Double>> currencyExchangeRates = Optional.empty();
        HttpURLConnection httpURLConnection = null;

        try {
            URL openErApiCurrencyUrl = new URL(OPEN_ER_API_URL + currency);
            httpURLConnection = (HttpURLConnection) openErApiCurrencyUrl.openConnection();
            httpURLConnection.setRequestMethod(GET_REQUEST_METHOD);
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MILLISECONDS);
            httpURLConnection.setReadTimeout(CONNECT_TIMEOUT_MILLISECONDS);

            int statusCode = httpURLConnection.getResponseCode();

            if (statusCode != SUCCESS_HTTP_REQUEST_CODE) {
                log.error("Could not get exchange rates for currency {}, status code: {}", currency, statusCode);
                throw new CurrencyException(HttpStatus.NOT_FOUND, CURRENCY_ERROR_RESPONSE_TEXT + currency);
            }

            StringBuffer responseString = readResponseBodyFromInputStream(httpURLConnection.getInputStream());
            JsonObject jsonObject = new JsonParser().parse(responseString.toString()).getAsJsonObject();
            String result = jsonObject.get(RESULT_RESPONSE_KEY).getAsString();

            if (result.equals(ERROR_RESULT_KEY)) {
                log.error("Could not get exchange rates for currency {}", currency);
                throw new CurrencyException(HttpStatus.NOT_FOUND, CURRENCY_ERROR_RESPONSE_TEXT + currency);
            }
            JsonElement jsonElement = jsonObject.get(RATES_RESPONSE_KEY);

            Type mapOfStringAndDoubleType = new TypeToken<Map<String, Double>>(){}.getType();
            currencyExchangeRates = Optional.of(new Gson().fromJson(jsonElement, mapOfStringAndDoubleType));
        } catch (IOException e) {
            log.error("There was an error while trying to get data from URL Connection!", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return currencyExchangeRates;
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
