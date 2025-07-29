package com.syntokee.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntokee.literalura.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class GutendexService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private static final String API_URL = "https://gutendex.com/books?search=";

    public Optional<BookDTO> buscarLibroPorTitulo(String titulo) {
        try {
            String encoded = titulo.replace(" ", "%20");
            String url = API_URL + encoded;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Java HttpClient") // ⬅️ Esencial para Gutendex
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Error HTTP: " + response.statusCode());
                return Optional.empty();
            }

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode results = root.path("results");

            if (results.isArray()) {
                for (JsonNode result : results) {
                    String tituloLibro = result.path("title").asText();
                    if (tituloLibro.toLowerCase().contains(titulo.toLowerCase())) {
                        BookDTO dto = objectMapper.treeToValue(result, BookDTO.class);
                        return Optional.of(dto);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error al consultar la API: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        return Optional.empty();
    }
}
