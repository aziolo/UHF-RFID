package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "Profile", primaryKeys = ["id1", "id2"])
data class ProfileEntity(
    @ColumnInfo(name = "id1") val id1: Long,
    @ColumnInfo(name = "id2") val id2: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String

) {
    constructor() : this(0, 0, "", "")
}