interface Attachment {
    val type: String
}

data class Video(
    val id: Int,
    val ownerId: Int,
    val duration: Int,
    val title: String,

    )

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo_130: String,
    val photo_604: String
)

data class Audio(
    val id: Int,
    val ownerId: Int = 0,
    val duration: Int = 0,
    val title: String = "",
    val artist: String = "",
    val url: String = ""
)

data class Doc(
    val id: Int,
    val ownerId: Int,
    val size: Int,
    val title: String,
    val url: String,
    val type: Int
)

data class Graffiti(
    val id: Int,
    val ownerId: Int,
    val photo_130: String,
    val photo_604: String
)

data class PhotoAttachment(val photo: Photo, override val type: String = "photo") : Attachment
data class VideoAttachment(val video: Video, override val type: String = "video") : Attachment
data class AudioAttachment(val audio: Audio, override val type: String = "audio") : Attachment
data class DocAttachment(val doc: Doc, override val type: String = "doc") : Attachment
data class GraffitiAttachment(val graffiti: Graffiti, override val type: String = "graffiti") : Attachment
