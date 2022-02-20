package com.transfer.transfer.transfer.rest;

import com.transfer.transfer.account.validation.AccountValidation;
import com.transfer.transfer.transfer.service.TransferService;
import com.transfer.transfer.transfer.validation.TransferValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/funds")
public class TransferRestController {

    private final AccountValidation accountValidation;
    private final TransferValidation transferValidation;
    private final TransferService transferService;

    public TransferRestController(AccountValidation accountValidation,
                                  TransferValidation transferValidation,
                                  TransferService transferService) {
        this.accountValidation = accountValidation;
        this.transferValidation = transferValidation;
        this.transferService = transferService;
    }

    @PostMapping("/{senderUserID}")
    public ResponseEntity<String> transferMoney(@PathVariable("senderUserID") long senderId,
                                                @RequestParam("userID") long receiverID,
                                                @RequestParam("amount") double amount) {
        accountValidation.validateAccountExists(senderId);
        accountValidation.validateAccountExists(receiverID);

        transferValidation.validateTransferAmountIsGreaterThanZero(amount);
        transferService.transferMoneyBetweenAccounts(senderId, receiverID, amount);

        return ResponseEntity.ok("Transfer successful!");
    }
}
