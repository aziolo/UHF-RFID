package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Item", primaryKeys = ["id1", "id2"])
data class ItemEntity(
    @ColumnInfo(name = "id1") val id1: Long,
    @ColumnInfo(name = "id2") val id2: Long,
    @ColumnInfo(name = "tag") var tag: Double
){
    constructor() : this(0, 0, 0.0)
}
