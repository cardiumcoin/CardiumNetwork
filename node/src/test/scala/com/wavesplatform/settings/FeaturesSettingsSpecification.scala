package com.wavesplatform.settings

import com.typesafe.config.ConfigFactory
import com.wavesplatform.test.FlatSpec
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

class FeaturesSettingsSpecification extends FlatSpec {
  "FeaturesSettings" should "read values" in {
    val config = ConfigFactory.parseString("""
        |CARDIUM {
        |  features {
        |    auto-shutdown-on-unsupported-feature = yes
        |    supported = [123,124,135]
        |  }
        |}
      """.stripMargin).resolve()

    val settings = config.as[FeaturesSettings]("CARDIUM.features")

    settings.autoShutdownOnUnsupportedFeature should be(true)
    settings.supported shouldEqual List(123, 124, 135)
  }
}
