package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.auth.*;
import org.apache.http.impl.auth.*;
import org.apache.http.cookie.*;
import org.apache.http.impl.cookie.*;
import org.apache.http.impl.*;
import org.apache.http.impl.conn.*;
import org.apache.http.client.methods.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.client.params.*;
import java.io.*;
import java.lang.reflect.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.config.*;
import org.apache.http.conn.routing.*;

@Deprecated
@Contract(threading = ThreadingBehavior.SAFE_CONDITIONAL)
public abstract class AbstractHttpClient extends CloseableHttpClient
{
    private final Log log;
    private HttpParams defaultParams;
    private HttpRequestExecutor requestExec;
    private ClientConnectionManager connManager;
    private ConnectionReuseStrategy reuseStrategy;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private CookieSpecRegistry supportedCookieSpecs;
    private AuthSchemeRegistry supportedAuthSchemes;
    private BasicHttpProcessor mutableProcessor;
    private ImmutableHttpProcessor protocolProcessor;
    private HttpRequestRetryHandler retryHandler;
    private RedirectStrategy redirectStrategy;
    private AuthenticationStrategy targetAuthStrategy;
    private AuthenticationStrategy proxyAuthStrategy;
    private CookieStore cookieStore;
    private CredentialsProvider credsProvider;
    private HttpRoutePlanner routePlanner;
    private UserTokenHandler userTokenHandler;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private BackoffManager backoffManager;
    
    protected AbstractHttpClient(final ClientConnectionManager conman, final HttpParams params) {
        this.log = LogFactory.getLog(this.getClass());
        this.defaultParams = params;
        this.connManager = conman;
    }
    
    protected abstract HttpParams createHttpParams();
    
    protected abstract BasicHttpProcessor createHttpProcessor();
    
    protected HttpContext createHttpContext() {
        final HttpContext context = new BasicHttpContext();
        context.setAttribute("http.scheme-registry", this.getConnectionManager().getSchemeRegistry());
        context.setAttribute("http.authscheme-registry", this.getAuthSchemes());
        context.setAttribute("http.cookiespec-registry", this.getCookieSpecs());
        context.setAttribute("http.cookie-store", this.getCookieStore());
        context.setAttribute("http.auth.credentials-provider", this.getCredentialsProvider());
        return context;
    }
    
    protected ClientConnectionManager createClientConnectionManager() {
        final SchemeRegistry registry = SchemeRegistryFactory.createDefault();
        ClientConnectionManager connManager = null;
        final HttpParams params = this.getParams();
        ClientConnectionManagerFactory factory = null;
        final String className = (String)params.getParameter("http.connection-manager.factory-class-name");
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        if (className != null) {
            try {
                Class<?> clazz;
                if (contextLoader != null) {
                    clazz = Class.forName(className, true, contextLoader);
                }
                else {
                    clazz = Class.forName(className);
                }
                factory = (ClientConnectionManagerFactory)clazz.newInstance();
            }
            catch (ClassNotFoundException ex3) {
                throw new IllegalStateException("Invalid class name: " + className);
            }
            catch (IllegalAccessException ex) {
                throw new IllegalAccessError(ex.getMessage());
            }
            catch (InstantiationException ex2) {
                throw new InstantiationError(ex2.getMessage());
            }
        }
        if (factory != null) {
            connManager = factory.newInstance(params, registry);
        }
        else {
            connManager = new BasicClientConnectionManager(registry);
        }
        return connManager;
    }
    
    protected AuthSchemeRegistry createAuthSchemeRegistry() {
        final AuthSchemeRegistry registry = new AuthSchemeRegistry();
        registry.register("Basic", new BasicSchemeFactory());
        registry.register("Digest", new DigestSchemeFactory());
        registry.register("NTLM", new NTLMSchemeFactory());
        registry.register("Negotiate", new SPNegoSchemeFactory());
        registry.register("Kerberos", new KerberosSchemeFactory());
        return registry;
    }
    
    protected CookieSpecRegistry createCookieSpecRegistry() {
        final CookieSpecRegistry registry = new CookieSpecRegistry();
        registry.register("default", new BestMatchSpecFactory());
        registry.register("best-match", new BestMatchSpecFactory());
        registry.register("compatibility", new BrowserCompatSpecFactory());
        registry.register("netscape", new NetscapeDraftSpecFactory());
        registry.register("rfc2109", new RFC2109SpecFactory());
        registry.register("rfc2965", new RFC2965SpecFactory());
        registry.register("ignoreCookies", new IgnoreSpecFactory());
        return registry;
    }
    
    protected HttpRequestExecutor createRequestExecutor() {
        return new HttpRequestExecutor();
    }
    
    protected ConnectionReuseStrategy createConnectionReuseStrategy() {
        return new DefaultConnectionReuseStrategy();
    }
    
    protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy();
    }
    
    protected HttpRequestRetryHandler createHttpRequestRetryHandler() {
        return new DefaultHttpRequestRetryHandler();
    }
    
    @Deprecated
    protected RedirectHandler createRedirectHandler() {
        return new DefaultRedirectHandler();
    }
    
    protected AuthenticationStrategy createTargetAuthenticationStrategy() {
        return new TargetAuthenticationStrategy();
    }
    
    @Deprecated
    protected AuthenticationHandler createTargetAuthenticationHandler() {
        return new DefaultTargetAuthenticationHandler();
    }
    
    protected AuthenticationStrategy createProxyAuthenticationStrategy() {
        return new ProxyAuthenticationStrategy();
    }
    
    @Deprecated
    protected AuthenticationHandler createProxyAuthenticationHandler() {
        return new DefaultProxyAuthenticationHandler();
    }
    
    protected CookieStore createCookieStore() {
        return new BasicCookieStore();
    }
    
    protected CredentialsProvider createCredentialsProvider() {
        return new BasicCredentialsProvider();
    }
    
    protected HttpRoutePlanner createHttpRoutePlanner() {
        return new DefaultHttpRoutePlanner(this.getConnectionManager().getSchemeRegistry());
    }
    
    protected UserTokenHandler createUserTokenHandler() {
        return new DefaultUserTokenHandler();
    }
    
    @Override
    public final synchronized HttpParams getParams() {
        if (this.defaultParams == null) {
            this.defaultParams = this.createHttpParams();
        }
        return this.defaultParams;
    }
    
    public synchronized void setParams(final HttpParams params) {
        this.defaultParams = params;
    }
    
    @Override
    public final synchronized ClientConnectionManager getConnectionManager() {
        if (this.connManager == null) {
            this.connManager = this.createClientConnectionManager();
        }
        return this.connManager;
    }
    
    public final synchronized HttpRequestExecutor getRequestExecutor() {
        if (this.requestExec == null) {
            this.requestExec = this.createRequestExecutor();
        }
        return this.requestExec;
    }
    
    public final synchronized AuthSchemeRegistry getAuthSchemes() {
        if (this.supportedAuthSchemes == null) {
            this.supportedAuthSchemes = this.createAuthSchemeRegistry();
        }
        return this.supportedAuthSchemes;
    }
    
    public synchronized void setAuthSchemes(final AuthSchemeRegistry registry) {
        this.supportedAuthSchemes = registry;
    }
    
    public final synchronized ConnectionBackoffStrategy getConnectionBackoffStrategy() {
        return this.connectionBackoffStrategy;
    }
    
    public synchronized void setConnectionBackoffStrategy(final ConnectionBackoffStrategy strategy) {
        this.connectionBackoffStrategy = strategy;
    }
    
    public final synchronized CookieSpecRegistry getCookieSpecs() {
        if (this.supportedCookieSpecs == null) {
            this.supportedCookieSpecs = this.createCookieSpecRegistry();
        }
        return this.supportedCookieSpecs;
    }
    
    public final synchronized BackoffManager getBackoffManager() {
        return this.backoffManager;
    }
    
    public synchronized void setBackoffManager(final BackoffManager manager) {
        this.backoffManager = manager;
    }
    
    public synchronized void setCookieSpecs(final CookieSpecRegistry registry) {
        this.supportedCookieSpecs = registry;
    }
    
    public final synchronized ConnectionReuseStrategy getConnectionReuseStrategy() {
        if (this.reuseStrategy == null) {
            this.reuseStrategy = this.createConnectionReuseStrategy();
        }
        return this.reuseStrategy;
    }
    
    public synchronized void setReuseStrategy(final ConnectionReuseStrategy strategy) {
        this.reuseStrategy = strategy;
    }
    
    public final synchronized ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        if (this.keepAliveStrategy == null) {
            this.keepAliveStrategy = this.createConnectionKeepAliveStrategy();
        }
        return this.keepAliveStrategy;
    }
    
    public synchronized void setKeepAliveStrategy(final ConnectionKeepAliveStrategy strategy) {
        this.keepAliveStrategy = strategy;
    }
    
    public final synchronized HttpRequestRetryHandler getHttpRequestRetryHandler() {
        if (this.retryHandler == null) {
            this.retryHandler = this.createHttpRequestRetryHandler();
        }
        return this.retryHandler;
    }
    
    public synchronized void setHttpRequestRetryHandler(final HttpRequestRetryHandler handler) {
        this.retryHandler = handler;
    }
    
    @Deprecated
    public final synchronized RedirectHandler getRedirectHandler() {
        return this.createRedirectHandler();
    }
    
    @Deprecated
    public synchronized void setRedirectHandler(final RedirectHandler handler) {
        this.redirectStrategy = new DefaultRedirectStrategyAdaptor(handler);
    }
    
    public final synchronized RedirectStrategy getRedirectStrategy() {
        if (this.redirectStrategy == null) {
            this.redirectStrategy = new DefaultRedirectStrategy();
        }
        return this.redirectStrategy;
    }
    
    public synchronized void setRedirectStrategy(final RedirectStrategy strategy) {
        this.redirectStrategy = strategy;
    }
    
    @Deprecated
    public final synchronized AuthenticationHandler getTargetAuthenticationHandler() {
        return this.createTargetAuthenticationHandler();
    }
    
    @Deprecated
    public synchronized void setTargetAuthenticationHandler(final AuthenticationHandler handler) {
        this.targetAuthStrategy = new AuthenticationStrategyAdaptor(handler);
    }
    
    public final synchronized AuthenticationStrategy getTargetAuthenticationStrategy() {
        if (this.targetAuthStrategy == null) {
            this.targetAuthStrategy = this.createTargetAuthenticationStrategy();
        }
        return this.targetAuthStrategy;
    }
    
    public synchronized void setTargetAuthenticationStrategy(final AuthenticationStrategy strategy) {
        this.targetAuthStrategy = strategy;
    }
    
    @Deprecated
    public final synchronized AuthenticationHandler getProxyAuthenticationHandler() {
        return this.createProxyAuthenticationHandler();
    }
    
    @Deprecated
    public synchronized void setProxyAuthenticationHandler(final AuthenticationHandler handler) {
        this.proxyAuthStrategy = new AuthenticationStrategyAdaptor(handler);
    }
    
    public final synchronized AuthenticationStrategy getProxyAuthenticationStrategy() {
        if (this.proxyAuthStrategy == null) {
            this.proxyAuthStrategy = this.createProxyAuthenticationStrategy();
        }
        return this.proxyAuthStrategy;
    }
    
    public synchronized void setProxyAuthenticationStrategy(final AuthenticationStrategy strategy) {
        this.proxyAuthStrategy = strategy;
    }
    
    public final synchronized CookieStore getCookieStore() {
        if (this.cookieStore == null) {
            this.cookieStore = this.createCookieStore();
        }
        return this.cookieStore;
    }
    
    public synchronized void setCookieStore(final CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
    
    public final synchronized CredentialsProvider getCredentialsProvider() {
        if (this.credsProvider == null) {
            this.credsProvider = this.createCredentialsProvider();
        }
        return this.credsProvider;
    }
    
    public synchronized void setCredentialsProvider(final CredentialsProvider credsProvider) {
        this.credsProvider = credsProvider;
    }
    
    public final synchronized HttpRoutePlanner getRoutePlanner() {
        if (this.routePlanner == null) {
            this.routePlanner = this.createHttpRoutePlanner();
        }
        return this.routePlanner;
    }
    
    public synchronized void setRoutePlanner(final HttpRoutePlanner routePlanner) {
        this.routePlanner = routePlanner;
    }
    
    public final synchronized UserTokenHandler getUserTokenHandler() {
        if (this.userTokenHandler == null) {
            this.userTokenHandler = this.createUserTokenHandler();
        }
        return this.userTokenHandler;
    }
    
    public synchronized void setUserTokenHandler(final UserTokenHandler handler) {
        this.userTokenHandler = handler;
    }
    
    protected final synchronized BasicHttpProcessor getHttpProcessor() {
        if (this.mutableProcessor == null) {
            this.mutableProcessor = this.createHttpProcessor();
        }
        return this.mutableProcessor;
    }
    
    private synchronized HttpProcessor getProtocolProcessor() {
        if (this.protocolProcessor == null) {
            final BasicHttpProcessor proc = this.getHttpProcessor();
            final int reqc = proc.getRequestInterceptorCount();
            final HttpRequestInterceptor[] reqinterceptors = new HttpRequestInterceptor[reqc];
            for (int i = 0; i < reqc; ++i) {
                reqinterceptors[i] = proc.getRequestInterceptor(i);
            }
            final int resc = proc.getResponseInterceptorCount();
            final HttpResponseInterceptor[] resinterceptors = new HttpResponseInterceptor[resc];
            for (int j = 0; j < resc; ++j) {
                resinterceptors[j] = proc.getResponseInterceptor(j);
            }
            this.protocolProcessor = new ImmutableHttpProcessor(reqinterceptors, resinterceptors);
        }
        return this.protocolProcessor;
    }
    
    public synchronized int getResponseInterceptorCount() {
        return this.getHttpProcessor().getResponseInterceptorCount();
    }
    
    public synchronized HttpResponseInterceptor getResponseInterceptor(final int index) {
        return this.getHttpProcessor().getResponseInterceptor(index);
    }
    
    public synchronized HttpRequestInterceptor getRequestInterceptor(final int index) {
        return this.getHttpProcessor().getRequestInterceptor(index);
    }
    
    public synchronized int getRequestInterceptorCount() {
        return this.getHttpProcessor().getRequestInterceptorCount();
    }
    
    public synchronized void addResponseInterceptor(final HttpResponseInterceptor itcp) {
        this.getHttpProcessor().addInterceptor(itcp);
        this.protocolProcessor = null;
    }
    
    public synchronized void addResponseInterceptor(final HttpResponseInterceptor itcp, final int index) {
        this.getHttpProcessor().addInterceptor(itcp, index);
        this.protocolProcessor = null;
    }
    
    public synchronized void clearResponseInterceptors() {
        this.getHttpProcessor().clearResponseInterceptors();
        this.protocolProcessor = null;
    }
    
    public synchronized void removeResponseInterceptorByClass(final Class<? extends HttpResponseInterceptor> clazz) {
        this.getHttpProcessor().removeResponseInterceptorByClass(clazz);
        this.protocolProcessor = null;
    }
    
    public synchronized void addRequestInterceptor(final HttpRequestInterceptor itcp) {
        this.getHttpProcessor().addInterceptor(itcp);
        this.protocolProcessor = null;
    }
    
    public synchronized void addRequestInterceptor(final HttpRequestInterceptor itcp, final int index) {
        this.getHttpProcessor().addInterceptor(itcp, index);
        this.protocolProcessor = null;
    }
    
    public synchronized void clearRequestInterceptors() {
        this.getHttpProcessor().clearRequestInterceptors();
        this.protocolProcessor = null;
    }
    
    public synchronized void removeRequestInterceptorByClass(final Class<? extends HttpRequestInterceptor> clazz) {
        this.getHttpProcessor().removeRequestInterceptorByClass(clazz);
        this.protocolProcessor = null;
    }
    
    @Override
    protected final CloseableHttpResponse doExecute(final HttpHost target, final HttpRequest request, final HttpContext context) throws IOException, ClientProtocolException {
        Args.notNull(request, "HTTP request");
        HttpContext execContext = null;
        RequestDirector director = null;
        HttpRoutePlanner routePlanner = null;
        ConnectionBackoffStrategy connectionBackoffStrategy = null;
        BackoffManager backoffManager = null;
        synchronized (this) {
            final HttpContext defaultContext = this.createHttpContext();
            if (context == null) {
                execContext = defaultContext;
            }
            else {
                execContext = new DefaultedHttpContext(context, defaultContext);
            }
            final HttpParams params = this.determineParams(request);
            final RequestConfig config = HttpClientParamConfig.getRequestConfig(params);
            execContext.setAttribute("http.request-config", config);
            director = this.createClientRequestDirector(this.getRequestExecutor(), this.getConnectionManager(), this.getConnectionReuseStrategy(), this.getConnectionKeepAliveStrategy(), this.getRoutePlanner(), this.getProtocolProcessor(), this.getHttpRequestRetryHandler(), this.getRedirectStrategy(), this.getTargetAuthenticationStrategy(), this.getProxyAuthenticationStrategy(), this.getUserTokenHandler(), params);
            routePlanner = this.getRoutePlanner();
            connectionBackoffStrategy = this.getConnectionBackoffStrategy();
            backoffManager = this.getBackoffManager();
        }
        try {
            if (connectionBackoffStrategy != null && backoffManager != null) {
                final HttpHost targetForRoute = (HttpHost)((target != null) ? target : this.determineParams(request).getParameter("http.default-host"));
                final HttpRoute route = routePlanner.determineRoute(targetForRoute, request, execContext);
                CloseableHttpResponse out;
                try {
                    out = CloseableHttpResponseProxy.newProxy(director.execute(target, request, execContext));
                }
                catch (RuntimeException re) {
                    if (connectionBackoffStrategy.shouldBackoff(re)) {
                        backoffManager.backOff(route);
                    }
                    throw re;
                }
                catch (Exception e) {
                    if (connectionBackoffStrategy.shouldBackoff(e)) {
                        backoffManager.backOff(route);
                    }
                    if (e instanceof HttpException) {
                        throw (HttpException)e;
                    }
                    if (e instanceof IOException) {
                        throw (IOException)e;
                    }
                    throw new UndeclaredThrowableException(e);
                }
                if (connectionBackoffStrategy.shouldBackoff(out)) {
                    backoffManager.backOff(route);
                }
                else {
                    backoffManager.probe(route);
                }
                return out;
            }
            return CloseableHttpResponseProxy.newProxy(director.execute(target, request, execContext));
        }
        catch (HttpException httpException) {
            throw new ClientProtocolException(httpException);
        }
    }
    
    @Deprecated
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor requestExec, final ClientConnectionManager conman, final ConnectionReuseStrategy reustrat, final ConnectionKeepAliveStrategy kastrat, final HttpRoutePlanner rouplan, final HttpProcessor httpProcessor, final HttpRequestRetryHandler retryHandler, final RedirectHandler redirectHandler, final AuthenticationHandler targetAuthHandler, final AuthenticationHandler proxyAuthHandler, final UserTokenHandler userTokenHandler, final HttpParams params) {
        return new DefaultRequestDirector(requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectHandler, targetAuthHandler, proxyAuthHandler, userTokenHandler, params);
    }
    
    @Deprecated
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor requestExec, final ClientConnectionManager conman, final ConnectionReuseStrategy reustrat, final ConnectionKeepAliveStrategy kastrat, final HttpRoutePlanner rouplan, final HttpProcessor httpProcessor, final HttpRequestRetryHandler retryHandler, final RedirectStrategy redirectStrategy, final AuthenticationHandler targetAuthHandler, final AuthenticationHandler proxyAuthHandler, final UserTokenHandler userTokenHandler, final HttpParams params) {
        return new DefaultRequestDirector(this.log, requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectStrategy, targetAuthHandler, proxyAuthHandler, userTokenHandler, params);
    }
    
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor requestExec, final ClientConnectionManager conman, final ConnectionReuseStrategy reustrat, final ConnectionKeepAliveStrategy kastrat, final HttpRoutePlanner rouplan, final HttpProcessor httpProcessor, final HttpRequestRetryHandler retryHandler, final RedirectStrategy redirectStrategy, final AuthenticationStrategy targetAuthStrategy, final AuthenticationStrategy proxyAuthStrategy, final UserTokenHandler userTokenHandler, final HttpParams params) {
        return new DefaultRequestDirector(this.log, requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectStrategy, targetAuthStrategy, proxyAuthStrategy, userTokenHandler, params);
    }
    
    protected HttpParams determineParams(final HttpRequest req) {
        return new ClientParamsStack(null, this.getParams(), req.getParams(), null);
    }
    
    @Override
    public void close() {
        this.getConnectionManager().shutdown();
    }
}