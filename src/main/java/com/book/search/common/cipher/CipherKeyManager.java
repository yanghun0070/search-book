package com.book.search.common.cipher;

import com.book.search.common.properties.BookProperties;
import com.book.search.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.Security;

/**
 * Keypair load를 위한 클래스
 */
@Slf4j
@Component
public class CipherKeyManager {

    private KeyPair keyPair;

    @Autowired
    public CipherKeyManager(BookProperties bookProperties){
        loadKeyPair(bookProperties.getKeyPairPath());
    }

    public KeyPair getServerKeyPair() {
        return keyPair;
    }

    private void loadKeyPair(String keyPairPath) {
        Security.addProvider(new BouncyCastleProvider());
        PEMParser pemParser = new PEMParser(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(keyPairPath)));
        try {
            final Object privateKeyPemObject = pemParser.readObject();
            final JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());

            if (privateKeyPemObject instanceof PEMEncryptedKeyPair) {
                final PEMEncryptedKeyPair ckp = (PEMEncryptedKeyPair) privateKeyPemObject;
                final PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder()
                        .build("dlehdwls".toCharArray());
                keyPair = converter.getKeyPair(ckp.decryptKeyPair(decProv));
            } else {
                keyPair = converter.getKeyPair((PEMKeyPair) privateKeyPemObject);
            }
        } catch (IOException ex) {
            log.error("failed to load the key pair file(.PEM).", ex);
            throw new InternalException("failed to load the key pair file(.PEM).", ex);
        }
    }
}
