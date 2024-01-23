package ru.musify.musicservice.service;

import java.util.List;
import java.util.UUID;

/**
 * BaseService interface defining common service methods for entities and their DTO representations.
 *
 * @param <E> The entity type.
 * @param <D> The DTO type.
 */
public interface BaseService<E, D> {

    /**
     * Retrieves an entity by its ID and returns its DTO representation.
     *
     * @param id The ID of the entity to be retrieved.
     * @return The DTO representation of the entity.
     */
    D findById(UUID id);

    /**
     * Retrieves all entities and returns their DTO representations.
     *
     * @return A list of DTO representations for all entities.
     */
    List<D> findAll();

    /**
     * Saves a new entity and returns its DTO representation.
     *
     * @param e The entity to be saved.
     * @return The DTO representation of the saved entity.
     */
    D save(E e);

    /**
     * Updates an existing entity and returns its DTO representation.
     *
     * @param e The entity to be updated.
     * @return The DTO representation of the updated entity.
     */
    D update(E e);

    /**
     * Removes an entity by its ID.
     *
     * @param id The ID of the entity to be removed.
     */
    void removeById(UUID id);
}