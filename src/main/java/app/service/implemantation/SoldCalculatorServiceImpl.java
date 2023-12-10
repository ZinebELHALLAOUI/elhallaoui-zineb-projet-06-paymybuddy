package app.service.implemantation;

import app.dal.entity.Account;
import app.dal.entity.Deposit;
import app.dal.entity.Transfer;
import app.dal.entity.Withdrawal;
import app.service.SoldCalculatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;

@Service
public class SoldCalculatorServiceImpl implements SoldCalculatorService {
    @Override
    public BigDecimal calculate(Account account) {
        BigDecimal sumDepositAmount = BigDecimal.ZERO;
        BigDecimal sumWithdrawalAmount = BigDecimal.ZERO;
        BigDecimal sumTransferReceiverAmount = BigDecimal.ZERO;
        BigDecimal sumTransferSenderAmount = BigDecimal.ZERO;
        BigDecimal sumOfPayedCommissions = BigDecimal.ZERO;
        if (account.getDeposits() != null) {
            Iterator<Deposit> itr = account.getDeposits().iterator();
            while (itr.hasNext()) {
                Deposit deposit = itr.next();
                sumDepositAmount = sumDepositAmount.add(deposit.getAmount());
            }
        }
        if (account.getWithdrawals() != null) {
            Iterator<Withdrawal> itr = account.getWithdrawals().iterator();
            while (itr.hasNext()) {
                Withdrawal withdrawal = itr.next();
                sumWithdrawalAmount = sumWithdrawalAmount.add(withdrawal.getAmount());
            }
        }

        if (account.getTransfers() != null) {
            Iterator<Transfer> itr = account.getTransfers().iterator();
            while (itr.hasNext()) {
                Transfer transfer = itr.next();
                if (transfer.isSender(account)) {
                    sumTransferSenderAmount = sumTransferSenderAmount.add(transfer.getAmount());
                } else {
                    sumTransferReceiverAmount = sumTransferReceiverAmount.add(transfer.getAmount());

                }
                BigDecimal commissionPercentage = BigDecimal.valueOf(transfer.getCommission());
                BigDecimal payedCommission = commissionPercentage.divide(BigDecimal.valueOf(100)).multiply(transfer.getAmount());
                sumOfPayedCommissions = sumOfPayedCommissions.add(payedCommission);
            }
        }

        return sumDepositAmount.add(sumTransferReceiverAmount).subtract(sumWithdrawalAmount.add(sumTransferSenderAmount).add(sumOfPayedCommissions));
    }

}
