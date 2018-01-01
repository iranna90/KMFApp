package com.federation.milk.karantaka.kmfapp.token;


import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import java.text.ParseException;
import java.util.Date;

public class TokenValidationUtil {

    public static boolean isValidToken(final String token) throws ParseException {
        final JWT jwt = JWTParser.parse(token);
        final int result = jwt.getJWTClaimsSet().getExpirationTime().compareTo(new Date());
        return result > 0;
    }
}
