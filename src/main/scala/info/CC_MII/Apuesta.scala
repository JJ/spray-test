package info.CC_MII

class Apuesta( var local:String, var visitante: String, var quien: String ) {
  
  override def toString = s"$quien: $local-$visitanted"
}
