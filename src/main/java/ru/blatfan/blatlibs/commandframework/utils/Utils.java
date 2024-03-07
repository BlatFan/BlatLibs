/*
 * Command Framework - Annotation based command framework
 * Copyright (C) 2024  Berke Akçen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ru.blatfan.blatlibs.commandframework.utils;

import ru.blatfan.blatlibs.commandframework.Command;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.Annotation;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@ApiStatus.Internal
public class Utils {

	private Utils() {
	}

	public static int getInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException | NullPointerException ignored) {
			return 0;
		}
	}

	public static double getDouble(String string) {
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException | NullPointerException ignored) {
			return 0d;
		}
	}

	public static long getLong(String string) {
		try {
			return Long.parseLong(string);
		} catch (NumberFormatException | NullPointerException ignored) {
			return 0L;
		}
	}

	public static float getFloat(String string) {
		try {
			return Float.parseFloat(string);
		} catch (NumberFormatException | NullPointerException ignored) {
			return 0F;
		}
	}

	public static <K, V> Map.Entry<K, V> mapEntry(K a, V b) {
		return new AbstractMap.SimpleEntry<>(a, b);
	}

	public static <K, V> Map<K, V> mapOf(K a, V b) {
		return mapOf(mapEntry(a, b));
	}

	@SafeVarargs
	public static <K, V> Map<K, V> mapOf(Map.Entry<K, V>... a) {
		return Arrays.stream(a).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (b, c) -> c));
	}

	public static void handleExceptions(Exception exception) {
		Throwable cause = exception.getCause();

		if (cause == null) exception.printStackTrace();
		else cause.printStackTrace();
	}

	public static Command createCommand(final Command command, final String commandName) {
		return new Command() {

			@Override
			public String name() {
				return commandName;
			}

			@Override
			public String permission() {
				return command.permission();
			}

			@Override
			public String[] aliases() {
				return new String[0];
			}

			@Override
			public String desc() {
				return command.desc();
			}

			@Override
			public String usage() {
				return command.usage();
			}

			@Override
			public int min() {
				return command.min();
			}

			@Override
			public int max() {
				return command.max();
			}

			@Override
			public boolean allowInfiniteArgs() {
				return command.allowInfiniteArgs();
			}

			@Override
			public boolean onlyOp() {
				return command.onlyOp();
			}

			@Override
			public boolean async() {
				return command.async();
			}

			@Override
			public SenderType senderType() {
				return command.senderType();
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				return command.annotationType();
			}
		};
	}
}