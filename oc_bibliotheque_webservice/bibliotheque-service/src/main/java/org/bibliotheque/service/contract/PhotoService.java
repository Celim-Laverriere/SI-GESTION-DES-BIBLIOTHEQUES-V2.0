package org.bibliotheque.service.contract;

import org.bibliotheque.entity.PhotoEntity;
import java.util.List;

public interface PhotoService {

    PhotoEntity getPhotoById(Integer id);

    List<PhotoEntity> getAllPhotos();

    PhotoEntity addPhoto(PhotoEntity photo);

    boolean updatePhoto(PhotoEntity photo);

    boolean deletePhoto(Integer id);

}
