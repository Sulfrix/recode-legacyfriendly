package io.github.homchom.recode.text

import io.github.homchom.recode.ui.IntegralColor
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.*
import net.minecraft.network.chat.HoverEvent.EntityTooltipInfo
import net.minecraft.network.chat.HoverEvent.ItemStackInfo
import net.minecraft.resources.ResourceLocation

typealias TextScope = TextBuilder.() -> Unit

inline fun text(builder: TextScope) = TextBuilder().apply(builder).text

@Suppress("PropertyName")
class TextBuilder(val style: Style = Style.EMPTY) {
    var text = TextComponent("")

    inline val black get() = ChatFormatting.BLACK
    inline val darkBlue get() = ChatFormatting.DARK_BLUE
    inline val darkGreen get() = ChatFormatting.DARK_GREEN
    inline val darkAqua get() = ChatFormatting.DARK_AQUA
    inline val darkRed get() = ChatFormatting.DARK_RED
    inline val darkPurple get() = ChatFormatting.DARK_PURPLE
    inline val gold get() = ChatFormatting.GOLD
    inline val gray get() = ChatFormatting.GRAY
    inline val darkGray get() = ChatFormatting.DARK_GRAY
    inline val blue get() = ChatFormatting.BLUE
    inline val green get() = ChatFormatting.GREEN
    inline val aqua get() = ChatFormatting.AQUA
    inline val red get() = ChatFormatting.RED
    inline val lightPurple get() = ChatFormatting.LIGHT_PURPLE
    inline val yellow get() = ChatFormatting.YELLOW
    inline val white get() = ChatFormatting.WHITE

    val OpenUrl: TextClickAction get() = ClickEventAction(ClickEvent.Action.OPEN_URL)
    val OpenFile: TextClickAction get() = ClickEventAction(ClickEvent.Action.OPEN_FILE)
    val RunCommand: TextClickAction get() = ClickEventAction(ClickEvent.Action.RUN_COMMAND)
    val SuggestCommand: TextClickAction get() = ClickEventAction(ClickEvent.Action.SUGGEST_COMMAND)
    val ChangePage: TextClickAction get() = ClickEventAction(ClickEvent.Action.CHANGE_PAGE)
    val CopyToClipboard: TextClickAction get() = ClickEventAction(ClickEvent.Action.COPY_TO_CLIPBOARD)

    object Insert : TextClickAction {
        override fun applyTo(style: Style, string: String): Style = style.withInsertion(string)
    }

    val ShowText: HoverEvent.Action<Component> get() = HoverEvent.Action.SHOW_TEXT
    val ShowItem: HoverEvent.Action<ItemStackInfo> get() = HoverEvent.Action.SHOW_ITEM
    val ShowEntity: HoverEvent.Action<EntityTooltipInfo> get() = HoverEvent.Action.SHOW_ENTITY

    fun append(component: MutableComponent, style: Style = this.style) {
        text += component.setStyle(style)
    }

    inline fun appendBlock(scope: TextScope, style: Style) {
        text += TextBuilder(style).apply(scope).text
    }

    fun translate(key: String, vararg args: Any) = append(TranslatableComponent(key, args))
    fun literal(string: String) = append(TextComponent(string))

    inline fun ChatFormatting.invoke(scope: TextScope) = appendBlock(scope, style + this)

    inline fun color(scope: TextScope, color: IntegralColor) =
        appendBlock(scope, style.withColor(color.toInt()))

    inline fun bold(scope: TextScope) =
        appendBlock(scope, style.withBold(true))

    inline fun underline(scope: TextScope) =
        appendBlock(scope, style.withUnderlined(true))

    inline fun italic(scope: TextScope) =
        appendBlock(scope, style.withItalic(true))

    inline fun strikethrough(scope: TextScope) =
        appendBlock(scope, style.withStrikethrough(true))

    inline fun obfuscated(scope: TextScope) =
        appendBlock(scope, style.withObfuscated(true))

    inline fun onClick(action: TextClickAction, value: String, scope: TextScope) =
        appendBlock(scope, action.applyTo(style, value))

    inline fun <T> onHover(action: HoverEvent.Action<T>, value: T, scope: TextScope) =
        appendBlock(scope, style.withHoverEvent(HoverEvent(action, value)))

    inline fun font(id: ResourceLocation, scope: TextScope) =
        appendBlock(scope, style.withFont(id))

    sealed interface TextClickAction {
        fun applyTo(style: Style, string: String): Style
    }

    @JvmInline
    private value class ClickEventAction(val vanilla: ClickEvent.Action) : TextClickAction {
        override fun applyTo(style: Style, string: String): Style =
            style.withClickEvent(ClickEvent(vanilla, string))
    }
}