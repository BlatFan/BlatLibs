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

package ru.blatfan.blatlibs.commandframework;

import ru.blatfan.blatlibs.commandframework.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public class CommandArguments {

	private final CommandSender commandSender;
	private final Command command;
	private final String label, arguments[];

	public CommandArguments(CommandSender commandSender, Command command, String label, String... arguments) {
		this.commandSender = commandSender;
		this.command = command;
		this.label = label;
		this.arguments = arguments;
	}

	@SuppressWarnings("unchecked")
	@NotNull
	public <T extends CommandSender> T getSender() {
		return (T) commandSender;
	}

	@NotNull
	public Command getCommand() {
		return command;
	}

	@NotNull
	public String getLabel() {
		return label;
	}

	@NotNull
	public String[] getArguments() {
		return arguments;
	}

	@Nullable
	public String getArgument(int i) {
		return arguments.length > i && i >= 0 ? arguments[i] : null;
	}

	public int getArgumentAsInt(int i) {
		return Utils.getInt(this.getArgument(i));
	}

	public double getArgumentAsDouble(int i) {
		return Utils.getDouble(this.getArgument(i));
	}

	public float getArgumentAsFloat(int i) {
		return Utils.getFloat(this.getArgument(i));
	}

	public long getArgumentAsLong(int i) {
		return Utils.getLong(this.getArgument(i));
	}

	public boolean getArgumentAsBoolean(int i) {
		return "true".equalsIgnoreCase(this.getArgument(i));
	}

	public boolean isArgumentsEmpty() {
		return arguments.length == 0;
	}

	public void sendMessage(String message) {
		if (message == null)
			return;
		commandSender.sendMessage(message);
	}

	public boolean isSenderConsole() {
		return !isSenderPlayer();
	}

	public boolean isSenderPlayer() {
		return commandSender instanceof Player;
	}

	public boolean hasPermission(String permission) {
		return permission.isEmpty() || commandSender.hasPermission(permission);
	}

	public int getLength() {
		return arguments.length;
	}

	public Optional<Player> getPlayer(String name) {
		return Optional.ofNullable(Bukkit.getPlayer(name));
	}

	public Optional<Player> getPlayer(int i) {
        return this.getPlayer(this.getArgument(i));
    }

	public String concatenateArguments() {
		return String.join(" ", arguments);
	}

	public String concatenateRangeOf(int from, int to) {
		return String.join(" ", Arrays.copyOfRange(arguments, from, to));
	}

	public boolean isNumeric(int i) {
		return this.isNumeric(this.getArgument(i));
	}

	public boolean isNumeric(String string) {
		if (string == null || string.isEmpty())
			return false;

		return string.chars().allMatch(Character::isDigit);
	}

	public boolean isInteger(int i) {
		return this.isInteger(this.getArgument(i));
	}

	public boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException | NullPointerException exception) {
			return false;
		}
	}

	public boolean isFloatingDecimal(int i) {
		return this.isFloatingDecimal(this.getArgument(i));
	}

	public boolean isFloatingDecimal(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException | NullPointerException exception) {
			return false;
		}
	}
}