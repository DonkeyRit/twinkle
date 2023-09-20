package com.github.donkeyrit.twinkle.bll;

import com.github.donkeyrit.twinkle.bll.models.AuthenticationResult;
import com.github.donkeyrit.twinkle.bll.security.HashManager;
import com.github.donkeyrit.twinkle.bll.services.DefaultLoginService;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.dal.repositories.UserRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import jakarta.persistence.EntityManager;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginServiceTests extends BaseTest {

    private UserRepository userRepository;
    private User user;
    private DefaultLoginService loginService;
    private AuthenticationResult authenticationResult;

    @BeforeClass
    public void setUp() {
        EntityManager session = sessionFactory.createEntityManager();
        userRepository = new UserRepositoryImpl(session);
        loginService = new DefaultLoginService(userRepository);
    }

    @Test
    void registerNewUser(){
        // Arrange
        user = Utils.generateTestUser();
        String userPassword = user.getPassword();
        user.setPassword(HashManager.generateHash(user.getPassword()));

        // Act
        authenticationResult = loginService.signUp(user.getLogin(), userPassword, userPassword);

        // Assert
        Assert.assertNull(authenticationResult.errorMessage());
        Assert.assertTrue(authenticationResult.isSuccessfull());
        Assert.assertEquals(authenticationResult.authenticatedUser().get().getLogin(), user.getLogin());
        Assert.assertEquals(authenticationResult.authenticatedUser().get().getPassword(), user.getPassword());
    }

    @Test
    void registerExistingUser(){
        // Arrange
        user = Utils.generateTestUser();
        String userPassword = user.getPassword();
        user.setPassword(HashManager.generateHash(user.getPassword()));
        userRepository.insert(user);

        // Act
        authenticationResult = loginService.signUp(user.getLogin(), userPassword, userPassword);

        // Assert
        Assert.assertEquals(authenticationResult.errorMessage(), "Login already exist");
        Assert.assertFalse(authenticationResult.isSuccessfull());
    }

    @DataProvider(name = "userData")
    public Object[][] provideUserData() {
        return new Object[][] {
                {"test_user_login", "test_user_password", ""},
                {"test_user_login", "", "test_user_password"},
                {"", "test_user_password", "test_user_password"},
                {"test_user_login", "", ""},
                {"", "test_user_password", ""},
                {"", "", "test_user_password"},
                {"", "", ""}
        };
    }

    @Test(dataProvider = "userData")
    void registerUserWithEmptyFields(String username, String password, String confirmPassword){
        // Arrange
        User user = Utils.generateTestUser();
        user.setPassword(HashManager.generateHash(user.getPassword()));

        // Act
        authenticationResult = loginService.signUp(username, password, confirmPassword);

        // Assert
        Assert.assertEquals(authenticationResult.errorMessage(), "All fields are required.");
        Assert.assertFalse(authenticationResult.isSuccessfull());
    }

    @AfterTest
    void tearDown(){
        if (authenticationResult.authenticatedUser().isPresent()){
            System.out.println("Remove the test user: " + user.getLogin());
            user = authenticationResult.authenticatedUser().orElse(new User());
            userRepository.delete(user);
        }
        else if (user != null) {
            System.out.println("Remove the test user: " + user.getLogin());
            user = userRepository.getByLoginAndPassword(user.getLogin(), user.getPassword()).orElse(new User());
            userRepository.delete(user);
        }
    }
}