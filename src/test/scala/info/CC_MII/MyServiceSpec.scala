package info.CC_MII

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import spray.json._
import spray.httpx.SprayJsonSupport



class MyServiceSpec extends Specification with Specs2RouteTest with SprayJsonSupport with MyService {
  def actorRefFactory = system
  
  "MyService" should {


    "Devuelve lista de rutas en JSON" in {
      Get() ~> myRoute ~> check {
	response.entity should not be equalTo(None)
	responseAs[String] must contain("routes")
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/kermit") ~> myRoute ~> check {
        handled must beFalse
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put() ~> sealRoute(myRoute) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}
