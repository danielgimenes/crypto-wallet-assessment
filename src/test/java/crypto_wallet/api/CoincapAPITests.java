package crypto_wallet.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static test_utils.Formatters.*;
import static test_utils.HttpMock.mockHttpResponse;

public class CoincapAPITests {

    @Test
    public void fetchPricesWhenSingleAsset() throws IOException, InterruptedException {
        List<String> symbols = List.of("DOGE");
        HttpClient mockHttpClient = mock(HttpClient.class);

        // DOGE
        mockHttpResponse(
                mockHttpClient,
                url -> url.startsWith("https://api.coincap.io/v2/assets?search=DOGE"),
                "{\"data\": [{\"id\": \"dogecoin\"}]}"
        );
        mockHttpResponse(
                mockHttpClient,
                url -> url.contains("dogecoin/history"),
                "{\"data\": [{\"priceUsd\": \"2222222.00\"}]}"
        );

        CoincapAPI api = new CoincapAPI(mockHttpClient, defaultNumberFormatter);

        Map<String, BigDecimal> expected = Map.of(
                "DOGE", new BigDecimal("2222222.00")
        );
        assertEquals(expected, api.fetchPrices(symbols));
    }

    @Test
    public void fetchPricesWhenMultipleAssets() throws IOException, InterruptedException {
        List<String> symbols = List.of("BTC", "ETH");
        HttpClient mockHttpClient = mock(HttpClient.class);

        // BTC
        mockHttpResponse(
                mockHttpClient,
                url -> url.startsWith("https://api.coincap.io/v2/assets?search=BTC"),
                "{\"data\": [{\"id\": \"bitcoin\"}]}"
        );
        mockHttpResponse(
                mockHttpClient,
                url -> url.contains("bitcoin/history"),
                "{\"data\": [{\"priceUsd\": \"10.00\"}]}"
        );

        // ETH
        mockHttpResponse(
                mockHttpClient,
                url -> url.startsWith("https://api.coincap.io/v2/assets?search=ETH"),
                "{\"data\": [{\"id\": \"ethereum\"}]}"
        );
        mockHttpResponse(
                mockHttpClient,
                url -> url.contains("ethereum/history"),
                "{\"data\": [{\"priceUsd\": \"20.00\"}]}"
        );

        CoincapAPI api = new CoincapAPI(mockHttpClient, defaultNumberFormatter);

        Map<String, BigDecimal> expected = Map.of(
                "BTC", new BigDecimal("10.00"),
                "ETH", new BigDecimal("20.00")
        );
        assertEquals(expected, api.fetchPrices(symbols));
    }

    @Test
    public void fetchPricesWhenEmptyAssets() {
        CoincapAPI api = new CoincapAPI(defaultNumberFormatter);
        assertTrue(api.fetchPrices(List.of()).isEmpty());
    }
}
