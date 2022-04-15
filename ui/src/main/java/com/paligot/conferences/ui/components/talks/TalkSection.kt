package com.paligot.conferences.ui.components.talks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.RichTextThemeIntegration
import com.paligot.conferences.models.TalkUi
import com.paligot.conferences.ui.theme.Conferences4HallTheme

@Composable
fun TalkSection(
    talk: TalkUi,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    titleTextStyle: TextStyle = MaterialTheme.typography.h6,
    bodyTextStyle: TextStyle = MaterialTheme.typography.body2
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = talk.title, color = color, style = titleTextStyle)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = talk.date, color = color, style = bodyTextStyle)
        Room(room = talk.room, color = color, style = bodyTextStyle)
        talk.level?.let {
            Text(text = it, color = color, style = bodyTextStyle)
        }
        Spacer(modifier = Modifier.height(8.dp))
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            RichTextThemeIntegration(
                textStyle = { bodyTextStyle },
                ProvideTextStyle = null,
                contentColor = { color.copy(LocalContentAlpha.current) },
                ProvideContentColor = null,
            ) {
                RichText {
                    Markdown(talk.abstract)
                }
            }
        }
    }
}

@Preview
@Composable
fun TalkSectionPreview() {
    Conferences4HallTheme {
        TalkSection(talk = TalkUi.fake)
    }
}
