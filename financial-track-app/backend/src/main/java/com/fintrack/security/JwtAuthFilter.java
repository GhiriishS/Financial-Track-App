package com.fintrack.security;

import com.fintrack.user.User;
import com.fintrack.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);

    if (!jwtService.isValid(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    String email = jwtService.parseSubject(token);
    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
      filterChain.doFilter(request, response);
      return;
    }

    var auth = new UsernamePasswordAuthenticationToken(
        email,
        null,
        List.of(new SimpleGrantedAuthority("ROLE_USER"))
    );

    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(request, response);
  }
}
