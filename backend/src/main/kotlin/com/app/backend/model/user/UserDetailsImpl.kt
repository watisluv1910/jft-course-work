package com.app.backend.model.user

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * Custom implementation of Spring Security's [UserDetails].
 *
 *
 * Includes the user's id, username, email, password and the list of roles assigned to the user.
 */
class UserDetailsImpl: UserDetails {

    var id: Long? = null

    @get: JvmName("get_username")
    var username: String? = null

    var email: String? = null

    @get: JvmName("get_password")
    @field:JsonIgnore
    var password: String? = null

    @get: JvmName("get_authorities")
    var authorities: MutableCollection<out GrantedAuthority>? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities!!

    override fun getPassword(): String = password!!

    override fun getUsername(): String = username!!

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {
        fun build(user: User): UserDetailsImpl {
            val authorities: MutableList<GrantedAuthority> = user
                .roles
                .map { SimpleGrantedAuthority(it.roleName.name) }
                .toMutableList()

            return UserDetailsImpl().apply {
                id = user.id
                username = user.username
                email = user.userEmail
                password = user.password
                this.authorities = authorities
            }
        }
    }
}
