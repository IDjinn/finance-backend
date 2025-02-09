package idjinn.finance.security;

import idjinn.finance.repository.UsersRepository;
import idjinn.finance.util.errors.common.NotFoundException;
import idjinn.finance.util.errors.common.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    private final UsersRepository userRepository;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        if (!isPublicEndpoint(request.getRequestURI())) {
            final var token = recoveryToken(request);
            if (token == null)
                throw new UnauthorizedException("Invalid token");

            final var subject = jwtTokenService.getSubjectFromToken(token);
            final var userOptional = userRepository.findUserByEmail(subject);
            if (userOptional.isEmpty())
                throw new NotFoundException("User not found.");

            final var user = userOptional.get();
            final var userDetails = new UserDetailsImpl(user);

            final var authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(final HttpServletRequest request) {
        final var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null)
            return authorizationHeader.replace("Bearer ", "");

        return null;
    }

    private boolean isPublicEndpoint(final String requestURI) {
        return "/".equals(requestURI) ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                SecurityConfiguration.NO_AUTH.stream().anyMatch(requestURI::startsWith);
    }
}
