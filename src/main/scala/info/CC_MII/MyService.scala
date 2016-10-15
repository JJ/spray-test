package info.CC_MII

import scala.collection.mutable.Map

import akka.actor.Actor

import spray.routing._
import spray.http._
import spray.json._
import spray.json.DefaultJsonProtocol._

import spray.httpx.SprayJsonSupport
import spray.httpx.SprayJsonSupport._


import MediaTypes._

import info.CC_MII._

import DefaultJsonProtocol._ 

case class Apuesta( var local:Int, var visitante: Int, var quien: String ) {
  
  override def toString = s"$quien: $local-$visitante"
}

object MasterJsonProtocol extends DefaultJsonProtocol {
  implicit val apuestaFormat = jsonFormat3(Apuesta)
}


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)

}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  import MasterJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  // Cada servidor lleva una porra
  private val apuestas = scala.collection.mutable.Map[String, Apuesta]()

  // Añade nueva apuesta
  def add( apuesta: Apuesta ): Apuesta = {
    this.apuestas += ( apuesta.quien -> apuesta )
    return apuesta
  }

  val myRoute =
    pathSingleSlash {
      get {
        complete ( "routes" -> "get,post")
      }  
    } ~
  path( IntNumber / IntNumber / Segment ) { (local,visitante,quien) =>
    put {
      val apuesta = new Apuesta(local,visitante,quien)
      this.add( apuesta )
      complete( apuesta )
    } 
  } ~
  path( Segment ) { (quien) =>
    get {
      val esta_apuesta = this.apuestas( quien )
      complete( esta_apuesta )
    } 
  }
  
}
