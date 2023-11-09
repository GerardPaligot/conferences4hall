package org.gdglille.devfest.android.theme.m3.style.markdowns

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.RichTextThemeIntegration
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.android.theme.m3.style.previews.ThemedPreviews
import org.gdglille.devfest.android.theme.m3.style.socials.SocialsSectionDefaults

@Composable
fun MarkdownText(
    text: String,
    modifier: Modifier = Modifier,
    bodyColor: Color = SocialsSectionDefaults.bodyColor,
    bodyTextStyle: TextStyle = SocialsSectionDefaults.bodyTextStyle
) {
    RichTextThemeIntegration(
        textStyle = { bodyTextStyle },
        ProvideTextStyle = null,
        contentColor = { bodyColor },
        ProvideContentColor = null,
    ) {
        RichText(modifier = modifier) {
            Markdown(text)
        }
    }
}

@ThemedPreviews
@Composable
internal fun SocialsSectionPreview() {
    Conferences4HallTheme {
        MarkdownText(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        )
    }
}