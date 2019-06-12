package com.gotoams.wallet.command;

import com.gotoams.wallet.coreapi.CashDepositedEvent;
import com.gotoams.wallet.coreapi.CashWithdrawnEvent;
import com.gotoams.wallet.coreapi.CreateWalletCommand;
import com.gotoams.wallet.coreapi.DepositCashCommand;
import com.gotoams.wallet.coreapi.InsufficientFundsException;
import com.gotoams.wallet.coreapi.WalletCreatedEvent;
import com.gotoams.wallet.coreapi.WithdrawCashCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Wallet {

    @AggregateIdentifier
    private String walletId;
    private int balance;

    public Wallet() {
        // Required by Axon
    }

    @CommandHandler
    public Wallet(CreateWalletCommand command) {
        AggregateLifecycle.apply(new WalletCreatedEvent(command.getWalletId(), command.getBalance()));
    }

    @CommandHandler
    public void handle(DepositCashCommand command) {
        AggregateLifecycle.apply(new CashDepositedEvent(walletId, command.getAmount()));
    }

    @CommandHandler
    public void handle(WithdrawCashCommand command) throws InsufficientFundsException {
        int amount = command.getAmount();
        if (amount > balance) {
            throw new InsufficientFundsException();
        }
        AggregateLifecycle.apply(new CashWithdrawnEvent(walletId, amount));
    }

    @EventSourcingHandler
    public void on(WalletCreatedEvent event) {
        walletId = event.getWalletId();
        balance = event.getBalance();
    }

    @EventSourcingHandler
    public void on(CashDepositedEvent event) {
        balance += event.getAmount();
    }

    @EventSourcingHandler
    public void on(CashWithdrawnEvent event) {
        balance -= event.getAmount();
    }
}
