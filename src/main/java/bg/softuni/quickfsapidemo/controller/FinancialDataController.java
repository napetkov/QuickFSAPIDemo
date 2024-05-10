package bg.softuni.quickfsapidemo.controller;

import bg.softuni.quickfsapidemo.service.FinancialAllDataServiceByCompany;
import bg.softuni.quickfsapidemo.service.FinancialDataServiceByCompanyMetricAndPeriod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FinancialDataController {
    private final FinancialAllDataServiceByCompany financialAllDataServiceByCompany;
    private final FinancialDataServiceByCompanyMetricAndPeriod financialDataServiceByCompanyMetricAndPeriod;

    public FinancialDataController(FinancialAllDataServiceByCompany financialAllDataServiceByCompany, FinancialDataServiceByCompanyMetricAndPeriod financialDataServiceByCompanyMetricAndPeriod) {
        this.financialAllDataServiceByCompany = financialAllDataServiceByCompany;
        this.financialDataServiceByCompanyMetricAndPeriod = financialDataServiceByCompanyMetricAndPeriod;
    }

    @GetMapping("/company/{symbol}/financial-data")
    public Mono<ResponseEntity<String>> getCompanyFinancialAllDataByCompany(@PathVariable("symbol") String symbol) {
        return financialAllDataServiceByCompany.getCompanyFinancialAllDataByCompany(symbol)
                .map(data -> {
                    // Here, you could parse and manipulate the JSON data as needed
                    // For simplicity, let's just return the raw data
                    return ResponseEntity.ok(data);
                });
    }

    @GetMapping("/company/{symbol}/{metric}/{period}")
    public Mono<ResponseEntity<String>> getCompanyFinancialDataByCompanyMetricAndPeriod(
            @PathVariable("symbol") String symbol,
            @PathVariable("metric") String metric,
            @PathVariable("period") String period) {
        return financialDataServiceByCompanyMetricAndPeriod.getCompanyDataServiceByCompanyMetricAndPeriod(symbol, metric, period)
                .map(data -> {
                    // Here, you could parse and manipulate the JSON data as needed
                    // For simplicity, let's just return the raw data
                    return ResponseEntity.ok(data);
                });
    }
}
