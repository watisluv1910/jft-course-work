package com.app.backend.management

import com.app.backend.repo.UserRefreshTokenRepository
import com.app.backend.repo.UserRepository
import org.springframework.boot.actuate.info.Info
import org.springframework.boot.actuate.info.InfoContributor
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class AppUserInfoContributor(
    private val userRepository: UserRepository,
    private val userRefreshTokenRepository: UserRefreshTokenRepository
): InfoContributor {

    override fun contribute(builder: Info.Builder) {
        builder
            .withDetail(
                "app-user.stats",
                mapOf(
                    "count" to userRepository.count(),
                    "recentlyActive" to userRefreshTokenRepository.findAll().count {
                        it.dateExpiration?.before(Timestamp(System.currentTimeMillis())) ?: false
                    }
                )
            )
            .build()
    }
}