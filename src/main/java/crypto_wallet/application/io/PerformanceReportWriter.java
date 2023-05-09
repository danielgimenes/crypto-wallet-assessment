package crypto_wallet.application.io;

import crypto_wallet.domain.data.WalletPerformanceReport;
import currency.MoneyFormatter;

public class PerformanceReportWriter {
    private final MoneyFormatter moneyFormatter;

    public PerformanceReportWriter(MoneyFormatter moneyFormatter) {
        this.moneyFormatter = moneyFormatter;
    }

    public void printReport(WalletPerformanceReport report) {
        System.out.printf(
                "total=%s,best_asset=%s,best_performance=%f,worst_asset=%s,worst_performance=%f\n",
                moneyFormatter.format(report.total()),
                report.bestAsset(),
                report.bestPerformance(),
                report.worstAsset(),
                report.worstPerformance()
        );
    }
}
