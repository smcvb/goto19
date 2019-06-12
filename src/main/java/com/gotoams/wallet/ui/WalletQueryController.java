package com.gotoams.wallet.ui;

import com.gotoams.wallet.coreapi.FindAllWalletsQuery;
import com.gotoams.wallet.coreapi.FindWalletQuery;
import com.gotoams.wallet.coreapi.WalletView;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query")
public class WalletQueryController {

    private final QueryGateway queryGateway;

    public WalletQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/wallet/{walletId}")
    public CompletableFuture<WalletView> getOne(@PathVariable("walletId") String walletId) {
        return queryGateway.query(new FindWalletQuery(walletId), ResponseTypes.instanceOf(WalletView.class));
    }

    @GetMapping("/wallets")
    public CompletableFuture<List<WalletView>> getAll() {
        return queryGateway.query(new FindAllWalletsQuery(), ResponseTypes.multipleInstancesOf(WalletView.class));
    }
}
