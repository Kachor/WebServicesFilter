package com.unitedthinkers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.servlet.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class WebServicesFilter implements Filter {
    private FilterConfig config = null;
    private Set<String> blockedIPs = new HashSet<>();
    private String filePath = null;
    private ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
        filePath = filterConfig.getInitParameter("location");
        getIPsFromFile();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request != null && request.getRemoteAddr() != null){
            String clientIP = request.getRemoteAddr();

            if (!blockedIPs.contains(clientIP)){
                chain.doFilter(request, response);
            } else {
                response.setContentType("text/html");
                response.setCharacterEncoding("utf-8");
                try {
                    response.getWriter().write("<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head><title>Filter</title></head>\n" +
                            "<p style=\"text-align: center\"><img src=\"image/error.png\" alt=\"Error\"></p>\n"+
                            "<body bgcolor=\"#fdf5e6\">\n" +
                            "<p style=\"text-align: center\">Sorry, this IP address has been blocked! <a href=\"index.jsp\">Home</a></p>\n" +
                            "</body></html>");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() {
        config = null;
        blockedIPs = null;
        filePath = null;
        ses.shutdown();
    }

    private void getIPsFromFile() {
        Runnable fileScan = new Runnable() {
            public void run() {
                Set<String> ips = new HashSet<>();
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = br.readLine()) != null){
                        ips.add(line.trim());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                blockedIPs = ips;
            }
        };
        ses.scheduleAtFixedRate(fileScan, 10, 10, SECONDS);
    }
}
