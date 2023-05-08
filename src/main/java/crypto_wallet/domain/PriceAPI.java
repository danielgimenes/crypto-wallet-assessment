package crypto_wallet.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PriceAPI {
    public Map<String, BigDecimal> fetchPrices(List<String> symbols);
}
