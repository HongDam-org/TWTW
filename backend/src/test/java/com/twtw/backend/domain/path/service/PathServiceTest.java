package com.twtw.backend.domain.path.service;

import com.twtw.backend.domain.path.dto.client.car.SearchCarPathRequest;
import com.twtw.backend.domain.path.dto.client.car.SearchCarPathResponse;
import com.twtw.backend.domain.path.dto.client.car.SearchPathFuel;
import com.twtw.backend.domain.path.dto.client.car.SearchPathOption;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.support.service.LoginTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("PathService의")
public class PathServiceTest extends LoginTest {
    @Autowired private PathService pathService;

    @Test
    @DisplayName("차량 경로를 탐색할 수 있는가")
    void searchCarPath(){
        // given
        SearchCarPathRequest request = new SearchCarPathRequest(
                "126.827507,37.636040",
                "126.832659,37.644998",
                "",
                SearchPathOption.TRAFAST,
                SearchPathFuel.DIESEL,
                1);
        // when

        SearchCarPathResponse response = pathService.searchCarPath(request);

        // then

        assertThat(response.getCode()).isEqualTo(0);
    }

    @Test
    @DisplayName("보행자 경로를 탐색할 수 있는가")
    void searchPedPath(){
        // given
        SearchPedPathRequest request = new SearchPedPathRequest(
                126.827507,37.636040,
                126.832659,37.644998,
                "START_POINT",
                "END_POINT"
        );
        // when
        SearchPedPathResponse response = pathService.searchPedPath(request);

        // then
        assertThat(response).isNotNull();
    }
}
