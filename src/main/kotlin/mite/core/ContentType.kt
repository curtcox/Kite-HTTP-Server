package mite.core

/**
 * Content AKA MIME types
 */
enum class ContentType(val streamName: String, val binary:Boolean = true) {

    HTML("text/html",false),
    TEXT("text/plain",false),
    ICON("image/x-icon"),
    GIF("image/gif"),
    CLASS("application/octet-stream"),
    JPEG("image/jpeg");

    companion object {
        fun auto(content:String) = if (seemsLikeHTML(content)) HTML else TEXT
        fun seemsLikeHTML(content:String) =
            content.length > 10 &&
            content.substring(0,10).toLowerCase().contains("<html>")
    }
}