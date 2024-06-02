package sbsamir.util;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import sbsamir.payload.response.MessageResponse;
import sbsamir.service.impl.UserDetailsImpl;

@Component
public class JwtTokenUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8777122786748123026L;

	public static final long JWT_TOKEN_VALIDITY=300000;
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String generateJwtToken(UserDetailsImpl userPrincipal){
        return generateTokenFromUsername(userPrincipal.getUsername());
    }

    public String generateTokenFromUsername(String username){
        return Jwts.builder().setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    private MessageResponse buildMessageResponse(String request){
        return new MessageResponse(request);
    }

    public boolean validateJwtToken(String authToken){
        boolean flag=false;
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            flag=true;
        } catch (SignatureException e) {
            //TODO: handle exception
            flag=false;
            buildMessageResponse(e.getMessage());
            System.err.println("signature problem "+e.getMessage());
        }catch(MalformedJwtException e){
            flag=false;
            buildMessageResponse(e.getMessage());
            System.err.println("jwt format problems "+e.getMessage());
        }catch(ExpiredJwtException e){
            flag=false;
            buildMessageResponse(e.getMessage());
            System.err.println("jwt is expired "+e.getMessage());
        }catch(UnsupportedJwtException e){
            flag=false;
            buildMessageResponse(e.getMessage());
            System.err.println("jwt not supported "+e.getMessage());
        }catch(IllegalArgumentException e){
            flag=false;
            buildMessageResponse(e.getMessage());
            System.err.println("others error "+e.getMessage());
        }
        return flag;
    }
}
