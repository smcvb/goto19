package com.gotoams.wallet.query;

import org.springframework.stereotype.Component;

@Component
public class WalletViewProjector {

    private final WalletViewRepository repository;

    public WalletViewProjector(WalletViewRepository repository) {
        this.repository = repository;
    }
}
