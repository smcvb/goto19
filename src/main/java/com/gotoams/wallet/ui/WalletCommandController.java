package com.gotoams.wallet.ui;

import com.gotoams.wallet.coreapi.CreateWalletCommand;
import com.gotoams.wallet.coreapi.DepositCashCommand;
import com.gotoams.wallet.coreapi.WithdrawCashCommand;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/command")
public class WalletCommandController {

    private final CommandGateway commandGateway;

    public WalletCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void createWallet() {
        String walletId = UUID.randomUUID().toString();

        commandGateway.send(new CreateWalletCommand(walletId, 1000), LoggingCallback.INSTANCE);
        commandGateway.send(new DepositCashCommand(walletId, 42), LoggingCallback.INSTANCE);
        commandGateway.send(new DepositCashCommand(walletId, 42), LoggingCallback.INSTANCE);
        commandGateway.send(new WithdrawCashCommand(walletId, 84), LoggingCallback.INSTANCE);
    }

    @PostMapping("/funds")
    public void insufficientFunds() {
        String walletId = UUID.randomUUID().toString();

        commandGateway.send(new CreateWalletCommand(walletId, 500), LoggingCallback.INSTANCE);
        commandGateway.send(new WithdrawCashCommand(walletId, 1337), LoggingCallback.INSTANCE);
    }
}
