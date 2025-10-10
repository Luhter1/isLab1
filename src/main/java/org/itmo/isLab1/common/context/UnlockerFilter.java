package org.itmo.isLab1.common.context;

import jakarta.servlet.*;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class UnlockerFilter implements Filter {

    private final ApplicationLockBean applicationLockBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            ReentrantLock importLock = applicationLockBean.getImportLock();
            if (importLock.isHeldByCurrentThread()) {
                importLock.unlock();
            }

        }
    }
}
