package ru.blatfan.blatlibs.commandframework.utils;

import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public interface SelfExpiringMap<K, V> extends Map<K, V> {

	V put(K key, V value, long lifeTimeMs);
}