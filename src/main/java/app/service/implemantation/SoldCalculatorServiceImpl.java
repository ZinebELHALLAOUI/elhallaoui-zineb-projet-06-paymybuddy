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
            for (Deposit deposit : account.getDeposits()) {
                sumDepositAmount = sumDepositAmount.add(deposit.getAmount());
            }
        }
        if (account.getWithdrawals() != null) {
            for (Withdrawal withdrawal : account.getWithdrawals()) {
                sumWithdrawalAmount = sumWithdrawalAmount.add(withdrawal.getAmount());
            }
        }

        if (account.getTransfers() != null) {
            for (Transfer transfer : account.getTransfers()) {
                if (transfer.isSender(account)) {
                    sumTransferSenderAmount = sumTransferSenderAmount.add(transfer.getAmount());
                    BigDecimal commissionPercentage = BigDecimal.valueOf(transfer.getCommission());
                    BigDecimal payedCommission = commissionPercentage.divide(BigDecimal.valueOf(100)).multiply(transfer.getAmount());
                    sumOfPayedCommissions = sumOfPayedCommissions.add(payedCommission);
                } else {
                    sumTransferReceiverAmount = sumTransferReceiverAmount.add(transfer.getAmount());
                }
            }
        }

        return sumDepositAmount.add(sumTransferReceiverAmount).subtract(sumWithdrawalAmount.add(sumTransferSenderAmount).add(sumOfPayedCommissions));
    }

}
