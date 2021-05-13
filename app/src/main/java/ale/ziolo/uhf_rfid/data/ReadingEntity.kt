package ale.ziolo.uhf_rfid.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Reading", primaryKeys = ["id1", "id2"])
data class ReadingEntity(
    @ColumnInfo(name = "id1") val id1: Long,
    @ColumnInfo(name = "id2") val id2: Long,
    @ColumnInfo(name = "tag") var tag: Double,
    @ColumnInfo(name = "date") var date: String
) {
    constructor() : this(0, 0, 0.0, "")
}
