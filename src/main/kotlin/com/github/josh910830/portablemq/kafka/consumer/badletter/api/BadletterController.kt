package com.github.josh910830.portablemq.kafka.consumer.badletter.api

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterService
import com.github.josh910830.portablemq.kafka.consumer.badletter.api.BadletterProcessResult.Companion.fail
import com.github.josh910830.portablemq.kafka.consumer.badletter.api.BadletterProcessResult.Companion.success
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.internalServerError
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/portable-mq/badletter")
class BadletterController(
    private val badletterService: BadletterService
) {

    @GetMapping("/")
    fun find(@RequestParam(required = false) topic: String?): ResponseEntity<List<Badletter>> {
        val res = badletterService.find(topic)
        return ok(res)
    }

    @GetMapping("/{badletterId}")
    fun findById(@PathVariable badletterId: String): ResponseEntity<Badletter> {
        val res = badletterService.findById(badletterId)
        return ok(res)
    }


    @DeleteMapping("/drop")
    fun drop(@RequestParam badletterId: String): ResponseEntity<BadletterProcessResult> {
        return try {
            badletterService.drop(badletterId)
            ok(success(badletterId))
        } catch (e: Exception) {
            internalServerError().body(fail(badletterId, e))
        }
    }

    @DeleteMapping("/drop-batch")
    fun dropBatch(@RequestParam badletterIds: List<String>): ResponseEntity<List<BadletterProcessResult>> {
        return ok(badletterIds.map { drop(it).body!! })
    }

    @DeleteMapping("/clear")
    fun clear(): ResponseEntity<Void> {
        badletterService.clear()
        return ok().build()
    }

}
