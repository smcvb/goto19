package com.gotoams.wallet.query;

import com.gotoams.wallet.coreapi.CashDepositedEvent;
import com.gotoams.wallet.coreapi.CashWithdrawnEvent;
import com.gotoams.wallet.coreapi.WalletCreatedEvent;
import com.gotoams.wallet.coreapi.WalletView;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class WalletViewProjector {

    private final WalletViewRepository repository;

    public WalletViewProjector(WalletViewRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(WalletCreatedEvent event) {
        repository.save(new WalletView(event.getWalletId(), event.getBalance()));
    }

    @EventHandler
    public void on(CashWithdrawnEvent event) {
        repository.findById(event.getWalletId())
                  .ifPresent(walletView -> walletView.withdraw(event.getAmount()));
    }

    @EventHandler
    public void on(CashDepositedEvent event) {
        repository.findById(event.getWalletId())
                  .ifPresent(walletView -> walletView.deposit(event.getAmount()));
    }
}
