package com.forestsoftware.ppmtool.security;

import com.forestsoftware.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        String userId = Long.toString(user.getId());
        Map<String, Object>  claims= new HashMap<>();

        claims.put("id", userId);
        claims.put("username",user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                .compact();
    }
    public boolean validateToken(String s){
        try{
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(s);
            return  true;
        }catch (SignatureException e){
            System.out.println(e.getMessage());
        }catch(ExpiredJwtException  e){
            System.out.println(e.getMessage());
        }catch (UnsupportedJwtException e){
            System.out.println(e);
        }catch (IllegalArgumentException e){
            System.out.println("Illegal" + e);
        }catch (MalformedJwtException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public Long getUserIdFromJwt(String s){
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(s).getBody();
        String id =(String) claims.get("id");
        System.out.println("======= " + id);
        return Long.parseLong(id);
    }
}
