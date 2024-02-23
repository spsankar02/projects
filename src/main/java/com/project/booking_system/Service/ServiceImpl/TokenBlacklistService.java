package com.Myproject.Bookingsystem.Service;

import java.util.Set;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
	public final Set<String> blacklistedTokens=ConcurrentHashMap.newKeySet();
	
	public void blacklisttoken(String token) {
		 blacklistedTokens.add(token);
	}
	 
	public boolean istokenblacklisted(String token) {
		return blacklistedTokens.contains(token);
	}
}
