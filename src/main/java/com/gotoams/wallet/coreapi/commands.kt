package com.gotoams.wallet.coreapi

data class CreateWalletCommand(
        val walletId: String,
        val balance: Int
)

data class DepositCashCommand(
        val walletId: String,
        val amount: Int
)

data class WithdrawCashCommand(
        val walletId: String,
        val amount: Int
)
