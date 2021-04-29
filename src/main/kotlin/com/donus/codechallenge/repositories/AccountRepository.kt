package com.donus.codechallenge.repositories

import com.donus.codechallenge.models.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : MongoRepository<Account, String> {
    fun findByCpf(cpf: String): Optional<Account>
}