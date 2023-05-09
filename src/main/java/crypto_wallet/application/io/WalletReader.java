package crypto_wallet.application.io;

import crypto_wallet.application.adapter.CryptoAssetAdapter;
import crypto_wallet.domain.data.CryptoAsset;
import format.NumberFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WalletReader {

    private final CryptoAssetAdapter adapter;

    public WalletReader(NumberFormatter numberFormatter) {
        this.adapter = new CryptoAssetAdapter(numberFormatter);
    }

    public List<CryptoAsset> readAssetsFromCSV(String csvFilepath) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(new File(csvFilepath));
        List<CryptoAsset> assets = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String csvLine = scanner.nextLine();
            if (csvLine.startsWith("symbol,")) {
                continue;
            }
            Scanner rowScanner = new Scanner(csvLine);
            rowScanner.useDelimiter(",");
            assets.add(adapter.toModel(rowScanner.next(), rowScanner.next(), rowScanner.next()));
        }
        return assets;
    }

}
