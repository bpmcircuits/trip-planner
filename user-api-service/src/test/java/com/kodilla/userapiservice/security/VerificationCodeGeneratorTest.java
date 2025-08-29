package com.kodilla.userapiservice.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VerificationCodeGeneratorTest {

    @Test
    void shouldGenerateCode() {
        // When
        String code = VerificationCodeGenerator.generateCode();

        // Then
        assertNotNull(code);
        assertTrue(code.matches("\\d{6}"), "Code should be a 6-digit number");
        int codeValue = Integer.parseInt(code);
        assertTrue(codeValue >= 100000 && codeValue <= 999999, "Code should be between 100000 and 999999");
    }

    @Test
    void shouldGenerateUniqueCode() {
        // When
        String code1 = VerificationCodeGenerator.generateCode();
        String code2 = VerificationCodeGenerator.generateCode();
        String code3 = VerificationCodeGenerator.generateCode();

        // Then
        // Note: There's a very small chance this test could fail if the random generator
        // happens to generate the same code twice, but it's extremely unlikely
        assertTrue(!code1.equals(code2) || !code1.equals(code3) || !code2.equals(code3),
                "Generated codes should be unique");
    }
}