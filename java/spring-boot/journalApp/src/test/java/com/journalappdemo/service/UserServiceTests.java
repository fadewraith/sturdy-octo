package com.journalappdemo.service;

import com.journalappdemo.entity.User;
import com.journalappdemo.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testFindByUserName() {
        assertEquals(4, 2 + 2);
        assertTrue(5 > 3);
        assertNotNull(userRepository.findByUserName(null));
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testFindByUserNameArgs(User user) {
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = { // @EnumSource
            "",
            "",
            ""
    })
    public void testFindByUserNameParams(String name) {
        assertNotNull(userRepository.findByUserName(name), "failed for name => " + name);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "2, 10, 12",
            "3, 3, 9"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

//    @BeforeEach
//    @BeforeAll
//    @AfterEach
//    @AfterAll
}
