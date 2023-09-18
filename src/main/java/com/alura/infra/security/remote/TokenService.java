package com.alura.infra.security.remote;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.alura.domain.model.User;
import com.alura.infra.error.ErrorMessages;
import com.alura.infra.error.TokenException;
import com.alura.infra.security.CryptUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {

	private RSAPublicKey rsaPublicKey;
	private RSAPrivateKey rsaPrivateKey;
	private Long tokenExpirationHours;

	public TokenService(@Value("${app.keys.public-key-path}") Resource publicKeyResource,
			@Value("${app.keys.private-key-path}") Resource privateKeyResource,
			@Value("${app.token.expiration-hours}") Long tokenExpirationHours) throws ParseException {

		this.tokenExpirationHours = tokenExpirationHours;

		try {
			this.rsaPublicKey = CryptUtils.readPublicKey(publicKeyResource.getFile());
			this.rsaPrivateKey = CryptUtils.readPrivateKey(privateKeyResource.getFile());
		} catch (IOException e) {
			throw new ParseException(ErrorMessages.KEY_PARSE_ERROR.getMessage());
		}
	}

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
			return JWT.create().withIssuer("jdum").withSubject(user.getName()).withClaim("id", user.getId())
					.withExpiresAt(createExpirationDate()).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new TokenException(ErrorMessages.TOKEN_NOT_CREATED.getMessage() + user.getId());
		}
	}

	public String getSubject(String token) {
		DecodedJWT decodedJWT = null;
		if (token == null) {
			throw new TokenException(ErrorMessages.NULL_TOKEN.getMessage());
		}
		try {
			Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
			decodedJWT = JWT.require(algorithm).withIssuer("jdum").build().verify(token);
		} catch (JWTVerificationException exception) {
			throw new JWTVerificationException(ErrorMessages.COULD_NOT_VERIFY_TOKEN.getMessage());
		}
		if (decodedJWT == null || decodedJWT.getSubject() == null) {
			throw new TokenException(ErrorMessages.NULL_VERIFIER.getMessage());
		}
		return decodedJWT.getSubject();
	}

	private Instant createExpirationDate() {
		// Get the system's default time zone
		TimeZone timeZone = TimeZone.getDefault();

		// Create a ZoneOffset based on the system's default time zone
		ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timeZone.getRawOffset() / 1000);

		LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(tokenExpirationHours);

		return expirationDateTime.toInstant(zoneOffset);

	}
}
