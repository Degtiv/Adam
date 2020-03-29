package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.repository.OperationRepository;
import space.deg.adam.repository.TransactionRepository;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private TransactionService transactionService;

    public void addOperation(Operation operation) {
        operationRepository.save(operation);
    }

    public void deleteOperation(Operation operation) {
        operationRepository.delete(operation);
    }
}
