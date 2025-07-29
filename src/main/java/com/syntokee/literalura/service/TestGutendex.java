package com.syntokee.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestGutendex {
    public static void main(String[] args) {
        String titulo = "Moby Dick";
        String encoded = titulo.replace(" ", "%20");
        String url = "https://gutendex.com/books?search=" + encoded;

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)  // 👈 esto es clave
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Java HttpClient")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("=== HTTP STATUS ===");
            System.out.println(response.statusCode());

            System.out.println("=== RAW RESPONSE BODY ===");
            System.out.println(response.body());

            if (response.statusCode() != 200) {
                System.err.println("❌ Error al hacer la petición. Código: " + response.statusCode());
            } else if (response.body().isBlank()) {
                System.err.println("⚠️ La respuesta está vacía.");
            }

        } catch (IOException e) {
            System.err.println("❌ Error de red o IO: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("❌ La petición fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
