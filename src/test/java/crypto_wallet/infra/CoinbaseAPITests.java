package crypto_wallet.infra;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinbaseAPITests {

    @Test
    public void fetchPrices() {
        List<String> symbols = List.of("BTC", "ETH");
        CoinbaseAPI api = new CoinbaseAPI();

        Map<String, BigDecimal> expected = Map.of(
                "BTC", new BigDecimal("10.00"),
                "ETH", new BigDecimal("20.00")
        );
        assertEquals(expected, api.fetchPrices(symbols));
    }
}
