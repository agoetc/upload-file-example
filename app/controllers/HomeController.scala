package controllers

import java.nio.file.Paths

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action(parse.multipartFormData) { implicit request =>
    request.body.file("image") match {
      case Some(value) =>
        val filename = value.filename
        value.ref.moveTo(Paths.get(s"./tmp/picture$filename"), replace = true)
        Ok
      case None => BadRequest
    }
  }
}
