package com.meme.ala.core.config;

import com.meme.ala.common.EntityCreator;
import com.meme.ala.core.auth.jwt.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

final class WithUserDetailsSecurityContextFactory
        implements WithSecurityContextFactory<AlaWithAccount> {

    public SecurityContext createSecurityContext(AlaWithAccount withUser) {
        String email = withUser.value();
        UserDetails principal = new UserDetailsImpl(EntityCreator.testMember());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}