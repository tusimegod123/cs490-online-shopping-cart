package com.cs490.shoppingcart.administrationmodule.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtService.class})
@ExtendWith(SpringExtension.class)
class JwtServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    /**
     * Method under test: {@link JwtService#validateToken(String)}
     */
    @Test
    void testValidateToken() {
        // Arrange, Act and Assert
        assertFalse(jwtService.validateToken("ABC123"));
        assertFalse(jwtService.validateToken(""));
    }

    /**
     * Method under test: {@link JwtService#parseClaims(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testParseClaims() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   io.jsonwebtoken.MalformedJwtException: JWT strings must contain exactly 2 period characters. Found: 0
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:275)
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:529)
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parseClaimsJws(DefaultJwtParser.java:589)
        //       at com.cs490.shoppingcart.administrationmodule.service.JwtService.parseClaims(JwtService.java:55)
        //   In order to prevent parseClaims(String)
        //   from throwing MalformedJwtException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   parseClaims(String).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        jwtService.parseClaims("ABC123");
    }

    /**
     * Method under test: {@link JwtService#parseClaims(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testParseClaims2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   io.jsonwebtoken.MalformedJwtException: JWT strings must contain exactly 2 period characters. Found: 0
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:275)
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:529)
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parseClaimsJws(DefaultJwtParser.java:589)
        //       at com.cs490.shoppingcart.administrationmodule.service.JwtService.parseClaims(JwtService.java:55)
        //   In order to prevent parseClaims(String)
        //   from throwing MalformedJwtException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   parseClaims(String).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        jwtService.parseClaims("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
    }

    /**
     * Method under test: {@link JwtService#parseClaims(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testParseClaims3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: JWT String argument cannot be null or empty.
        //       at io.jsonwebtoken.lang.Assert.hasText(Assert.java:132)
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:527)
        //       at io.jsonwebtoken.impl.DefaultJwtParser.parseClaimsJws(DefaultJwtParser.java:589)
        //       at com.cs490.shoppingcart.administrationmodule.service.JwtService.parseClaims(JwtService.java:55)
        //   In order to prevent parseClaims(String)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   parseClaims(String).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        jwtService.parseClaims("");
    }

    /**
     * Method under test: {@link JwtService#generateToken(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateToken() {
        // TODO: Complete this test.
        //   Reason: F009 Internal error.
        //   java.lang.StackOverflowError
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        // Arrange
        // TODO: Populate arranged inputs
        String username = "";

        // Act
        String actualGenerateTokenResult = this.jwtService.generateToken(username);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link JwtService#refreshToken(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRefreshToken() {
        // TODO: Complete this test.
        //   Reason: F009 Internal error.
        //   java.lang.StackOverflowError
        //   Please contact Diffblue through the appropriate support channel
        //   (https://www.diffblue.com/support/) providing details about this error.

        // Arrange
        // TODO: Populate arranged inputs
        String username = "";

        // Act
        String actualRefreshTokenResult = this.jwtService.refreshToken(username);

        // Assert
        // TODO: Add assertions on result
    }
}

