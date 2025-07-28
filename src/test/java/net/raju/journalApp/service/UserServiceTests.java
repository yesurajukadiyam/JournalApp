package net.raju.journalApp.service;

import net.raju.journalApp.entity.User;
import net.raju.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource( strings = {
            "raju",
            "hero",
    })
    public void testFindByUserName(String name){
        User user = userRepository.findByUserName(name);
        assertNotNull(user);
        assertTrue(!user.getJournalEntries().isEmpty());
    }



    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "2,3,5"
    })
    public void add(int a, int b,int expected){
      assertEquals(expected,a+b);
    }
}
