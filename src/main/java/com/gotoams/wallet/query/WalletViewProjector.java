package com.gotoams.wallet.query;

import com.gotoams.wallet.coreapi.CashDepositedEvent;
import com.gotoams.wallet.coreapi.CashWithdrawnEvent;
import com.gotoams.wallet.coreapi.FindAllWalletsQuery;
import com.gotoams.wallet.coreapi.FindWalletQuery;
import com.gotoams.wallet.coreapi.WalletCreatedEvent;
import com.gotoams.wallet.coreapi.WalletView;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @QueryHandler
    public WalletView handle(FindWalletQuery query) {
        return repository.findById(query.getWalletId()).orElse(null);
    }

    @QueryHandler
    public List<WalletView> handle(FindAllWalletsQuery query) {
        return repository.findAll();
    }
}
