package net.google.journalApp.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import net.google.journalApp.utilis.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//		String authorizationHeader = request.getHeader("Authorization");
//		String username = null;
//		String jwt = null;
//		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//			jwt = authorizationHeader.substring(7);
//			username = jwtUtil.extractUsername(jwt);
//		}
//		if (username != null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//			if (jwtUtil.validateToken(jwt)) {
//				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
//						userDetails.getAuthorities());
//				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(auth);
//			}
//		}
//		chain.doFilter(request, response);
//	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		// skip login & register (no need JWT)
		return path.equals("/log-in") || path.equals("/register");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = null;
		String username = null;

		try {

			String authHeader = request.getHeader("Authorization");

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				jwt = authHeader.substring(7);
				username = jwtUtil.extractUsername(jwt);
			}
			System.err.println("call 1");

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				System.err.println("call ");
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				System.err.println("userDetails " + userDetails);

				if (userDetails == null) {
					System.err.println("Call When object null");
					Map<String, Object> responseMap = new LinkedHashMap<>();
					responseMap.put("error", false);
					responseMap.put("errorcode", null);
					responseMap.put("errorMessage", null);
					responseMap.put("data", "Unauthorized User");
				}

				if (jwtUtil.validateToken(jwt)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			filterChain.doFilter(request, response);

		} catch (JwtException e) {
			// Send custom JSON response when unauthorized
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");

			Map<String, Object> responseMap = new LinkedHashMap<>();
			responseMap.put("error", false);
			responseMap.put("errorcode", null);
			responseMap.put("errorMessage", null);
			responseMap.put("data", "Unauthorized User");

			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().write(mapper.writeValueAsString(responseMap));
		}
	}

	private void unauthorizedResponse(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		Map<String, Object> responseMap = new LinkedHashMap<>();
		responseMap.put("error", false);
		responseMap.put("errorcode", null);
		responseMap.put("errorMessage", null);
		responseMap.put("data", "Unauthorized User");

		new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
	}
}