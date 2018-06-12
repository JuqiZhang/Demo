package com.ynuedu.partbuilding.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static String REALM = "EQuake_OAUTH_REALM";


    public static final String OAUTH_CLIENT_ID = "oauth_eQuake";
    //!For Spring Boot 2 you need to Bcrypt CLIENT_SECRET,so in AuthorizationServerConfig.java
    //Neeed encode password default
    public static final String OAUTH_CLIENT_SECRET = "$2a$10$A3f3S9tUsIcz/.7XYSyAKe9hI0Rvxejk7fUBJCH8lmL.SCO.n3neW";
            //"oauth_eQuake_secret";
    public static final String RESOURCE_ID = "oauth-trusted-eQuake";
    public static final String[] SCOPES = { "read", "write" };

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    //
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
    @Bean
    public UserDetailsServiceImpl userService() {
        return new UserDetailsServiceImpl();
    }*/

    void testPassword()
    {
        //clients.withClientDetails()
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(OAUTH_CLIENT_SECRET);
        System.out.println(hashedPassword);
    }

   //  @Autowired

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        testPassword();

        clients.inMemory()

                .withClient(OAUTH_CLIENT_ID)
                .scopes(SCOPES)
                .secret(OAUTH_CLIENT_SECRET)
                .resourceIds(RESOURCE_ID)

                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")

                .accessTokenValiditySeconds(60 * 30) // 30min
                .refreshTokenValiditySeconds(60 * 60 * 24); // 24h
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(tokenStore)
                .userApprovalHandler(userApprovalHandler)
               .authenticationManager(authenticationManager)
                .userDetailsService(userService);
               // .
    }


    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);


        return handler;
    }

}