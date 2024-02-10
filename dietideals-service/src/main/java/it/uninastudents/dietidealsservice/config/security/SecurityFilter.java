package it.uninastudents.dietidealsservice.config.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import it.uninastudents.dietidealsservice.model.entity.User;
import it.uninastudents.dietidealsservice.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private static final String COOKIE_SESSION = "session";

    private final CookieUtils cookieUtils;
    private final SecurityService securityService;
    private final SecurityProperties securityProps;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        verifyToken(request);
        filterChain.doFilter(request, response);
    }

    private void verifyToken(HttpServletRequest request) {
        String session = null;
        FirebaseToken decodedToken = null;
        Credentials.CredentialType type = null;
        var strictServerSessionEnabled = securityProps.getFirebaseProps().isEnableStrictServerSession();
        var sessionCookie = cookieUtils.getCookie(COOKIE_SESSION);
        var token = securityService.getBearerToken(request);
        try {
            if (sessionCookie != null) {
                session = sessionCookie.getValue();
                decodedToken = FirebaseAuth.getInstance().verifySessionCookie(session,securityProps.getFirebaseProps().isEnableCheckSessionRevoked());
                type = Credentials.CredentialType.SESSION;
            } else if (!strictServerSessionEnabled && (token != null && !token.equalsIgnoreCase("undefined"))) {
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                type = Credentials.CredentialType.ID_TOKEN;
            }
        } catch (FirebaseAuthException e) {
            log.error("Something went wrong while authenticating through Firebase...", e);
        }

        var user = mapFirebaseTokenToInternalUser(decodedToken);
        if (user != null) {
            // Al posto di "null" in authorities, mettere il ruolo "UtenteRuolo" come SimpleGrantedAuthority, per poter usare i preauthorize di spring
            var authentication = new UsernamePasswordAuthenticationToken(user, new Credentials(type, decodedToken, token, session), null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private User mapFirebaseTokenToInternalUser(FirebaseToken decodedToken) {
        // Verificare che nella propria UtenteRepository esista un utente con la mail presa da decodedToken
        // Se esiste, restituire quell'utente
        // Se non esiste, creare un nuovo utente utilizzando i dati presenti in decoded token, ovvero name (username) e email. Restituire l'utente appena creato
        // Eventuali ruoli / scope, li prendi da getClaims
        // Quindi in getClaims (e' una mappa) avrai "rol" con valore uno degli enum che hai in UtenteRuolo

        User user = null;
        if (decodedToken != null) {
            user = new User();
            user.setUid(decodedToken.getUid());
            user.setName(decodedToken.getName());
            user.setEmail(decodedToken.getEmail());
            user.setEmailVerified(decodedToken.isEmailVerified());
        }
        return user;
    }
}
