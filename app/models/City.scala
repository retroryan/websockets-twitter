package models

import scala.collection.mutable.{SynchronizedMap, HashMap}
import java.util.UUID
import play.api.libs.json._
import com.sun.codemodel.internal.JStringLiteral

case class City(id: Long, name: String)

object City {

  val cityDB = new HashMap[Long, City] with SynchronizedMap[Long, City]


  def addCity(name: String) = {
    val cityId = UUID.randomUUID().getLeastSignificantBits

    val city = City(cityId, name)
    cityDB(city.id) = city
    city.id
  }

  def getCity(id: Long): City = cityDB(id)

  def cityList = cityDB.values

  implicit object CityFormat extends Format[City] {
    def reads(json: JsValue): JsResult[City] = JsSuccess(
      City(
        id = (json \ "id").as[Long],
        name = (json \ "name").as[String]))

    def writes(city: City) : JsValue =
      Json.obj(
        "id" -> city.id,
        "name" -> city.name
      )
  }

}