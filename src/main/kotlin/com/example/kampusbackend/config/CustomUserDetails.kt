package com.example.kampusbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetails : UserDetailsService {

    @Value("\${user.username}")
    private lateinit var username: String

    @Value("\${user.password}")
    private lateinit var password: String

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return if (username == this.username)
            User
                .withUsername(this.username)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .password(this.password)
                .roles("USER")
                .build()
        else
            throw UsernameNotFoundException("User Not Found with username: $username")
    }
}