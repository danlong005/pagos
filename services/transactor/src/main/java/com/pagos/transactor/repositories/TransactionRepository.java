package com.pagos.transactor.repositories;

import com.pagos.transactor.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> { }
