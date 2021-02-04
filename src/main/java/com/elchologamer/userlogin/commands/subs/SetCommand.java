package com.elchologamer.userlogin.commands.subs;

import com.elchologamer.userlogin.UserLogin;
import com.elchologamer.userlogin.api.CustomConfig;
import com.elchologamer.userlogin.api.QuickMap;
import com.elchologamer.userlogin.util.Path;
import com.elchologamer.userlogin.util.ULPlayer;
import com.elchologamer.userlogin.util.command.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetCommand extends SubCommand {

    private final UserLogin plugin;

    public SetCommand() {
        super("set", true);
        plugin = UserLogin.getPlugin();
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String[] args) {
        if (!"login".equals(args[0]) && !"spawn".equals(args[0])) return false;


        ULPlayer ulPlayer = plugin.getPlayer((Player) sender);
        Player player = ulPlayer.getPlayer();

        // Save location
        Location loc = player.getLocation();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();
        String world = loc.getWorld().getName();

        CustomConfig locationsConfig = plugin.getLocationsManager().getConfig();
        FileConfiguration config = locationsConfig.get();

        config.set(args[0] + ".x", x);
        config.set(args[0] + ".y", y);
        config.set(args[0] + ".z", z);
        config.set(args[0] + ".yaw", yaw);
        config.set(args[0] + ".pitch", pitch);
        config.set(args[0] + ".world", world);
        locationsConfig.save();

        // Send message
        ulPlayer.sendPathMessage(
                Path.SET,
                new QuickMap<>("type", (Object) args[0])
                        .set("x", x)
                        .set("y", y)
                        .set("z", "z")
                        .set("yaw", yaw)
                        .set("pitch", pitch)
                        .set("world", world)
        );

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> options = new ArrayList<>();
        if (args.length == 1) {
            options.add("login");
            options.add("spawn");
        }
        return options;
    }
}