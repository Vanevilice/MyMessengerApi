package com.example.messenger

import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class MyMessengerApplication {

    @Bean
    fun init(userRepository: UserRepository) = CommandLineRunner {
        userRepository.save(User("Alex.K", "08184209188", "Calamari911"))
    }
}


fun main(args: Array<String>) {
    SpringApplication.run(MyMessengerApplication::class.java, *args)
}
