package com.app.backend.repo

import com.app.backend.model.user.role.EUserRole
import com.app.backend.model.user.role.UserRole
import org.springframework.stereotype.Repository

/**
 * Repository interface for managing user roles using Spring Data JPA.
 *
 * This interface extends [BaseRepository] and provides methods for querying user roles.
 *
 * @see BaseRepository
 * @property UserRole entity type representing a user role.
 */
@Repository
interface UserRoleRepository: BaseRepository<UserRole> {

    /**
     * Finds a user role by its role name.
     *
     * @param roleName the role name to search for.
     * @return [UserRole] associated with the given role name, null if not found.
     */
    fun findByRoleName(roleName: EUserRole): UserRole?

    /**
     * Checks if a user role with the specified role name exists.
     *
     * @param roleName the role name to check.
     * @return true if a user role with the given role name exists, false otherwise.
     */
    fun existsByRoleName(roleName: EUserRole): Boolean
}