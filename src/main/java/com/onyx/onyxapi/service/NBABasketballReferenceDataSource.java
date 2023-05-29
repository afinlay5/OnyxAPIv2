package com.onyx.onyxapi.service;

import com.google.common.collect.ImmutableList;
import com.onyx.onyxapi.commons.exception.BasketballStatisticsNotFoundException;
import com.onyx.onyxapi.commons.exception.OnyxInternalServerErrorException;
import com.onyx.onyxapi.commons.model.BasicBasketballStatistics;
import com.onyx.onyxapi.commons.util.ConcurrentUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static com.onyx.onyxapi.commons.util.Constants.*;
import static com.onyx.onyxapi.commons.util.Preconditions.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public final class NBABasketballReferenceDataSource {

    private static final String CANNOT_PARSE_FROM_XML_EXC_STR = "%s could not be parsed from XML";
    private static final String CANNOT_PARSE_PPG_FROM_XML_EXC_STR = String.format(CANNOT_PARSE_FROM_XML_EXC_STR, "PPG");
    private static final String CANNOT_PARSE_RPG_FROM_XML_EXC_STR = String.format(CANNOT_PARSE_FROM_XML_EXC_STR, "RPG");
    private static final String CANNOT_PARSE_APG_FROM_XML_EXC_STR = String.format(CANNOT_PARSE_FROM_XML_EXC_STR, "APG");

    private static final String NBA_BASKETBALL_REFERENCE_URL = "https://www.basketball-reference.com/players/";

    private static final String APG = "APG";
    private static final String RPG = "RPG";
    private static final String PPG = "PPG";

    private static final String PPG_TARGET_XML_POINT = "data-stat=\"pts_per_g\"";
    private static final String RPG_TARGET_XML_POINT = "data-stat=\"trb_per_g\"";
    private static final String APG_TARGET_XML_POINT = "data-stat=\"ast_per_g\"";

    private static final Charset TARGET_ENCODING = UTF_8;
    private static final Set<String> IRRELEVANT_XML_TAGS = Set.of("<strong>");


    private final ExecutorService executorService;

    public CompletableFuture<BasicBasketballStatistics> getBasicStatistics(String firstName, String lastName, int season) {
        return CompletableFuture.supplyAsync(() -> {
            val targetUri = constructBasketballReferenceTargetURI(firstName, lastName);

            val request = HttpRequest.newBuilder().uri(targetUri).build();
            val response = sendRequest(request);

            if (HttpStatus.OK.value() == response.statusCode()) {
                val parsedHtmlMap = rawParseHtmlForBasicStats(response, season);

                requireMapHasKeys(parsedHtmlMap, "Could not find one or more basic stats", PPG, RPG, APG);

                return BasicBasketballStatistics.builder()
                        .season(season)
                        .ppg(parsedHtmlMap.get(PPG))
                        .rpg(parsedHtmlMap.get(RPG))
                        .apg(parsedHtmlMap.get(APG))
                        .build();
            } else {
                throw BasketballStatisticsNotFoundException.newBuilder()
                        .withTitle("No Data Found for Specified Player")
                        .withDetail("Player does not exist :(")
                        .build();
            }
        }, executorService);
    }

    @SuppressWarnings("java:S2142")
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
                            URL_DELIMITER +
                            parseSurnameSubstring(surname) +
                            firstName.substring(0, 2).toLowerCase() +
                            "01.html"
            );
        } catch (Exception exc) {
            throw new OnyxInternalServerErrorException("Could not construct target Basketball Reference URI", exc);
        }
    }

    private String parseSurnameSubstring(String surname) {
        val length = surname.length();
        return switch (length) {
            case 1 -> surname.substring(0, 1).toLowerCase();
            case 2 -> surname.substring(0, 2).toLowerCase();
            case 3 -> surname.substring(0, 3).toLowerCase();
            case 4 -> surname.substring(0, 4).toLowerCase();
            //TODO - Don't duplicate
            case 5 -> surname.substring(0, 5).toLowerCase();
            default -> surname.substring(0, 5).toLowerCase();
        };
    }

    private Map<String, Float> parseHtmlForBasicStats(HttpResponse<InputStream> response, int season) {
        //TODO - In 2018 We said we would replace this with jSoup LOL........
        throw new NotImplementedException("Let's use jsoup for this someday");
    }

    private Map<String, Float> rawParseHtmlForBasicStats(HttpResponse<InputStream> response, int season) {
        val targetXml = parseTargetXml(response, season);
        if (targetXml.isEmpty())
            throw BasketballStatisticsNotFoundException.newBuilder()
                    .withTitle("No Season Data for Player")
                    .withDetail(String.format("Player queried did not play in NBA for the [%d-%d] season", season - 1, season))
                    .build();
        else
            return Map.of(
                    PPG, getPPGFromXML(targetXml),
                    RPG, getRPGFromXML(targetXml),
                    APG, getAPGFromXML(targetXml)
            );

    }

    private List<String> parseTargetXml(HttpResponse<InputStream> response, int season) {
        try(val responseBody = response.body(); val scanner = new Scanner(responseBody, TARGET_ENCODING)) {
            val begin = new StringBuilder("id=\"per_game.").append(season).append("\"");
            val end = new StringBuilder("id=\"per_game.").append(season+1).append("\"");

            boolean foundRecord = false;
            val targetXml = new ImmutableList.Builder<String>();
            //Grab HTML content to analyze -- from "<tr id=\"per_game." to "</td></tr>"
            while (scanner.hasNext()) {
                val next = scanner.next();

                //from here
                if (next.contains(begin)){
                    foundRecord = true;
                }
                //to here
                else if (foundRecord && next.contains(end)) {
                    break;
                }
                //append
                else if (foundRecord && !next.contains(begin)) {
                    targetXml.add(next);
                }
            }

            return targetXml.build();

        } catch (IOException ioExc) {
            throw new UncheckedIOException(ioExc);
        }
    }


    private float getPPGFromXML(List<String> targetXml) {
        var targetXmlIt = targetXml.iterator();

        while (targetXmlIt.hasNext()) {
            val line = requireNotBlank(targetXmlIt.next(), CANNOT_PARSE_PPG_FROM_XML_EXC_STR);

            if (PPG_TARGET_XML_POINT.contentEquals(line.strip())) {
                val ppgXmlLine = requireNotBlank(targetXmlIt.next(), CANNOT_PARSE_PPG_FROM_XML_EXC_STR);
                val ppgElementBeginIndx = requireNonNegative(ppgXmlLine.indexOf(XML_CLOSE_TAG), "ppgElementBeginIndx") + 1;
                var ppgAsStr = removeIrrelevantXmlTagsFromLine(ppgXmlLine.substring(ppgElementBeginIndx));
                val ppgElementEndIndx = requirePositive(ppgAsStr.indexOf(XML_OPEN_TAG), "ppgElementEndIndx");

                ppgAsStr = ppgAsStr.substring(0, ppgElementEndIndx).strip();

                return Float.parseFloat(ppgAsStr);
            }
        }

        throw new IllegalArgumentException(CANNOT_PARSE_PPG_FROM_XML_EXC_STR);
    }

    private float getRPGFromXML(List<String> targetXml) {
        var targetXmlIt = targetXml.iterator();

        while (targetXmlIt.hasNext()) {
            val line = requireNotBlank(targetXmlIt.next(), CANNOT_PARSE_RPG_FROM_XML_EXC_STR);

            if (RPG_TARGET_XML_POINT.contentEquals(line.strip())) {
                val rpgXmlLine = requireNotBlank(targetXmlIt.next(), CANNOT_PARSE_RPG_FROM_XML_EXC_STR);
                val rpgElementBeginIndx = requireNonNegative(rpgXmlLine.indexOf(XML_CLOSE_TAG), "rpgElementBeginIndx") + 1;
                var rpgAsStr = removeIrrelevantXmlTagsFromLine(rpgXmlLine.substring(rpgElementBeginIndx));
                val rpgElementEndIndx = requirePositive(rpgAsStr.indexOf(XML_OPEN_TAG), "rpgElementEndIndx");

                rpgAsStr = rpgAsStr.substring(0, rpgElementEndIndx).strip();

                return Float.parseFloat(rpgAsStr);
            }
        }

        throw new IllegalArgumentException(CANNOT_PARSE_RPG_FROM_XML_EXC_STR);
    }

    private float getAPGFromXML(List<String> targetXml) {
        var targetXmlIt = targetXml.iterator();

        while (targetXmlIt.hasNext()) {
            val line = requireNotBlank(targetXmlIt.next(), CANNOT_PARSE_APG_FROM_XML_EXC_STR);

            if (APG_TARGET_XML_POINT.contentEquals(line.strip())) {
                val apgXmlLine = requireNotBlank(targetXmlIt.next(), CANNOT_PARSE_APG_FROM_XML_EXC_STR);
                val apgElementBeginIndx = requireNonNegative(apgXmlLine.indexOf(XML_CLOSE_TAG), "apgElementBeginIndx") + 1;
                var apgAsStr = removeIrrelevantXmlTagsFromLine(apgXmlLine.substring(apgElementBeginIndx));
                val apgElementEndIndx = requirePositive(apgAsStr.indexOf(XML_OPEN_TAG), "apgElementEndIndx");

                apgAsStr = apgAsStr.substring(0, apgElementEndIndx).strip();

                return Float.parseFloat(apgAsStr);
            }
        }

        throw new IllegalArgumentException(CANNOT_PARSE_APG_FROM_XML_EXC_STR);
    }

    private String removeIrrelevantXmlTagsFromLine(String line) {
       String fmtdLine = line;
       val irrelevantTagsIt = IRRELEVANT_XML_TAGS.iterator();

       do {
           fmtdLine = removeIrrelevantXmlTagFromLine(fmtdLine, irrelevantTagsIt.next());
       } while (irrelevantTagsIt.hasNext());

       return fmtdLine;
    }

    private String removeIrrelevantXmlTagFromLine(String line, String tag) {
        return line.replace(tag, "");
    }
}