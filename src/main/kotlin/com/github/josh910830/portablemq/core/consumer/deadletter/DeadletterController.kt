package com.github.josh910830.portablemq.core.consumer.deadletter

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/_deadletter")
class DeadletterController(
    private val deadletterHandler: DeadletterHandler,
    private val deadletterStore: DeadletterStore,
    private val redriveTokenManager: RedriveTokenManager
) {

    @PostMapping("/redrive")
    fun redrive(@RequestParam deadletterIds: List<String>): ResponseEntity<Void> {
        deadletterIds.forEach { deadletterHandler.redrive(it) }
        return ResponseEntity.ok(null)
    }

    @PostMapping("/redrive-all")
    fun redrive(): ResponseEntity<Void> {
        val deadletters = deadletterStore.findAllNotRedriven()
        return redrive(deadletters.map { it.id })
    }

    @GetMapping("/redrive-token")
    fun redrive(@RequestParam deadletterId: String, @RequestParam redriveToken: String): ResponseEntity<Void> {
        if (redriveTokenManager.authenticate(deadletterId, redriveToken)) return redrive(listOf(deadletterId))
        return ResponseEntity.badRequest().build()
    }

}
