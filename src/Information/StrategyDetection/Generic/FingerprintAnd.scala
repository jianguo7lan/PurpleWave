package Information.StrategyDetection.Generic

import Information.StrategyDetection.Fingerprint

class FingerprintAnd(fingerprints: Fingerprint*) extends Fingerprint {
  
  override def matches: Boolean = {
    fingerprints.foreach(_.matches) // Hack -- we need all fingerprints to update each time.
    fingerprints.forall(_.matches)
  }
}
