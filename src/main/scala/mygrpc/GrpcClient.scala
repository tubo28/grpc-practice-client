package mygrpc

import io.grpc.ManagedChannelBuilder
import proto.user_service.{UserRequest, UsersServiceGrpc}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object GrpcClient {
  def main(args: Array[String]): Unit = {
    val channel = ManagedChannelBuilder.forAddress("0.0.0.0", 50001)
      .usePlaintext(true)
      .build()
    val client = UsersServiceGrpc.stub(channel)
    val request = UserRequest(0)
    for (i <- 1 to 3) {
      val response = client.getUsers(request)
      response onComplete {
        case Success(user) => println(user)
        case Failure(error) => println(error)
      }
    }
    println("*** end ***")
  }
}
