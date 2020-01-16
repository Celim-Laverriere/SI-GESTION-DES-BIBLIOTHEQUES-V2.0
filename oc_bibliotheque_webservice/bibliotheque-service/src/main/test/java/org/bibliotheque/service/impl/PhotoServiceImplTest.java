package org.bibliotheque.service.impl;

import org.bibliotheque.entity.PhotoEntity;
import org.bibliotheque.repository.PhotoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PhotoServiceImplTest {

    @Mock
    PhotoRepository photoRepository;

    @InjectMocks
    PhotoServiceImpl photoServiceImpl;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPhotoById() {

        PhotoEntity photoMock = PhotoEntity.builder().id(1).urlPhoto("Dune_1.jpg")
                .nomPhoto("Dune_tome_1").ouvrageId(1).build();

        when(photoRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(photoMock));
        final PhotoEntity photoEntity = photoServiceImpl.getPhotoById(1);
        Assertions.assertEquals(photoEntity.getNomPhoto(), photoMock.getNomPhoto());
    }

    @Test
    void getAllPhotos() {

        List<PhotoEntity> photoMockList = new ArrayList<>();

        PhotoEntity photoMock1 = PhotoEntity.builder().id(1).urlPhoto("Dune_1.jpg")
                .nomPhoto("Dune_tome_1").ouvrageId(1).build();
        photoMockList.add(photoMock1);

        PhotoEntity photoMock2 = PhotoEntity.builder().id(2).urlPhoto("Fondation_1.jpg")
                .nomPhoto("Fondation_tome_1").ouvrageId(2).build();
        photoMockList.add(photoMock2);

        when(photoRepository.findAll()).thenReturn(photoMockList);
        final List<PhotoEntity> photoEntityList = photoServiceImpl.getAllPhotos();
        Assertions.assertEquals(photoEntityList.size(), 2);
        Assertions.assertEquals(photoEntityList.get(0).getNomPhoto(), photoMock1.getNomPhoto());

    }
}