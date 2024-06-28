package com.hocheol.presentation

import com.hocheol.domain.usecase.login.SignUpUseCase
import com.hocheol.presentation.login.SignUpSideEffect
import com.hocheol.presentation.login.SignUpViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val testId = "hckim"
private const val testUsername = "hocheol"
private const val testPassword = "1234"

class SignUpViewModelTest {

    // Given - 객체 생성
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun setup() {
        signUpUseCase = FakeSignUpUseCase()
        signUpViewModel = SignUpViewModel(signUpUseCase)
    }

    @Test
    fun 입력된_아이디_상태_테스트() {
        Assert.assertEquals(signUpViewModel.container.stateFlow.value.id, "")

        // When - 액션, 텍스트 입력
        signUpViewModel.onIdChange(testId)

        // Then - 검증
        Assert.assertEquals(signUpViewModel.container.stateFlow.value.id, testId)
    }

    @Test
    fun 입력된_패스워드_상태_테스트() {
        Assert.assertEquals(signUpViewModel.container.stateFlow.value.password, "")
        signUpViewModel.onPasswordChange(testPassword)
        Assert.assertEquals(signUpViewModel.container.stateFlow.value.password, testPassword)
    }

    @Test
    fun 회원가입_버튼_클릭_실패_테스트() = runTest {
        val vm = SignUpViewModel(signUpUseCase)
        vm.onIdChange(testId)
        vm.onPasswordChange(testPassword)

        vm.onSignUpClick()

        val sideEffect = vm.container.sideEffectFlow.first()
        Assert.assertEquals((sideEffect as? SignUpSideEffect.Toast)?.message, "패스워드를 다시 확인해주세요.")
    }

    @Test
    fun 회원가입_버튼_클릭_성공_테스트() = runTest {
        val vm = SignUpViewModel(signUpUseCase)
        vm.onIdChange(testId)
        vm.onUsernameChange(testUsername)
        vm.onPasswordChange(testPassword)
        vm.onRepeatPasswordChange(testPassword)

        vm.onSignUpClick()

        val sideEffect = vm.container.sideEffectFlow.first()
        Assert.assertTrue(sideEffect is SignUpSideEffect.NavigateToLoginScreen)
    }

    class FakeSignUpUseCase : SignUpUseCase {
        override suspend fun invoke(
            id: String,
            username: String,
            password: String
        ): Result<Boolean> = runCatching {
            id == testId && username == testUsername && password == testPassword
        }
    }
}