package crypto_wallet.domain.data;

import java.math.BigDecimal;

public record CryptoAsset(String symbol, int quantity, BigDecimal price) {}
