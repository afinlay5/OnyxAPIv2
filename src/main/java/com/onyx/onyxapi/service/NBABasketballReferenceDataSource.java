package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.exception.OnyxInternalServerErrorException;
import com.onyx.onyxapi.commons.model.BasicBasketballStatistics;
import com.onyx.onyxapi.commons.util.ConcurrentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public final class NBABasketballReferenceDataSource {
    private static final Charset TARGET_ENCODING = UTF_8;
    private static final String PPG = "PPG";
    private static final String RPG = "RPG";
    private static final String APG = "APG";

    private static final String NBA_BASKETBALL_REFERENCE_URL = "https://www.basketball-reference.com/players/";
    private final ExecutorService executorService;

    public CompletableFuture<BasicBasketballStatistics> getBasicStatistics(String firstName, String lastName, int season) {
        return CompletableFuture.supplyAsync(() -> {
                    var targetUri = constructBasketballReferenceTargetURI(firstName, lastName);

                     var request = HttpRequest.newBuilder()
                             .uri(targetUri).build();
                     var response = sendRequest(request);

                     if (HttpStatus.OK.value() == response.statusCode()) {
                         var parsedHtmlMap = parseHtmlForBasicStats(response, season);

                         //TODO - require map has X fields

                         return BasicBasketballStatistics.builder()
                                 .season(season)
                                 .ppg(parsedHtmlMap.get(PPG))
                                 .rpg(parsedHtmlMap.get(RPG))
                                 .apg(parsedHtmlMap.get(APG))
                                 .build();
                     } else {
                         return BasicBasketballStatistics.badInstanceForSeason(season);
                     }
                }
                , executorService);
    }

    private HttpResponse<InputStream> sendRequest(HttpRequest httpRequest) {
        try {
            return HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException exc) {
            ConcurrentUtil.resetInterruptedFlag();
            throw new OnyxInternalServerErrorException("Failed to send Http Request", exc);
        }
    }

    private URI constructBasketballReferenceTargetURI(String firstName, String surname)  {
        try {
            // For example: https://www.basketball-reference.com/players/b/birdla01.html#

            return new URI(
                    NBA_BASKETBALL_REFERENCE_URL +
                            surname.substring(0, 1).toLowerCase() +
                            "/" +
                            parseSurnameSubstring(surname) +
                            firstName.substring(0, 2).toLowerCase() +
                            "01.html"
            );
        } catch (Exception exc) {
            throw new OnyxInternalServerErrorException("Could not construct target Basketball Reference URI", exc);
        }
    }

    private String parseSurnameSubstring(String surname) {
        var length = surname.length();
        return switch (length) {
            case 1 -> surname.substring(0, 1).toLowerCase();
            case 2 -> surname.substring(0, 2).toLowerCase();
            case 3 -> surname.substring(0, 3).toLowerCase();
            case 4 -> surname.substring(0, 4).toLowerCase();
            case 5 -> surname.substring(0, 5).toLowerCase();
            default -> surname.substring(0, 5).toLowerCase();

            //TODO - Use this when Pattern Matching is enabled (JDK 18? I think)
//            case 5, default -> surname.substring(0, 5).toLowerCase();
        };
    }

    private Map<String, Float> parseHtmlForBasicStats(HttpResponse<InputStream> response, int season) {
        //TODO - In 2018 We said we would replace this with jSoup LOL........

        Stream<String> htmlContent;
        try(var responseBody = response.body()) {
            htmlContent = new BufferedReader(new InputStreamReader(responseBody, TARGET_ENCODING)).lines();

            htmlContent.forEach(System.out::println);
        } catch (IOException ioExc) {
            throw new UncheckedIOException(ioExc);
        }


        return Map.of(PPG, 33.2F, RPG, 8.6F, APG, 6.9F);

    }
}
/*

    // setBasicStatLine(HTTP_RESPONSE.body(), season+1); (Java 11 API)

    private void setBasicStatLine(java.io.InputStream in, int year) {

        var scanner = new Scanner(in);
        var html = new StringBuilder();
        var record = false;
        var begin = new StringBuilder("id=\"per_game.").append(year).append("\"");
        var end = new StringBuilder("id=\"per_game.").append(year+1).append("\"");

        //Grab HTML content to analyze -- from "<tr id=\"per_game." to "</td></tr>"
        while (scanner.hasNext()) {
            var next = scanner.next();

            //from here
            if (next.contains(begin)){
                record = true;
                continue;
            }
            //to here
            else if (record == true && next.contains(end)) {
                break;
            }
            //apend
            else if ( !next.contains(begin) && record == true) {
                html.append(next);
            }
            else
                continue;
        }

        System.gc();

        //season not found
        if (!record) {
            badInstance();
            return;
        }

        //RPG
        html.delete(0, (html.indexOf("trb_per_g\">")+("trb_per_g\">".length())));
        //remove career high boldface
        if (html.substring(0, "<strong>".length()).equals("<strong>"))
            html.delete(0, "<strong>".length());
        setRPG(Double.parseDouble(html.substring(0, html.indexOf("<"))));

        //APG
        html.delete(0, (html.indexOf("ast_per_g\">")+("ast_per_g\">".length())));
        //remove career high boldface
        if (html.substring(0, "<strong>".length()).equals("<strong>"))
            html.delete(0, "<strong>".length());
        setAPG(Double.parseDouble(html.substring(0, html.indexOf("<"))));

        //PPG
        html.delete(0, (html.indexOf("pts_per_g\">")+("pts_per_g\">".length())));
        //remove career high boldface
        if (html.substring(0, "<strong>".length()).equals("<strong>"))
            html.delete(0, "<strong>".length());
        setPPG(Double.parseDouble(html.substring(0, html.indexOf("<"))));
    };

    */