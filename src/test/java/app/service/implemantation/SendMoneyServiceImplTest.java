package app.service.implemantation;

import app.controller.transfer.dto.TransferRequest;
import app.dal.entity.Account;
import app.dal.entity.Deposit;
import app.dal.entity.Transfer;
import app.dal.entity.User;
import app.dal.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendMoneyServiceImplTest {


    @Mock
    private UserService userService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private SendMoneyServiceImpl sendMoneyService;


    @Test
    public void should_throw_exception_when_receiver_user_does_not_exist() {
        //given
        when(userService.isUserExist(1)).thenReturn(false);
        final TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAccountReceiverId(1);
        transferRequest.setAmount(BigDecimal.valueOf(200));
        //when then
        assertThatThrownBy(() -> this.sendMoneyService.sendMoney(transferRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Receiver does not exist");
    }

    @Test
    public void should_throw_exception_when_current_user_account_has_sold_100_but_send_200() {
        //given
        when(userService.isUserExist(1)).thenReturn(true);
        User currentUser = getAUserWithSold100();
        when(userService.getCurrentUser()).thenReturn(currentUser);
        final TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAccountReceiverId(1);
        transferRequest.setAmount(BigDecimal.valueOf(200));
        //when then
        assertThatThrownBy(() -> this.sendMoneyService.sendMoney(transferRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Insufficient sold");
    }

    @Test
    public void should_save_transfer_when_sold_is_enough_and_receiver_user_is_exist() {
        //given
        when(userService.isUserExist(1)).thenReturn(true);
        User currentUser = getAUserWithSold100();
        when(userService.getCurrentUser()).thenReturn(currentUser);
        Instant instant = Clock.systemDefaultZone().instant();
        when(clock.instant()).thenReturn(instant);
        final TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAccountReceiverId(1);
        transferRequest.setAmount(BigDecimal.valueOf(50));

        //when
        this.sendMoneyService.sendMoney(transferRequest);

        //then
        Transfer transfer = new Transfer(currentUser.getAccount().getId(), 1, BigDecimal.valueOf(100), 0.50D, instant);
        verify(transferRepository, Mockito.times(1)).save(transfer);
    }

    private User getAUserWithSold100() {
        final User user = new User();
        user.setId(new Random().nextInt(100) + 1);
        user.setFirstName(user.getId() + "firstName");
        user.setLastName(user.getId() + "lastName");
        user.setEmail(user.getId() + "mail@mail.com");
        Account account = new Account();
        Deposit deposit1 = new Deposit();
        deposit1.setAmount(BigDecimal.valueOf(100));
        account.setDeposits(List.of(deposit1));
        account.setId(new Random().nextInt(100) + 1);
        user.setAccount(account);
        return user;
    }

}