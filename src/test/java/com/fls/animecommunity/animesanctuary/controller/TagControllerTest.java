package com.fls.animecommunity.animesanctuary.controller;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fls.animecommunity.animesanctuary.controller.rest.TagController;
import com.fls.animecommunity.animesanctuary.exception.ResourceNotFoundException;
import com.fls.animecommunity.animesanctuary.model.tag.Tag;
import com.fls.animecommunity.animesanctuary.service.impl.TagService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTag_shouldReturnCreatedTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("example-tag");

        when(tagService.createTag(any(Tag.class))).thenReturn(tag);

        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tag)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("example-tag"));

        verify(tagService, times(1)).createTag(any(Tag.class));
    }

    @Test
    void getAllTags_shouldReturnListOfTags() throws Exception {
        Tag tag1 = new Tag();
        tag1.setName("tag1");

        Tag tag2 = new Tag();
        tag2.setName("tag2");

        when(tagService.getAllTags()).thenReturn(Arrays.asList(tag1, tag2));

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("tag1"))
                .andExpect(jsonPath("$[1].name").value("tag2"));

        verify(tagService, times(1)).getAllTags();
    }

    @Test
    void getTagById_shouldReturnTag_whenTagExists() throws Exception {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("example-tag");

        when(tagService.getTagById(anyLong())).thenReturn(tag);

        mockMvc.perform(get("/api/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("example-tag"));

        verify(tagService, times(1)).getTagById(1L);
    }

    @Test
    void getTagById_shouldReturnNotFound_whenTagDoesNotExist() throws Exception {
        when(tagService.getTagById(anyLong())).thenThrow(new ResourceNotFoundException("Tag not found"));

        mockMvc.perform(get("/api/tags/1"))
                .andExpect(status().isNotFound());

        verify(tagService, times(1)).getTagById(1L);
    }

    @Test
    void deleteTag_shouldReturnNoContent() throws Exception {
        doNothing().when(tagService).deleteTag(anyLong());

        mockMvc.perform(delete("/api/tags/1"))
                .andExpect(status().isNoContent());

        verify(tagService, times(1)).deleteTag(1L);
    }
}