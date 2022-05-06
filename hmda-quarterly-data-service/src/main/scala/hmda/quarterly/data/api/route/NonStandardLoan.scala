package hmda.quarterly.data.api.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import hmda.quarterly.data.api.dao.NonStandardLoanType._
import hmda.quarterly.data.api.dao.repo.QuarterlyGraphRepo
import hmda.quarterly.data.api.dto.QuarterGraphData._
import hmda.quarterly.data.api.serde.JsonSupport
import monix.execution.CancelableFuture
import monix.execution.Scheduler.Implicits.global

object NonStandardLoan extends JsonSupport {
  private def getVolumeByType(loanType: NonStandardLoanType): CancelableFuture[GraphSummary] =
    QuarterlyGraphRepo.fetchNonStandardLoanVolumeByType(loanType)
      .map(data => {
        val coordinates = data.map(nsl => GraphCoordinate(nsl.quarter, nsl.volume.toString))
        val updated = data.head.lastUpdated
        GraphSummary(loanType.description, updated.toString, coordinates)
      })
      .runToFuture

  val routes: Route = pathPrefix("non_standard_loan") {
    path("balloon") {
      complete(getVolumeByType(BALLOON_PAYMENT))
    } ~ path("reverse") {
      complete(getVolumeByType(REVERSE_MORTGAGE))
    } ~ path("biz") {
      complete(getVolumeByType(BUSINESS_OR_COMMERCIAL))
    } ~ path("interest") {
      complete(getVolumeByType(INTEREST_ONLY_PAYMENT))
    } ~ path("amortization") {
      complete(getVolumeByType(AMORTIZATION))
    }
  }
}
