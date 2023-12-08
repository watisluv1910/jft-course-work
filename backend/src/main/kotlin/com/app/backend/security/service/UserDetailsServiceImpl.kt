package com.app.backend.security.service

import com.app.backend.model.user.User
import com.app.backend.model.user.UserDetailsImpl
import com.app.backend.repo.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class implementing [UserDetailsService] for loading user details during authentication.
 *
 * @property userRepository repository for managing user entities.
 */
@Service
class UserDetailsServiceImpl(
    val userRepository: UserRepository
): UserDetailsService {

    /**
     * Loads user details by the provided username.
     *
     * Method is called during the authentication process to retrieve [user details][UserDetailsImpl] based on the username.
     *
     * Uses the [UserRepository] to find a user entity by the username and then builds a [UserDetailsImpl] object.
     *
     * @param username username for which to load user details.
     * @return [UserDetails] object containing the user details.
     * @throws UsernameNotFoundException if the user with the provided username is not found.
     */
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val foundUser: User = userRepository.findOneByUsername(username)
        return UserDetailsImpl.build(foundUser)
    }
}