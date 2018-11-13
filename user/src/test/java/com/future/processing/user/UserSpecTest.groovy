package com.future.processing.user

import com.future.processing.user.domain.UserConfiguration
import com.future.processing.user.domain.UserFacade
import com.future.processing.user.domain.dto.UserDto
import com.future.processing.user.domain.dto.post.PostUserDataDto
import com.future.processing.user.domain.dto.post.PostUserDto
import com.future.processing.user.domain.dto.post.PostWalletDto
import com.future.processing.user.domain.dto.put.PutUserDataDto
import com.future.processing.user.domain.dto.put.PutUserDto
import com.future.processing.user.domain.dto.stream.PaymentDto
import com.future.processing.user.domain.dto.stream.SaleDto
import com.future.processing.user.domain.model.User

import org.mockito.MockitoAnnotations

import spock.lang.Specification

import static com.future.processing.user.domain.configuration.security.SecurityConfiguration.encoder;

class UserSpecTest extends Specification implements SampleStockItems, SampleUsersDtos {


    UserFacade userFacade

    def setup () {
        MockitoAnnotations.initMocks(this)

        userFacade = new UserConfiguration().buildFacade()

        userFacade.createUser(bertie)
    }
    def "should create user and persist it to database" () {

        given:

            PostUserDataDto userDataDto = new PostUserDataDto("McRay", "Bertie", "bertie@gmail.com", "123231231")
            PostWalletDto postWalletDto = new PostWalletDto(1l, "50", Arrays.asList(coin,fp))

            PostUserDto dto = PostUserDto.builder()
                    .id(1)
                    .username("username")
                    .password("password")
                    .userData(userDataDto)
                    .wallet(postWalletDto)
                    .build()

        when:

            userFacade.createUser(dto)

        then:

         userFacade.showUser(dto.id) != null
    }

    def "should process sale" () {

        given:

            SaleDto saleDto = SaleDto.builder()
                    .stockName("Future Stock")
                    .amount(2)
                    .companyName("FP")
                    .userId(bertie.id)
                    .build()

            int amountBefore = userFacade.findById(bertie.id).wallet.stockItems.get(1).amount

        when:

            userFacade.processSale(saleDto)

        then:

            UserDto user = userFacade.findById(1l)

            user.wallet.balance == saleDto.amount * 13.23  + new BigDecimal(bertie.wallet.balance)
            user.wallet.stockItems.get(1).amount == amountBefore - saleDto.amount
    }

    def "should process user's payment" () {

        given:

            PaymentDto paymentDto = PaymentDto.builder()
                    .userId(bertie.getId())
                    .amount(10)
                    .payment(52.50)
                    .stockItem(fp)
                    .build()

            int amountBefore = userFacade.findById(bertie.id).wallet.stockItems.get(1).amount

        when :

            userFacade.processPayment(paymentDto)

        then :

            UserDto user = userFacade.findById(bertie.id)

            user.wallet.balance == new BigDecimal(bertie.wallet.balance) - paymentDto.payment
            user.wallet.stockItems.get(1).amount == amountBefore + paymentDto.amount
    }

    def "should update user data" () {

        given:

            PutUserDataDto putUserDataDto = PutUserDataDto.builder()
                    .lastName("someName")
                    .firstName("blabla")
                    .email(bertie.userData.email)
                    .phoneNumber("12342341")
                    .build()

            PostWalletDto postWalletDto = new PostWalletDto(1l, "51515", bertie.wallet.stockItems)

            PutUserDto putUserDto = PutUserDto.builder()
                    .id(1l)
                    .wallet(postWalletDto)
                    .username(bertie.username)
                    .password("someNewPassword")
                    .userData(putUserDataDto)
                    .build()

        when:

            userFacade.updateUser(putUserDto)

        then:

            User user = userFacade.findUserById(putUserDto.id)

            encoder().matches(putUserDto.password, user.password)

            user.userData.firstName == putUserDataDto.firstName
            user.userData.lastName == putUserDataDto.lastName
            user.userData.phoneNumber == putUserDataDto.phoneNumber
            user.wallet.balance == new BigInteger(postWalletDto.balance)



    }
}
