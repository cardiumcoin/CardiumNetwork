package com.wavesplatform.state.patch

import com.wavesplatform.account.Address
import com.wavesplatform.common.utils.EitherExt2
import com.wavesplatform.state.{Blockchain, Diff, LeaseBalance, Portfolio}

object CancelInvalidTx extends PatchAtHeight('L' -> 450000)  {

  def apply(blockchain: Blockchain): Diff = {
    val addr1 = Address.fromString("3JcQHUDAzkprtFHX8DskQCys3upBYhPeJQq").explicitGet()
    val addr2 = Address.fromString("3JbHxyVNbEEJXMDuuR9kPeTmXn5BCDBSQp4").explicitGet()
    val addr3 = Address.fromString("3JqAYiRnuiJxdMVmdTUsxuTV39LXHR5JWXk").explicitGet()
    val addr4 = Address.fromString("3JtJ5Kf3XuAUiMhtDQasSPdpaG6X5WLa8cE").explicitGet()
    val addr5 = Address.fromString("3JwSzCsBpTZtqxkfdj3KuZgZJ33BcTBnqQr").explicitGet()

    val bal1 = 120000004131663L
    val bal2 = 50000003846140L

    val pfs = Map(
      addr1 -> Portfolio(-bal1, LeaseBalance(0L, 0L), Map.empty),
      addr2 -> Portfolio(-bal2, LeaseBalance(0L, 0L), Map.empty),
      addr3 -> Portfolio(bal1 + bal2, LeaseBalance(0L, 0L), Map.empty),
      addr4 -> Portfolio(110000000L),
      addr5 -> Portfolio(108000000L)
    )

    Diff(portfolios = pfs)
  }

}

object CancelInvalidTx2 extends PatchAtHeight('L' -> 457100)  {
  def apply(blockchain: Blockchain): Diff = {
    val addr1 = Address.fromString("3JreB3JjQJgFfuz6ntFt76eTDUyv7hxdzMk").explicitGet()
    val pfs = Map(
      addr1 -> Portfolio(9593994L)
    )
    Diff(portfolios = pfs)
  }
}
