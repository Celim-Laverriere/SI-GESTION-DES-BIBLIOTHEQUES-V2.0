package org.bibliotheque.service.impl;

import lombok.NoArgsConstructor;
import org.bibliotheque.entity.PhotoEntity;
import org.bibliotheque.repository.PhotoRepository;
import org.bibliotheque.service.contract.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;


    @Override
    public PhotoEntity getPhotoById(Integer id) {
        return photoRepository.findById(id).get();
    }

    @Override
    public List<PhotoEntity> getAllPhotos() {
        List<PhotoEntity> photoEntities = new ArrayList<>();
        this.photoRepository.findAll().forEach(e -> photoEntities.add(e));
        return photoEntities;
    }

    @Override
    public PhotoEntity addPhoto(PhotoEntity photo) {
        return this.photoRepository.save(photo);
    }

    @Override
    public void updatePhoto(PhotoEntity photo) {
        this.photoRepository.save(photo);
    }

    @Override
    public void deletePhoto(Integer id) {
        this.photoRepository.deleteById(id);
    }

}
