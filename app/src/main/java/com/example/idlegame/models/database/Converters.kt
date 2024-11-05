import androidx.room.TypeConverter
import androidx.compose.ui.graphics.Color

class Converters {
    @TypeConverter
    fun fromColorInt(value: Int): Color = Color(value)

    @TypeConverter
    fun toColorInt(color: Color): Int = color.value.toInt()
}
