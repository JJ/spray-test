package info.CC_MII

class Apuesta( var local:Int, var visitante: Int, var quien: String ) {
  
  override def toString = s"$quien: $local-$visitante"
}
