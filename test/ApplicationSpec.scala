package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import models.City

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the index page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Welcome to Utah Scala")
      }
    }
  }

  //add more extensive testing, right now this just throws an excpetion if the city is not found
  "City DB" should {
      "add a new city" in {
        val cityId = City.addCity("Los Angles")
        val city = City.getCity(cityId)

      }
  }
}