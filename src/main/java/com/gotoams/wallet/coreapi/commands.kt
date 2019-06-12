package com.gotoams.wallet.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateWalletCommand(
        val walletId: String,
        val balance: Int
)

data class DepositCashCommand(
        @TargetAggregateIdentifier val walletId: String,
        val amount: Int
)

data class WithdrawCashCommand(
        @TargetAggregateIdentifier val walletId: String,
        val amount: Int
)
