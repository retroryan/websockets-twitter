package controllers


import play.api._
import libs.json.JsValue
import play.api.libs.json.Json._

import play.api.mvc._

import play.api.libs.iteratee._
import play.api.libs.concurrent._

import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits._

object CountdownClock extends Controller {

  /**
   * A String Enumerator producing a formatted Time message every 100 millis.
   * A callback enumerator is pure an can be applied on several Iteratee.
   */
  lazy val clock: Enumerator[JsValue] = {

    import java.util._
    import java.text._

    val dateFormat = new SimpleDateFormat("HH mm ss")

    Enumerator.fromCallback {
      () =>
        Promise.timeout( Some(toJson(dateFormat.format(new Date))), 100 milliseconds)
    }
  }

  def countdown = Action {
    implicit request =>
      Ok(views.html.countdown(request))
  }

  def socketClock = WebSocket.using[JsValue] {

    //should this be implicit?
    request =>

    // Log events to the console
      val in = Iteratee.foreach[JsValue](inVal => {
        Logger.info("recieved value: " + inVal)
      }).map {
        finalVal =>
          Logger.info("final value " + finalVal)
      }

      (in, clock)
  }
}
