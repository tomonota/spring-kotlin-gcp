package com.spring.gcp.config.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class FirebaseAuthInstance (
    @Autowired
    private val environment: Environment
) {
    lateinit var instance: FirebaseAuth

    init {
        instance = getFirebaseAuthInstance()
    }

    private fun getFirebaseAuthInstance(): FirebaseAuth {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .setProjectId(environment.getProperty("projectId"))
            .build()
        val apps = FirebaseApp.getApps()
        val firebaseApp = if (apps.size == 0) {
            FirebaseApp.initializeApp(options)
        } else {
            apps[0]
        }
        return FirebaseAuth.getInstance(firebaseApp)
    }
}
