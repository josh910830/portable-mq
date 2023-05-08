package com.github.josh910830.portablemq.core.consumer.deadletter.api

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterService
import com.github.josh910830.portablemq.core.consumer.deadletter.api.DeadletterProcessResult.Companion.fail
import com.github.josh910830.portablemq.core.consumer.deadletter.api.DeadletterProcessResult.Companion.success
import com.github.josh910830.portablemq.core.exception.PortableMQException
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/portable-mq/deadletter")
class DeadletterController(
    private val deadletterService: DeadletterService
) {

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<Deadletter>> {
        val res = deadletterService.findAll()
        return ok(res)
    }

    @GetMapping("/{deadletterId}")
    fun findById(@PathVariable deadletterId: String): ResponseEntity<Deadletter> {
        val res = deadletterService.findById(deadletterId)
        return ok(res)
    }

    @GetMapping("/not-redriven")
    fun findNotRedriven(): ResponseEntity<List<Deadletter>> {
        val res = deadletterService.findNotRedriven()
        return ok(res)
    }


    @PostMapping("/redrive")
    fun redrive(@RequestParam deadletterId: String): ResponseEntity<DeadletterProcessResult> {
        return try {
            deadletterService.redrive(deadletterId)
            ok(success(deadletterId))
        } catch (e: Exception) {
            internalServerError().body(fail(deadletterId, e))
        }
    }

    @PostMapping("/redrive-batch")
    fun redriveBatch(@RequestParam deadletterIds: List<String>): ResponseEntity<List<DeadletterProcessResult>> {
        return ok(deadletterIds.map { redrive(it).body!! })
    }

    @PostMapping("/redrive-all")
    fun redriveAll(): ResponseEntity<List<DeadletterProcessResult>> {
        val deadletterIds = deadletterService.findNotRedriven().map { it.id }
        return redriveBatch(deadletterIds)
    }

    @GetMapping("/redrive-token")
    fun redriveToken(@RequestParam deadletterId: String, @RequestParam redriveToken: String): ResponseEntity<DeadletterProcessResult> {
        if (deadletterService.authenticate(deadletterId, redriveToken)) return redrive(deadletterId)
        return badRequest().body(fail(deadletterId, PortableMQException("Invalid redrive token.")))
    }


    @DeleteMapping("/drop")
    fun drop(@RequestParam deadletterId: String): ResponseEntity<DeadletterProcessResult> {
        return try {
            deadletterService.drop(deadletterId)
            ok(success(deadletterId))
        } catch (e: Exception) {
            internalServerError().body(fail(deadletterId, e))
        }
    }

    @DeleteMapping("/drop-batch")
    fun dropBatch(@RequestParam deadletterIds: List<String>): ResponseEntity<List<DeadletterProcessResult>> {
        return ok(deadletterIds.map { drop(it).body!! })
    }

    @DeleteMapping("/drop-redriven")
    fun dropRedriven(): ResponseEntity<List<DeadletterProcessResult>> {
        val deadletterIds = deadletterService.findRedriven().map { it.id }
        return dropBatch(deadletterIds)
    }

    @DeleteMapping("/clear")
    fun clear(): ResponseEntity<Void> {
        deadletterService.clear()
        return ok().build()
    }

}
