package com.alura.infra.security;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class CryptUtils {

	public static RSAPrivateKey readPrivateKey(File file) throws IOException {
		try (FileReader keyReader = new FileReader(file)) {

			PEMParser pemParser = new PEMParser(keyReader);
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());

			return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
		}
	}

	public static RSAPublicKey readPublicKey(File file) throws IOException {
		try (FileReader keyReader = new FileReader(file)) {
			PEMParser pemParser = new PEMParser(keyReader);
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
			return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
		}
	}
}
