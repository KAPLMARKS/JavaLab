package chat.aspects;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

@Aspect
@Component
public class AuthAspect {

    private boolean forbidden;

    @Value("${jwt.secret}")
    private String secret;

    @Pointcut("execution(* chat.controllers.ChatController.getChat(..)) || execution(* chat.controllers.RoomController.getRooms(..))")
    public void selectAllMethodsAvailable() {

    }

    @Around("selectAllMethodsAvailable())")
    public Object logServiceMethods(ProceedingJoinPoint jp) throws Throwable {
        forbidden = false;
        Object result = jp.proceed(jp.getArgs());
        if (!forbidden) {
            return result;
        }
        return "forbidden";
    }

    @Before(value = "selectAllMethodsAvailable() && args(*, request, *, response)", argNames = "request,response")
    public void checkAuth(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = WebUtils.getCookie(request, "X-Authorization");
        if (cookie != null) {
            String token = cookie.getValue();
            try {
                Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token).getBody();
            } catch (JwtException ex) {
                forbidden = true;
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                System.out.println("Invalid token");
            }
        } else {
            forbidden = true;
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
