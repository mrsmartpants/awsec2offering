/*
 * Copyright 2014 Ken K. Lin. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kenlin.awsec2offering;

import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class Cache {
	private static final long						MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

	private ConcurrentHashMap<String, ArrayNode>	cache = null;
	private	long									oldest = 0;
	private long									ttl = MILLIS_IN_DAY;
	
	public Cache() {
		cache = new ConcurrentHashMap<String, ArrayNode>();
	}
	
	public ArrayNode get(String key) {
		if (System.currentTimeMillis() > (oldest + ttl)) {
			cache.clear();
			oldest = 0;
			return null;
		} else {
			ArrayNode offerings = cache.get(key);
			return offerings;
		}
	}
	
	public ArrayNode put(String key, ArrayNode offerings) {
		if (oldest == 0)
			oldest = System.currentTimeMillis();
		return cache.put(key, offerings);
	}
}
