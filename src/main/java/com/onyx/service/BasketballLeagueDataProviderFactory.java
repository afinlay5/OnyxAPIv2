package com.onyx.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public final class BasketballLeagueDataProviderFactory {
    private final NBABasketballReferenceDataProvider nbaBasketballReferenceDataProvider;
}
