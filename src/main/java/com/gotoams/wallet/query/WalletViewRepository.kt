package com.gotoams.wallet.query

import com.gotoams.wallet.coreapi.WalletView
import org.springframework.data.jpa.repository.JpaRepository

interface WalletViewRepository : JpaRepository<WalletView, String>
