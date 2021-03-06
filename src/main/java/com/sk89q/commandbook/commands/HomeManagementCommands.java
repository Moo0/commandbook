// $Id$
/*
 * Copyright (C) 2010, 2011 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.commandbook.commands;

import com.sk89q.commandbook.CommandBookPlugin;
import com.sk89q.commandbook.locations.NamedLocation;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class HomeManagementCommands {

    @Command(
            aliases = {"del", "delete", "remove", "rem"},
            usage = "[homename] [world]",
            desc = "Remove a home",
            min = 0, max = 2 )
    @CommandPermissions({"commandbook.home.remove"})
    public static void remove(CommandContext args, CommandBookPlugin plugin,
        CommandSender sender) throws CommandException {
        World world;
        String homeName = sender.getName();
        if (args.argsLength() == 2) {
            world = plugin.matchWorld(sender, args.getString(1));
        } else {
            world = plugin.checkPlayer(sender).getWorld();
        }
        if (args.argsLength() > 0) homeName = args.getString(0);
        NamedLocation home = plugin.getHomesManager().get(world, homeName);
        if (home == null) {
            throw new CommandException("No home found for " + homeName + " in world " + world.getName());
        }
        if (!home.getCreatorName().equals(sender.getName())) {
            plugin.checkPermission(sender, "commandbook.home.remove.other");
        }

        plugin.getHomesManager().remove(world, homeName);
        sender.sendMessage(ChatColor.YELLOW + "Home for " + homeName + " removed.");
    }

}
