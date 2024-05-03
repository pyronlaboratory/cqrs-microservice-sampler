package com.soagrowers.prefilters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * is a custom Zuul filter that logs incoming HTTP requests with a debug level message.
 * The filter has a single method, `run()`, which retrieves the current request context
 * and logs the request details using the `log.debug()` method from the SLF4J logging
 * framework.
 */
public class SimpleLoggingPreFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(SimpleLoggingPreFilter.class);

    /**
     * returns the string `"pre"`.
     * 
     * @returns the string "pre".
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * returns an integer value of 1, indicating that it filters the input data based on
     * a specific order.
     * 
     * @returns an integer value of 1.
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * determines whether filtering is necessary based on a predefined condition and
     * returns `true` if filtering is required.
     * 
     * @returns a boolean value indicating that filtering is required.
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * logs a message to the debug log of the current context, indicating the HTTP method
     * and URL of the incoming request.
     * 
     * @returns a debug message regarding the HTTP request method and URL.
     * 
     * 	- The output is an Object of type null.
     * 	- The `RequestContext` object `ctx` contains information about the current request
     * context, including the request itself and various other attributes.
     * 	- The `HttpServletRequest` object `request` is a subclass of `ServletRequest`
     * that provides access to information about the HTTP request, such as the method,
     * URL, and headers.
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.debug("{} request to {}", request.getMethod(), request.getRequestURL().toString());
        return null;
    }
}
