package org.itmo.isLab1.events.filters;

import com.fasterxml.uuid.Generators;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class RequestServerFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestServerFilter.class);

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var uuid = Generators.timeBasedEpochRandomGenerator().generate().toString();

        logger.info("Uuid: {}", uuid);

        request.setAttribute("requestUUID", uuid);
        response.setHeader("X-Response-Uuid", uuid);

        filterChain.doFilter(request, response);
    }
}