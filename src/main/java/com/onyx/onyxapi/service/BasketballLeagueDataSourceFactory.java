package com.onyx.onyxapi.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public final class BasketballLeagueDataSourceFactory {
    private final NBABasketballReferenceDataSource nbaBasketballReferenceDataSource;
}
