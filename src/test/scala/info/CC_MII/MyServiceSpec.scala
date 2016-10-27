package info.CC_MII

import org.specs2.mutable.Specification

import spray.httpx.SprayJsonSupport
import spray.testkit.Specs2RouteTest
import spray.http._

import StatusCodes._

import scala.concurrent._
import scala.concurrent.duration._

class MyServiceSpec extends Specification with Specs2RouteTest with SprayJsonSupport with MyService {
  def actorRefFactory = system

  "MyService" should {

    "Devuelve lista de rutas en JSON" in {
      Get() ~> myRoute ~> check {
	response.entity should not be equalTo(None)
	responseAs[String] must contain("routes")
      }
    }

    "Crea y lee apuestas correctamente" in {

      "Crea apuestas correctamente" in {
	Put( "/0/2/Alguien") ~> myRoute ~> check {
	  response.status should be equalTo OK
	  response.entity should not be equalTo(None)
	  responseAs[String] must contain("Alguien")
	}

	Put( "/3/0/Menda") ~> myRoute ~> check {
	  response.status should be equalTo OK
	  response.entity should not be equalTo(None)
	  responseAs[String] must contain("Menda")
	}
      }

      "Lee apuestas correctamente" in {
  Apuestas.add(Apuesta(0,2,"Alguien"))
  Apuestas.add(Apuesta(3,0,"Menda"))

	Get( "/Alguien") ~> myRoute ~> check {
	  response.status should be equalTo OK
	  response.entity should not be equalTo(None)
	  responseAs[String] must contain("Alguien").eventually
	}

	Get( "/Menda") ~> myRoute ~> check {
	  response.status should be equalTo OK
	  response.entity should not be equalTo(None)
	  responseAs[String] must contain("Menda")
	}
      }
    }

    "GET no funciona con otras rutas" in {
      Get("/kermit/1") ~> myRoute ~> check {
	handled must beFalse
      }
    }

    "return a MethodNotAllowed error for POST requests to the root path" in {
      Post() ~> sealRoute(myRoute) ~> check {
	status === MethodNotAllowed
	responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }

  }
}
