package com.donus.codechallenge.controllers

import com.donus.codechallenge.models.Account
import com.donus.codechallenge.models.Transfer
import com.donus.codechallenge.repositories.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transfer")
class TransferController(val repository : AccountRepository) {
    @PutMapping
    fun transfer(@RequestBody transfer: Transfer): ResponseEntity<Account> {
        val add = repository.findByCpf(transfer.cpfAddressed).get()
        val send = repository.findByCpf(transfer.cpfSend).get()
        if (send.validateTransfer(transfer.value)){
            send.transfer(add, transfer.value)
            repository.save(add)
            repository.save(send)
            return  ResponseEntity<Account>(HttpStatus.OK)
        }
        return ResponseEntity<Account>(HttpStatus.BAD_REQUEST)
    }
}