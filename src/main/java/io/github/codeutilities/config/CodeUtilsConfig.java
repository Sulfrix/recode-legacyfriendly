package io.github.codeutilities.config;

import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.util.file.FileUtil;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.Level;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class CodeUtilsConfig {

    public static JSONObject config;

    private final static Path configPath = FabricLoader.getInstance().getConfigDir().resolve("codeutilities.json");
    private final static String configPathString = String.valueOf(configPath);

    public static Screen getScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("title.codeutils.config"));
        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("category.codeutils.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // ============================================================================================================================

        general.addEntry(entryBuilder.startStrField(new TranslatableText("option.codeutils.optionA"), config.has("examplekey") ? config.getString("examplekey") : "")
                // !!!!!!!!!! to do key should always exist using cacheConfig()
                .setDefaultValue("This is the default value") // Recommended: Used when user click "Reset"
                .setTooltip(new TranslatableText("This option is awesome!"))// Optional: Shown when the user hover over this option
                .setSaveConsumer(newValue -> {
                    config.put("examplekey", newValue);
                })
                .build()); // Builds the option entry for cloth config

        // ============================================================================================================================

        builder.setSavingRunnable(() -> {
           updConfig(config);
        });

        return builder.build();
    }

    public static void cacheConfig() {
        File configFile = configPath.toFile();

        boolean success;

        // check if config file exists
        if (!configFile.exists()) {
            try {
                success = configFile.createNewFile();
                updConfig(new JSONObject());
                if (!success) CodeUtilities.log(Level.FATAL, "Error when parsing \"../.minecraft/config/codeutilities.json\"");
            } catch (IOException e) { e.printStackTrace(); }
        }

        // parse the file
        String jsonString = "";
        try { jsonString = FileUtil.readFile(configPathString, Charset.defaultCharset());
        } catch (IOException e) { e.printStackTrace(); }

        config = new JSONObject(jsonString);
    }

    public static void updConfig(JSONObject obj) {

        try {
            FileWriter configWriter = new FileWriter(configPathString);
            configWriter.write(obj.toString());
            configWriter.flush();
        } catch (IOException e) { e.printStackTrace(); }

    }

    // TEMPORARY OLD ONES
    public static boolean itemApi = true;
    public static boolean autoChatLocal = false;
    public static boolean autolagslayer = false;
    public static boolean discordRPC = true;
    public static boolean cpuOnScreen = true;
    public static boolean autotime = false;
    public static int autotimeval = 69420;
    public static boolean autonightvis = false;
    public static boolean dfCommands = true;
    public static boolean autoRC = false;
    public static boolean autofly = false;
    public static boolean hideMsgMatchingRegex = false;
    public static String hideMsgRegex = "";
    public static boolean hideVarScopeMessages = false;
    public static boolean hideMutedChat = false;
    public static boolean hideSessionSpy = false;
    public static boolean hideJoinLeaveMessages = false;
    public static ConfigSounds highlightSound = ConfigSounds.ShieldBlock;
    public static boolean highlightOwnSenderSound = false;
    public static float highlightSoundVolume = 3F;
    public static String highlightPrefix = "&e";
    public static boolean highlightIgnoreSender = false;
    public static String highlightMatcher = "{name}";
    public static boolean highlight = false;
    public static boolean discordRPCShowElapsed = true;
    public static int discordRPCTimeout = 15000;
    public static int colorMaxRender = 100;
    public static int colorLines = 100;
    public static CosmeticType cosmeticType = CosmeticType.Disabled;
    public enum CosmeticType {
        Enabled(),
        No_Event_Cosmetics(),
        Disabled()
    }
    public static int headMenuMaxRender = 1000;
    public static int fsNormal = 100;
    public static int fsFast = 1000;
    public static int fsMed = 350;
    public static boolean functionProcessSearch = true;
    public static boolean variableScopeView = true;
    public static boolean quickVarScope = true;
    public static boolean chestReplacement = false;
    public static boolean f3Tps = true;
    public static boolean dfButton = true;
    public static boolean errorSound = true;
    public static int signRenderDistance = 100;

    public static Object getConfig() {
        return config;
    }
}