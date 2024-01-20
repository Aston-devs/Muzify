package ru.musify.musicservice.service;

import java.util.List;
import java.util.UUID;

public interface BaseService<E, D> {

    D findById(UUID id);

    List<D> findAll();

    D save(E e);

    D update(E e);

    void removeById(UUID id);
}
