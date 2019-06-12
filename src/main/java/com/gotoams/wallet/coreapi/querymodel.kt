package com.gotoams.wallet.coreapi

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class WalletView(
        @Id val walletId: String? = null,
        var balance: Int = 0
) {
    fun withdraw(amount: Int) = let { balance -= amount }

    fun deposit(amount: Int) = let { balance += amount }
}