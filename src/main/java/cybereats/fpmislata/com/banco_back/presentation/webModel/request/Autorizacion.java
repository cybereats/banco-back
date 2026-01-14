package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Autorizacion(
        String login,
        @JsonProperty("api_token") String apiToken) {
}
