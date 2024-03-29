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

import ru.blatfan.blatlibs.commandframework.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Stream;

public class CommandFramework implements CommandExecutor, TabCompleter {

	@NotNull
	private final Plugin plugin;

	@NotNull
	private final Map<Command, Map.Entry<Method, Object>> commands = new HashMap<>();

	@NotNull
	private final Map<Command, Map.Entry<Method, Object>> subCommands = new TreeMap<>(Comparator.comparing(Command::name).reversed());

	@NotNull
	private final Map<Completer, Map.Entry<Method, Object>> commandCompletions = new HashMap<>();

	@NotNull
	private final Map<Completer, Map.Entry<Method, Object>> subCommandCompletions = new TreeMap<>(Comparator.comparing(Completer::name).reversed());

	@NotNull
	private final Map<CommandSender, Map<Command, Long>> cooldowns = new HashMap<>();

	@NotNull
	private final SelfExpiringMap<CommandSender, Command> confirmations = new SelfExpiringHashMap<>();

	@NotNull
	private final Map<String, Function<CommandArguments, ?>> customParametersMap = new HashMap<>();

	@NotNull
	private Function<CommandArguments, Boolean> matchFunction = (arguments) -> false;

	@Nullable
	protected CommandMap commandMap;

	// Error Message Handler
	public static String ONLY_BY_PLAYERS         = ChatColor.RED + "This command is only executable by players!";
	public static String ONLY_BY_CONSOLE         = ChatColor.RED + "This command is only executable by console!";
	public static String NO_PERMISSION           = ChatColor.RED + "You don't have enough permission to execute this command!";
	public static String MUST_HAVE_OP            = ChatColor.RED + "You must have OP to execute this command!";
	public static String SHORT_ARG_SIZE          = ChatColor.RED + "Required argument length is less than needed!";
	public static String LONG_ARG_SIZE           = ChatColor.RED + "Required argument length greater than needed!";
	public static String WAIT_BEFORE_USING_AGAIN = ChatColor.RED + "You have to wait %ds before using this command again!";

	public CommandFramework(@NotNull Plugin plugin) {
		this.plugin = plugin;

		if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
			final SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();

			try {
				final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);

				commandMap = (CommandMap) field.get(manager);
			} catch (ReflectiveOperationException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void setMatchFunction(@NotNull Function<CommandArguments, Boolean> matchFunction) {
		this.matchFunction = matchFunction;
	}

	public <A, B extends A> void addCustomParameter(@NotNull Class<A> instanceClass, @NotNull Function<CommandArguments, B> function) {
		final String simpleName = instanceClass.getSimpleName();

		if (this.customParametersMap.containsKey(simpleName))
			throw new CommandException("Object type '%s' is already registered as a custom parameter!", simpleName);
		this.customParametersMap.put(simpleName, function);
	}

	public void registerCommands(@NotNull Object instance) {
		for (final Method method : instance.getClass().getMethods()) {
			final Command command = method.getAnnotation(Command.class);

			if (command != null) {
				registerCommand(command, method, instance);

				// Register all aliases as a plugin command. If it is a sub-command then register it as a sub-command.
				Stream.of(command.aliases()).forEach(alias -> registerCommand(Utils.createCommand(command, alias), method, instance));
			} else if (method.isAnnotationPresent(Completer.class)) {
				if (!List.class.isAssignableFrom(method.getReturnType())) {
					plugin.getLogger().log(Level.WARNING, "Skipped registration of ''{0}'' because it is not returning java.util.List type.", method.getName());
					continue;
				}

				final Completer completer = method.getAnnotation(Completer.class);

				if (completer.name().contains(".")) {
					subCommandCompletions.put(completer, Utils.mapEntry(method, instance));
				} else {
					commandCompletions.put(completer, Utils.mapEntry(method, instance));
				}
			}
		}

		subCommands.forEach((key, value) -> {
			final String splitName = key.name().split("\\.")[0];

			// Framework is going to work properly but this should not be handled that way.
			if (commands.keySet().stream().noneMatch(cmd -> cmd.name().equals(splitName))) {
				registerCommand(Utils.createCommand(key, splitName), null, null);
			}
		});
	}

	private void registerCommand(Command command, Method method, Object instance) {
		final String cmdName = command.name();

		if (cmdName.contains(".")) {
			subCommands.put(command, Utils.mapEntry(method, instance));
		} else {
			commands.put(command, Utils.mapEntry(method, instance));

			try {
				final Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
				constructor.setAccessible(true);

				final PluginCommand pluginCommand = constructor.newInstance(cmdName, plugin);
				pluginCommand.setTabCompleter(this);
				pluginCommand.setExecutor(this);
				pluginCommand.setUsage(command.usage());
				pluginCommand.setPermission(!command.permission().isEmpty() ? null : command.permission());
				pluginCommand.setDescription(command.desc());

				commandMap.register(cmdName, pluginCommand);
			} catch (Exception exception) {
				Utils.handleExceptions(exception);
			}
		}
	}

	public void unregisterCommand(@NotNull String commandName) {
		if (commandName.contains(".")) commandName = commandName.split("\\.")[0];

		final Map.Entry<Command, Map.Entry<Method, Object>> entry = this.getAssociatedCommand(commandName, new String[0]);

		if (entry == null) {
			plugin.getLogger().log(Level.WARNING, "Command removal is failed because there is no command named ''{0}''!", commandName);
			return;
		}

		final Command command = entry.getKey();
		final String name = command.name();
		final PluginCommand pluginCommand = plugin.getServer().getPluginCommand(name);

		Optional.ofNullable(pluginCommand).ifPresent(cmd -> {
			if (!pluginCommand.getPlugin().equals(plugin))
				return;

			try {
				cmd.unregister(commandMap);

				Field field = SimpleCommandMap.class.getDeclaredField("knownCommands");
				field.setAccessible(true);

				Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) field.get(commandMap);
				knownCommands.remove(name);
			} catch (Exception exception) {
				plugin.getLogger().log(Level.WARNING, "Something went wrong while trying to unregister command(name: {0}) from server!", name);
			}

			this.commands.remove(command);
			new HashMap<>(this.subCommands).keySet().stream().filter(subCmd -> subCmd.name().startsWith(name)).forEach(this.subCommands::remove); // Copy elements to new map to avoid modification exception
		});
	}

	public void unregisterCommands() {
		new HashMap<>(commands).keySet().stream().map(Command::name).forEach(this::unregisterCommand);
	}

	@Nullable
	private Map.Entry<Command, Map.Entry<Method, Object>> getAssociatedCommand(@NotNull String commandName, @NotNull String[] possibleArgs) {
		Command command = null;

		for (Command cmd : subCommands.keySet()) {
			final String name = cmd.name(), cmdName = commandName + (possibleArgs.length == 0 ? "" : "." + String.join(".", Arrays.copyOfRange(possibleArgs, 0, name.split("\\.").length - 1)));
			if (name.equals(cmdName)) {
				command = cmd;
				break;
			}

			if (name.equalsIgnoreCase(cmdName)) {
				command = cmd;
				break;
			}
		}

		if (command != null) {
			final long argCount = command.name().chars().mapToObj(c -> (char) c).filter(c -> c == '.').count();

			if (command.min() >= possibleArgs.length - argCount || possibleArgs.length - argCount <= command.max() || command.allowInfiniteArgs()) {
				return Utils.mapEntry(command, subCommands.get(command));
			}
		}

		for (Command cmd : commands.keySet()) {
			final String name = cmd.name();

			if (name.equalsIgnoreCase(commandName) || Stream.of(cmd.aliases()).anyMatch(commandName::equalsIgnoreCase)) {
				command = cmd;
				break;
			}
		}

		if (command != null) {
			if (command.min() >= possibleArgs.length || possibleArgs.length <= command.max() || command.allowInfiniteArgs()) {
				return Utils.mapEntry(command, commands.get(command));
			}
		}

		return null;
	}

	private boolean hasCooldown(final CommandSender sender, final Command command, Map.Entry<Command, Map.Entry<Method, Object>> entry) {
		final Method method = entry.getValue().getKey();

		if (method == null) return false;
		if (!method.isAnnotationPresent(Cooldown.class)) return false;

		final Cooldown cooldown = method.getAnnotation(Cooldown.class);

		if (cooldown.cooldown() <= 0) return false;

		final boolean isConsoleSender = sender instanceof ConsoleCommandSender;

		if (isConsoleSender && !cooldown.overrideConsole()) return false;
		if (!isConsoleSender && !cooldown.bypassPerm().isEmpty() && sender.hasPermission(cooldown.bypassPerm())) return false;

		final Map<Command, Long> cooldownMap = cooldowns.get(sender);

		if (cooldownMap == null) {
			cooldowns.put(sender, Utils.mapOf(command, System.currentTimeMillis()));
			return false;
		} else if (!cooldownMap.containsKey(command)) {
			cooldownMap.put(command, System.currentTimeMillis());

			cooldowns.replace(sender, cooldownMap);
			return false;
		}

		final long remainingSeconds = ((System.currentTimeMillis() - cooldownMap.get(command)) / 1000) % 60;
		final long cooldownInSeconds = cooldown.timeUnit().toSeconds(cooldown.cooldown());
		final int timeBetween = (int) (cooldownInSeconds - remainingSeconds); // less precious more accurate

		if (timeBetween > 0) {
			sender.sendMessage(String.format(WAIT_BEFORE_USING_AGAIN, timeBetween));
			return true;
		} else {
			cooldownMap.put(command, System.currentTimeMillis());

			cooldowns.replace(sender, cooldownMap);
			return false;
		}
	}

	private boolean checkConfirmations(final CommandSender sender, final Command command, Map.Entry<Command, Map.Entry<Method, Object>> entry) {
		final Method method = entry.getValue().getKey();

		if (method == null) return false;
		if (!method.isAnnotationPresent(Confirmation.class)) return false;

		final Confirmation confirmation = method.getAnnotation(Confirmation.class);

		if (confirmation.expireAfter() <= 0) return false;

		final boolean isConsoleSender = sender instanceof ConsoleCommandSender;

		if (isConsoleSender && !confirmation.overrideConsole()) return false;
		if (!isConsoleSender && !confirmation.bypassPerm().isEmpty() && sender.hasPermission(confirmation.bypassPerm())) return false;

		if (confirmations.containsKey(sender)) {
			confirmations.remove(sender);
			return false;
		} else {
			confirmations.put(sender, command, confirmation.timeUnit().toMillis(confirmation.expireAfter()));

			sender.sendMessage(confirmation.message());
			return true;
		}
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
		final Map.Entry<Command, Map.Entry<Method, Object>> entry = this.getAssociatedCommand(cmd.getName(), args);

		if (entry == null) {
			return matchFunction.apply(new CommandArguments(sender, cmd, label, args));
		}

		final Command command = entry.getKey();
		final String permission = command.permission();

		if (command.onlyOp() && !sender.isOp()) {
			sender.sendMessage(MUST_HAVE_OP);
			return true;
		}

		if ((!permission.isEmpty() && !sender.hasPermission(permission))) {
			sender.sendMessage(NO_PERMISSION);
			return true;
		}

		if (command.senderType() == Command.SenderType.PLAYER && !(sender instanceof Player)) {
			sender.sendMessage(ONLY_BY_PLAYERS);
			return true;
		}

		if (command.senderType() == Command.SenderType.CONSOLE && sender instanceof Player) {
			sender.sendMessage(ONLY_BY_CONSOLE);
			return true;
		}

		if (this.checkConfirmations(sender, command, entry))
			return true;

		if (this.hasCooldown(sender, command, entry))
			return true;

		final String[] splitted = command.name().split("\\.");
		final String[] newArgs = Arrays.copyOfRange(args, splitted.length - 1, args.length);

		if (this.checkArgumentLength(sender, command, args, splitted, newArgs))
			return true;

		final Runnable invocation = () -> {
			try {
				final Method method = entry.getValue().getKey();
				final Object instance = entry.getValue().getValue();

				if (method == null)
					return;

				method.invoke(instance, getParameterArray(method, new CommandArguments(sender, cmd, label, newArgs)));
			} catch (Exception exception) {
				Utils.handleExceptions(exception);
			}
		};

		if (command.async()) {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, invocation);
		} else {
			invocation.run();
		}

        return true;
    }

	@Nullable
	private Map.Entry<Completer, Map.Entry<Method, Object>> getAssociatedCompleter(@NotNull String commandName, @NotNull String[] possibleArgs) {
		Completer completer = null;

		for (Completer comp : subCommandCompletions.keySet()) {
			final String name = comp.name(), cmdName = commandName + (possibleArgs.length == 0 ? "" : "." + String.join(".", Arrays.copyOfRange(possibleArgs, 0, name.split("\\.").length - 1)));

			if (name.equalsIgnoreCase(cmdName) || Stream.of(comp.aliases()).anyMatch(commandName::equalsIgnoreCase)) {
				completer = comp;
				break;
			}
		}

		if (completer != null) {
			return Utils.mapEntry(completer, subCommandCompletions.get(completer));
		}

		for (Completer comp : commandCompletions.keySet()) {
			final String name = comp.name();

			if (name.equalsIgnoreCase(commandName) || Stream.of(comp.aliases()).anyMatch(commandName::equalsIgnoreCase)) {
				completer = comp;
				break;
			}
		}

		if (completer != null) {
			return Utils.mapEntry(completer, commandCompletions.get(completer));
		}

		return null;
	}

	@NotNull
	private Object[] getParameterArray(Method method, CommandArguments commandArguments) {
		final Parameter[] parameters = method.getParameters();
		final Object[] methodParameters = new Object[parameters.length];

		for (int i = 0; i < parameters.length; i++) {
			final String simpleName = parameters[i].getType().getSimpleName();

			if ("CommandArguments".equals(simpleName)) {
				methodParameters[i] = commandArguments;
				continue;
			}

			if (!customParametersMap.containsKey(simpleName))
				throw new CommandException("Custom parameter(%s) is requested but return function is not found!", simpleName);

			methodParameters[i] = customParametersMap.get(simpleName).apply(commandArguments);
		}

		return methodParameters;
	}

	private boolean checkArgumentLength(CommandSender sender, Command command, String[] args, String[] splitted, String[] newArgs) {
		if (args.length < command.min() + splitted.length - 1) {
			sender.sendMessage(SHORT_ARG_SIZE);
			return true;
		}

		if (newArgs.length > Math.max(command.max(), newArgs.length + 1)) {
			sender.sendMessage(LONG_ARG_SIZE);
			return true;
		}

		return false;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
		final Map.Entry<Completer, Map.Entry<Method, Object>> entry = this.getAssociatedCompleter(cmd.getName(), args);

		if (entry == null)
			return null;

		final String permission = entry.getKey().permission();

		if (!permission.isEmpty() && !sender.hasPermission(permission))
			return null;

		try {
			final Method method = entry.getValue().getKey();
			final Object instance = entry.getValue().getValue();
			final Object completer = method.invoke(instance, getParameterArray(method, new CommandArguments(sender, cmd, label, args)));

			return (List<String>) completer;
		} catch (Exception exception) {
			Utils.handleExceptions(exception);
		}

		return null;
	}

	@NotNull
	@Contract(pure = true)
	public List<Command> getSubCommands() {
		return new ArrayList<>(this.subCommands.keySet());
	}

	@NotNull
	@Contract(pure = true)
	public List<Command> getCommands() {
		List<Command> commands = new ArrayList<>(this.commands.keySet());
		commands.addAll(this.subCommands.keySet());

		return commands;
	}
}