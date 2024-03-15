package com.twtw.backend.domain.place.service;

import com.twtw.backend.domain.place.dto.client.SurroundPlaceRequest;
import com.twtw.backend.domain.place.dto.client.SurroundPlaceResponse;
import com.twtw.backend.domain.place.dto.response.PlaceResponse;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.place.mapper.PlaceMapper;
import com.twtw.backend.domain.plan.dto.response.PlaceDetails;
import com.twtw.backend.global.client.KakaoMapClient;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceMapper placeMapper;
    private final KakaoMapClient<SurroundPlaceRequest, SurroundPlaceResponse> surroundPlaceClient;

    @CacheEvict(
            value = "surroundPlace",
            key = "'searchSurroundPlace'.concat(#surroundPlaceRequest.toString())",
            cacheManager = "cacheManager")
    public PlaceResponse searchSurroundPlace(final SurroundPlaceRequest surroundPlaceRequest) {
        final SurroundPlaceResponse response = surroundPlaceClient.request(surroundPlaceRequest);
        return new PlaceResponse(response.getDocuments(), response.getMeta().getIsEnd());
    }

    @Cacheable(
            value = "surroundPlace",
            key = "'searchSurroundPlace'.concat(#surroundPlaceRequest.toString())",
            cacheManager = "cacheManager",
            unless = "#result.results.size() <= 0")
    public PlaceResponse searchSurroundPlaceWithCache(
            final SurroundPlaceRequest surroundPlaceRequest) {
        final SurroundPlaceResponse response = surroundPlaceClient.request(surroundPlaceRequest);
        return new PlaceResponse(response.getDocuments(), response.getMeta().getIsEnd());
    }

    public PlaceDetails getPlaceDetails(Place place) {
        return placeMapper.toPlaceResponse(place);
    }

    public Place getEntityByDetail(PlaceDetails detail) {
        return placeMapper.toEntity(detail);
    }
}
