package com.app.backend.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.boot.logging.DeferredLog
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Component for handling environment configuration through properties files.
 *
 * This class implements [EnvironmentPostProcessor] and [ApplicationListener] to load
 * environment variables from a specified file (e.g., ".env") during application startup.
 *
 * @property log DeferredLog instance for logging.
 */
@Component
class EnvironmentConfiguration :
    EnvironmentPostProcessor,
    ApplicationListener<ApplicationEvent?> {

    /**
     * Post-process the given environment.
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        loadEnvFile(environment)
    }

    /**
     * Loads environment variables from a specified file and adds them to given environment.
     *
     * @param environment the environment to which properties are added.
     */
    private fun loadEnvFile(environment: ConfigurableEnvironment) {
        val envFile = findFile(".env") ?: findFile(".env.example")
        if (envFile != null) {
            try {
                FileInputStream(envFile).use { fileInputStream ->
                    val properties = Properties()
                    properties.load(fileInputStream)
                    val propertySource = PropertiesPropertySource("dotenv", properties)
                    environment.propertySources.addFirst(propertySource)
                    log.info("Loaded environment variables from the environment file: " + envFile.absolutePath)
                }
            } catch (exception: IOException) {
                log.error(
                    "Failed to load environment variables from the environment file: " + envFile.absolutePath,
                    exception
                )
            }
        } else {
            log.info("No environment file was found.")
        }
    }

    /**
     * Finds a file with the specified name in the current and parent directories.
     *
     * @param name name of the file to find.
     * @return [File] instance matching provided name if exists, null otherwise.
     */
    private fun findFile(name: String): File? {
        var currentDirectory = File(System.getProperty("user.dir"))
        var file = File(currentDirectory, name)

        while (!file.exists() && currentDirectory.parentFile != null) {
            currentDirectory = currentDirectory.parentFile
            file = File(currentDirectory, name)
        }

        return if (file.exists()) file else null
    }

    companion object {
        private val log = DeferredLog()
    }

    /**
     * Handles an application event by replaying the log to the [EnvironmentConfiguration] class.
     *
     * @param event the event to respond to.
     */
    override fun onApplicationEvent(event: ApplicationEvent) {
        log.replayTo(EnvironmentConfiguration::class.java)
    }
}