package me.reasonless.codeutilities.commands.help;

import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import me.reasonless.codeutilities.Main;
import me.reasonless.codeutilities.util.MinecraftColors;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class HelpCommand {
	
	static String version = Main.MOD_VERSION;
	
	public static int execute(MinecraftClient mc, CommandContext<CottonClientCommandSource> ctx) {
		mc.player.sendMessage(new LiteralText(MinecraftColors.GRAY + "" + MinecraftColors.STRIKETHROUGH + "========================================="), false);
		mc.player.sendMessage(new LiteralText(MinecraftColors.DARK_AQUA + "" + MinecraftColors.BOLD + "Code" + MinecraftColors.AQUA + "" + MinecraftColors.BOLD + "Utilities" + MinecraftColors.GRAY + " - " + MinecraftColors.RESET + "v" + version), false);
		mc.player.sendMessage(new LiteralText(" "), false);
		mc.player.sendMessage(new LiteralText(MinecraftColors.YELLOW + "/codeutilities music"), false);
		mc.player.sendMessage(new LiteralText("Shows all music commands"), false);
		mc.player.sendMessage(new LiteralText(" "), false);
		mc.player.sendMessage(new LiteralText(MinecraftColors.YELLOW + "/codeutilities image"), false);
		mc.player.sendMessage(new LiteralText("Shows all image commands"), false);
		mc.player.sendMessage(new LiteralText(MinecraftColors.GRAY + "" + MinecraftColors.STRIKETHROUGH + "========================================="), false);
		return 1;
	}
}
