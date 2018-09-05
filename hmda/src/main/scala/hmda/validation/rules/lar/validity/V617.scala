package hmda.validation.rules.lar.validity

import hmda.model.filing.lar.LoanApplicationRegister
import hmda.validation.dsl.PredicateCommon._
import hmda.validation.dsl.PredicateSyntax._
import hmda.validation.dsl.ValidationResult
import hmda.validation.rules.EditCheck

object V617 extends EditCheck[LoanApplicationRegister] {
  override def name: String = "V617"

  override def apply(lar: LoanApplicationRegister): ValidationResult = {
    lar.loan.amount is greaterThanOrEqual(0)
  }
}
