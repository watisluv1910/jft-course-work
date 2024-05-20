package com.app.backend.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

/**
 * Base repository interface for CRUD operations using Spring Data JPA.
 *
 * This interface extends [JpaRepository] and provides additional common functionality.
 *
 * @param T entity type managed by the repository.
 * @author Vladislav Nasevich
 * @see [JpaRepository]
 */
@NoRepositoryBean
@JvmDefaultWithCompatibility
interface BaseRepository<T> : JpaRepository<T, Long?> {

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve.
     * @return the entity with the specified ID.
     * @throws IllegalArgumentException if the entity with the given ID is not found.
     */
    fun getExisted(id: Long): T =
        findById(id).orElseThrow {
            IllegalArgumentException("Entity with id=$id not found")
        }
}
