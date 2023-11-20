package com.twtw.backend.domain.place.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twtw.backend.domain.place.dto.client.SurroundPlaceRequest;
import com.twtw.backend.domain.place.dto.client.SurroundPlaceResponse;
import com.twtw.backend.domain.place.dto.response.PlaceResponse;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.dto.client.MetaDetails;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import com.twtw.backend.fixture.place.PlaceDetailsFixture;
import com.twtw.backend.fixture.place.PlaceEntityFixture;
import com.twtw.backend.global.client.KakaoMapClient;
import com.twtw.backend.support.service.LoginTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("PlaceService의")
class PlaceServiceTest extends LoginTest {

    @Autowired private PlaceService placeService;
    @MockBean private KakaoMapClient<SurroundPlaceRequest, SurroundPlaceResponse> surroundPlaceClient;

    @Test
    @DisplayName("주변 장소 찾기가 수행되는가")
    void searchSurroundPlace() {
        // given
        final SurroundPlaceResponse expected = new SurroundPlaceResponse(
                new MetaDetails(true),
                List.of(PlaceDetailsFixture.FIRST_PLACE.toPlaceDetails()));
        given(surroundPlaceClient.request(any())).willReturn(expected);

        // when
        final PlaceResponse result = placeService.searchSurroundPlace(new SurroundPlaceRequest(1.1, 2.2, 1));

        // then
        assertThat(result.getResults()).hasSameElementsAs(expected.getDocuments());
        assertThat(result.getIsLast()).isEqualTo(expected.getMeta().getIsEnd());
    }

    @Test
    @DisplayName("Place 엔티티를 통한 dto 생성이 수행되는가")
    void getPlaceDetails() {
        // given
        final Place place = PlaceEntityFixture.FIRST_PLACE.toEntity();

        // when
        final PlaceDetails placeDetails = placeService.getPlaceDetails(place);

        // then
        assertThat(placeDetails.getPlaceName()).isEqualTo(place.getPlaceName());
    }

    @Test
    @DisplayName("Place dto를 통한 엔티티 생성이 수행되는가")
    void getEntityByDetail() {
        // given
        final PlaceDetails placeDetails = PlaceDetailsFixture.SECOND_PLACE.toPlaceDetails();

        // when
        final Place place = placeService.getEntityByDetail(placeDetails);

        // then
        assertThat(place.getPlaceName()).isEqualTo(placeDetails.getPlaceName());
    }
}
