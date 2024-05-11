package bg.softuni.quickfsapidemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FinancialDataServiceListOfCompanies {

    private final WebClient webClient;

    public FinancialDataServiceListOfCompanies(WebClient.Builder webClientBuilder, @Value("${QUICKFS_API_KEY}") String apiKey) {
        this.webClient = webClientBuilder
                .baseUrl("https://public-api.quickfs.net/v1/data/")
                .defaultHeader("X-QFS-API-Key",apiKey)
                .build();
    }
    public Mono<String> getListOfCompaniesDataService() {
        String requestBodyJson = """
                {
                  "data": {
                    "roa" : {
                      "Coca-Cola Co" : "QFS(KO:US,roa,FY-2:FY)",
                      "PepsiCo" : "QFS(PEP:US,roa,FY-2:FY)",
                      "Apple" : "QFS(AAPL:US,roa,FY-2:FY)"
                     },
                    "roic" : {
                      "Coca-Cola Co" : "QFS(KO:US,roic,FY-2:FY)",
                      "PepsiCo" : "QFS(PEP:US,roic,FY-2:FY)",
                      "Apple" : "QFS(AAPL:US,roic,FY-2:FY)"
                     },
                     "revenue" : {
                      "Coca-Cola Co" : "QFS(KO:US,revenue,FY)",
                      "PepsiCo" : "QFS(KO:US,revenue,FY)",
                      "Apple" : "QFS(AAPL:US,revenue,FY)"
                     }
                  }
                }
                """;

        return webClient.post()
                .uri("batch")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBodyJson))
                .retrieve() // Retrieve the response
                .onStatus(status -> !status.is2xxSuccessful(), response -> {

                    return response.createException().flatMap(Mono::error);
                })
                .bodyToMono(String.class);
    }
}
