package com.example.messenger.api.controllers

import com.example.messenger.api.components.ConversationAssembler
import com.example.messenger.api.helpers.objects.ConversationListVO
import com.example.messenger.api.helpers.objects.ConversationVO
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import com.example.messenger.api.services.ConversationServiceImpl
import javax.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/conversations")
class ConversationController(val conversationService: ConversationServiceImpl,
                             val conversationAssembler: ConversationAssembler,
                             val userRepository: UserRepository) {

    @GetMapping
    fun list(request: HttpServletRequest): ResponseEntity<ConversationListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val conversations = conversationService.listUserConversations(user.id)
        return ResponseEntity.ok(conversationAssembler.toConversationListVO(conversations as ArrayList<Conversation>, user.id))
    }


    @GetMapping
    @RequestMapping("/{conversation_id}")
    fun show(@PathVariable(name = "conversation_id") conversationId: Long, request: HttpServletRequest): ResponseEntity<ConversationVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val conversationThread = conversationService.retrieveThread(conversationId)
        return ResponseEntity.ok(conversationAssembler.toConversationVO(conversationThread, user.id))
    }
}