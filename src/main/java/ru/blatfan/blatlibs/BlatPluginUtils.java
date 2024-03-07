package ru.blatfan.blatlibs;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

public class BlatPluginUtils {
    @Getter
    public static Collection<BlatPlugin> pluginList = Arrays.asList(BlatLibs.getInstance());
}
