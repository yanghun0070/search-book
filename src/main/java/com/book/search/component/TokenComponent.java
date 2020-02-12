package com.book.search.component;

import com.book.search.common.cipher.CipherKeyManager;
import com.book.search.common.cipher.RSASigner;
import com.book.search.common.data.UserData;
import com.book.search.exception.InternalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class TokenComponent {

    private RSASigner rsaSigner;
    private ObjectMapper objectMapper;

    @Autowired
    public TokenComponent(CipherKeyManager keyManager,
                          ObjectMapper objectMapper) {
        rsaSigner = new RSASigner(keyManager);
        this.objectMapper = objectMapper;
    }

    public String generateToken(UserData userData) {
        try {
            return rsaSigner.protect(objectMapper.writeValueAsString(userData));
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new InternalException("failed RSA signature.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalException("failed jwt generate.");
        }
    }


    public UserData verify(String jwt) throws ParseException, JOSEException, JsonProcessingException {
        return objectMapper.readValue(rsaSigner.verify(jwt), UserData.class);
    }

}
