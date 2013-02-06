package controllers

import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

import play.api.libs.json.Json._
import models.City
import play.api.data.Form

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Two was here"))
  }

  val cityForm = Form(single("name" -> text))

  def sample = Action {
    Ok(views.html.sample(cityForm))
  }

  def addCity = Action {
    implicit request =>
      cityForm.bindFromRequest.value map {
        name =>
          val cityId = City.addCity(name)
          Redirect(routes.Application.sample())
      } getOrElse BadRequest

    Ok(views.html.sample(cityForm))
  }

  def getCities() = Action {
    Ok(toJson(City.cityList))
  }

  def addCityJson = Action(parse.json) {
    request =>
      val name:String = (request.body \ "name").as[String]
      City.addCity(name)
      Ok(toJson("added city"))
  }



}