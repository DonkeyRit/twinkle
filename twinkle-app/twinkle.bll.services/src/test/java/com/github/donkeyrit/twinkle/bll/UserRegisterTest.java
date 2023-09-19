package com.github.donkeyrit.twinkle.bll;

import com.github.donkeyrit.twinkle.bll.models.AuthenticationResult;
import com.github.donkeyrit.twinkle.bll.security.HashManager;
import com.github.donkeyrit.twinkle.bll.services.DefaultLoginService;
import com.github.donkeyrit.twinkle.bll.services.contracts.LoginService;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.dal.repositories.UserRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import jakarta.persistence.EntityManager;
import org.testng.Assert;
import org.testng.annotations.*;

public class UserRegisterTest extends BaseTest {

    private UserRepository userRepository;
    private User user;

    @BeforeClass
    public void setUp() {
        EntityManager session = sessionFactory.createEntityManager();
        userRepository = new UserRepositoryImpl(session);
    }

    @Test
    void registerNewUser(){
        // Arrange
        user = Utils.generateTestUser();
        String userPassword = user.getPassword();
        user.setPassword(HashManager.generateHash(user.getPassword()));

        // Act
        LoginService loginService = new DefaultLoginService(userRepository);
        AuthenticationResult authResult = loginService.signUp(user.getLogin(), userPassword, userPassword);

        // Assert
        Assert.assertNull(authResult.errorMessage());
        Assert.assertTrue(authResult.isSuccessfull());
    }

    @Test
    void registerExistenceUser(){
        // Arrange
        user = Utils.generateTestUser();
        String userPassword = user.getPassword();
        user.setPassword(HashManager.generateHash(user.getPassword()));
        userRepository.insert(user);

        // Act
        LoginService loginService = new DefaultLoginService(userRepository);
        AuthenticationResult authResult = loginService.signUp(user.getLogin(), userPassword, userPassword);

        // Assert
        Assert.assertEquals(authResult.errorMessage(), "Login already exist");
        Assert.assertFalse(authResult.isSuccessfull());
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
        user = Utils.generateTestUser();
        user.setPassword(HashManager.generateHash(user.getPassword()));

        // Act
        LoginService loginService = new DefaultLoginService(userRepository);
        AuthenticationResult authResult = loginService.signUp(username, password, confirmPassword);

        // Assert
        Assert.assertEquals(authResult.errorMessage(), "All fields are required.");
        Assert.assertFalse(authResult.isSuccessfull());
    }

    @AfterTest
    void tearDown(){
        if (user != null) {
            System.out.println("Remove the test user: " + user.getLogin());
            var temp = userRepository.getByLoginAndPassword(user.getLogin(), user.getPassword());
            user = temp.orElse(new User());

            userRepository.remove(user);
        }
    }
}