package crypto_wallet.infra;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinbaseAPITests {

    @Test
    public void fetchPrices() {
        List<String> symbols = List.of("BTC", "ETH", "USDT", "ADA", "XRP", "DOGE");
        CoinbaseAPI api = new CoinbaseAPI();

        Map<String, BigDecimal> expected = Map.of(
                "BTC", new BigDecimal("10.00"),
                "ETH", new BigDecimal("20.00"),
                "USDT", new BigDecimal("30.00"),
                "ADA", new BigDecimal("40.00"),
                "XRP", new BigDecimal("50.00"),
                "DOGE", new BigDecimal("42.00")
        );
        assertEquals(expected, api.fetchPrices(symbols));
    }
}
