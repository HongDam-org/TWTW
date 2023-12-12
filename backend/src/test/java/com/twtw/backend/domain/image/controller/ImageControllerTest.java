package com.twtw.backend.domain.image.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.image.dto.ImageResponse;
import com.twtw.backend.domain.image.service.ImageService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("ImageController의")
@WebMvcTest(ImageController.class)
class ImageControllerTest extends RestDocsTest {

    @MockBean private ImageService imageService;

    @Test
    @DisplayName("이미지 업로드 API가 수행되는가")
    void uploadImage() throws Exception {
        // given
        final ImageResponse expected =
                new ImageResponse("https://storage.googleapis.com/bucket-name/some-file-id");
        given(imageService.uploadImage(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/images")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .content(toRequestBody("image를 request시 넣어주세요"))
                                .header(
                                        "Authorization",
                                        "Bearer wefa3fsdczf32.gaoiuergf92.gb5hsa2jgh"));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.imageUrl").isString());

        // docs
        perform.andDo(print())
                .andDo(document("post upload image", getDocumentRequest(), getDocumentResponse()));
    }
}
