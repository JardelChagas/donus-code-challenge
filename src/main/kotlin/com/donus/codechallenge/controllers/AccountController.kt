package com.donus.codechallenge.controllers

import com.donus.codechallenge.models.Account
import com.donus.codechallenge.models.Transfer
import com.donus.codechallenge.repositories.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
@RequestMapping("/account")
class AccountController ( val repository : AccountRepository) {
    @GetMapping
    fun read() = ResponseEntity.ok(repository.findAll())

    /**
     *CRIAR ok
     *DEPOSITAR ok
     *TRANSFERIR
     */
    @PostMapping("/create")
    fun createAccount(@RequestBody account: Account): ResponseEntity<Account>{
        print(account.balance!!)
        return if ( account.isCPF(account.cpf) && !repository.existsById(account.cpf) && account.validateBalance(account.balance!!) )
            ResponseEntity.ok(repository.save(account))
        else
            ResponseEntity<Account>(HttpStatus.BAD_REQUEST)
    }
    @PutMapping("/deposit/")
    fun deposit(@RequestBody client: Account) : ResponseEntity<Account> {
        if (repository.findByCpf(client.cpf).isPresent){
            val account = repository.findByCpf(client.cpf).get()
            if (account.validateDeposit(client.balance!!)){
                account.deposit(client.balance!!)
                return  ResponseEntity.ok(repository.save(account))
            }else
                return ResponseEntity<Account>(HttpStatus.BAD_REQUEST)
        }else
            return ResponseEntity<Account>(HttpStatus.BAD_REQUEST)
    }


}