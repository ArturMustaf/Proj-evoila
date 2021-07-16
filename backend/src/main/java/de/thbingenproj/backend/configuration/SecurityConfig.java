package de.thbingenproj.backend.configuration;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    private final KeycloakClientRequestFactory keycloakClientRequestFactory;


    @Value("${cors-url}")
    String[] allowedCors;

    public SecurityConfig(KeycloakClientRequestFactory keycloakClientRequestFactory) {
        this.keycloakClientRequestFactory = keycloakClientRequestFactory;

        //to use principal and authentication together with @async
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate() {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

    /**
     * registers the Keycloakauthenticationprovider in spring context
     * and sets its mapping strategy for roles/authorities (mapping to spring seccurities' default ROLE_... for authorities ).
     *
     * @param auth SecurityBuilder to build authentications and add details like authproviders etc.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        var keyCloakAuthProvider = keycloakAuthenticationProvider();
        keyCloakAuthProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        auth.authenticationProvider(keyCloakAuthProvider);
    }

    /**
     * Sets keycloaks config resolver to use springs application.properties instead of keycloak.json (which is standard)
     */
    @Bean
    public KeycloakConfigResolver keyCloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * define the session auth strategy so that no session is created
     *
     * @return concrete implementation of session authentication strategy
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    /**
     * define the actual constraints of the app.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // TODO Remove csrf disable => needed currently for post / put mappings
        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .and()
                .authorizeRequests()
                .antMatchers("/costs/**").hasRole("user")
                .antMatchers("/create/**").hasRole("admin")
                .antMatchers("/public/**").permitAll()
                .antMatchers("/search/**").hasRole("admin")
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/vdcs/**").hasRole("user")
                .antMatchers("/org-networks/**").hasAnyRole("user", "admin")
                .antMatchers("/org-users/**").hasAnyRole("user", "admin")
                .antMatchers("/org-user-roles/**").hasAnyRole("user", "admin")
                .antMatchers("/organizations/**").hasAnyRole("user", "admin")
                .antMatchers("/vapps/**").hasAnyRole("user", "admin")
                .antMatchers("/vdc-compute-capacities/**").hasAnyRole("user", "admin")
                .antMatchers("/vdcs/**").hasAnyRole("user", "admin")
                .antMatchers("/vdc-storage-profiles/**").hasAnyRole("user", "admin")
                .anyRequest().hasAnyRole("user", "admin");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();

        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        //the below three lines will add the relevant CORS response headers
        for (String allowedCorsUrl : this.allowedCors) {
            configuration.addAllowedOrigin(allowedCorsUrl);
        }

        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public FilterRegistrationBean<KeycloakAuthenticationProcessingFilter> keycloakAuthenticationProcessingFilterRegistrationBean(
            KeycloakAuthenticationProcessingFilter filter) {
        var registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<KeycloakPreAuthActionsFilter> keycloakPreAuthActionsFilterRegistrationBean(
            KeycloakPreAuthActionsFilter filter) {
        var registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}
