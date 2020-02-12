package com.book.search.common.cipher;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import lombok.extern.slf4j.Slf4j;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

/**
 * RS256 RSA PKCS#1 signature with SHA-256, 2048 bits key
 */
@Slf4j
public class RSASigner {

    private RSASSAVerifier RSAVerifier;
    private RSASSASigner RSASigner;
    private JWSHeader jwsHeader;

    public RSASigner(CipherKeyManager keyManager) {
        RSAVerifier = new RSASSAVerifier((RSAPublicKey) keyManager.getServerKeyPair().getPublic());
        RSASigner = new RSASSASigner(keyManager.getServerKeyPair().getPrivate());
        jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
    }


    public String protect(String json) throws JOSEException {
        JWSObject jwsObject;
        try {
            jwsObject = new JWSObject(jwsHeader, new Payload(json));
            jwsObject.sign(RSASigner);
        } catch (JOSEException e) {
            log.error("failed RSA signature.", e);
            throw e;
        }

        return jwsObject.serialize();
    }

    public String verify(String jwt) throws ParseException, JOSEException {
        try {
            JWSObject jwsObject = JWSObject.parse(jwt);
            if (jwsObject.verify(RSAVerifier)) {
                return jwsObject.getPayload().toString();
            }
        } catch (ParseException e) {
            log.error("failed parsing idToken.", e);
            throw e;
        } catch (IllegalStateException e) {
            log.error("invalid idToken payload. ", e);
            throw e;
        } catch (JOSEException e) {
            log.error("failed to verify RSA protection.", e);
            throw e;
        }

        log.debug("verify failure.");
        throw new JOSEException("verify failure.");
    }
}
