package crypto_wallet.application;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleAppIntegrationTests {

    @Test
    public void walletPerformanceWhenSingleAssets() throws IOException {
        String path = createTempFileWithContent(
                """
                symbol,quantity,price
                BTC,0.12345,37870.5058
                """
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ConsoleApp.main(new String[] { path });

        String expectedPrint = "total=7036.65,best_asset=BTC,best_performance=1.51,worst_asset=BTC,worst_performance=1.51\n";
        assertEquals(expectedPrint, output.toString());
    }

    @Test
    public void walletPerformanceWhenTestSample() throws IOException {
        String path = createTempFileWithContent(
                """
                symbol,quantity,price
                BTC,0.12345,37870.5058
                ETH,4.89532,2004.9774
                """
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ConsoleApp.main(new String[] { path });

        String expectedPrint = "total=16984.62,best_asset=BTC,best_performance=1.51,worst_asset=ETH,worst_performance=1.01\n";
        assertEquals(expectedPrint, output.toString());
    }

    @Test
    public void walletPerformanceWhenMultipleAssets() throws IOException {
        String path = createTempFileWithContent(
                """
                symbol,quantity,price
                BTC,0.12345,37870.5058
                ETH,4.89532,2004.9774
                DOGE,32344.2222,0.3000
                ADA,2000.99999,2.5000
                USDT,4.999,0.59234
                XRP,333.222,0.40999
                """
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ConsoleApp.main(new String[] { path });

        String expectedPrint = "total=21694.72,best_asset=XRP,best_performance=2.37,worst_asset=DOGE,worst_performance=0.20\n";
        assertEquals(expectedPrint, output.toString());
    }

    private String createTempFileWithContent(String content) throws IOException {
        String tempFileName = UUID.randomUUID().toString();
        String path = File.createTempFile(tempFileName, null).getAbsolutePath();
        FileWriter writer = new FileWriter(path);
        writer.write(content);
        writer.close();
        return path;
    }

}
