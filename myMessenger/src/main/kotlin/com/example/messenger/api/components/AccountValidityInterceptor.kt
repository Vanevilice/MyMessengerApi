package com.example.messenger.api.components

import com.example.messenger.api.exceptions.UserDeactivatedException
import com.example.messenger.api.repositories.UserRepository
import com.example.messenger.api.models.User
import org.springframework.stereotype.Component
import java.security.Principal
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter    
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccountValidityInterceptor(val userRepository: UserRepository):
    HandlerInterceptorAdapter() {

        @Throws(UserDeactivatedException::class)
        override fun preHandle(request:HttpServletRequest,
        response: HttpServletResponse, handler: Any?): Boolean {
            val principal: Principal? = request.userPrincipal

            if (principal != null) {
                val user = userRepository.findByUsername(principal.name) as User
                if (user.accountStatus == "deactivated") {
                    throw UserDeactivatedException("The account of ths user had been deactivated.")
                }
            }
            return super.preHandle(request, response, handler)
        }
}