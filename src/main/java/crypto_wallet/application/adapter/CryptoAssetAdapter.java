package crypto_wallet.application.adapter;

import crypto_wallet.domain.data.CryptoAsset;
import format.NumberFormatter;

import java.text.ParseException;

public class CryptoAssetAdapter {
    private final NumberFormatter numberFormatter;

    public CryptoAssetAdapter(NumberFormatter numberFormatter) {
        this.numberFormatter = numberFormatter;
    }

    public CryptoAsset toModel(String symbol, String quantity, String price) throws ParseException {
        return new CryptoAsset(symbol, numberFormatter.parseDouble(quantity), numberFormatter.parseMoney(price));
    }
}
