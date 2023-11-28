package app.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Data
public class Account {

    private Integer id;
    private String accountNumber;
    private List<Deposit> deposits;// TODO could be lazy loaded
    private List<Withdrawal> withdrawals;// TODO could be lazy loaded
    private List<Transfer> transfers;// TODO could be lazy loaded

    public BigDecimal computeSold() {
        BigDecimal sumDepositAmount = BigDecimal.ZERO;
        BigDecimal sumWithdrawalAmount = BigDecimal.ZERO;
        BigDecimal sumTransferReceiverAmount = BigDecimal.ZERO;
        BigDecimal sumTransferSenderAmount = BigDecimal.ZERO;
        BigDecimal sumOfPayedCommissions = BigDecimal.ZERO;
        if (this.deposits != null) {
            Iterator<Deposit> itr = this.deposits.iterator();
            while (itr.hasNext()) {
                Deposit deposit = itr.next();
                sumDepositAmount = sumDepositAmount.add(deposit.getAmount());
            }
        }
        if (this.withdrawals != null) {
            Iterator<Withdrawal> itr = this.withdrawals.iterator();
            while (itr.hasNext()) {
                Withdrawal withdrawal = itr.next();
                sumWithdrawalAmount = sumWithdrawalAmount.add(withdrawal.getAmount());
            }
        }

        if (this.transfers != null) {
            Iterator<Transfer> itr = this.transfers.iterator();
            while (itr.hasNext()) {
                Transfer transfer = itr.next();
                if (transfer.isSender(this)) {
                    sumTransferSenderAmount = sumTransferSenderAmount.add(transfer.getAmount());
                } else {
                    sumTransferReceiverAmount = sumTransferReceiverAmount.add(transfer.getAmount());

                }
                BigDecimal commissionPercentage = BigDecimal.valueOf(transfer.getCommission());
                BigDecimal payedCommission = commissionPercentage.divide(BigDecimal.valueOf(100)).multiply(transfer.amount);
                sumOfPayedCommissions = sumOfPayedCommissions.add(payedCommission);
            }
        }

        return sumDepositAmount.add(sumTransferReceiverAmount).subtract(sumWithdrawalAmount.add(sumTransferSenderAmount).add(sumOfPayedCommissions));
    }
}
