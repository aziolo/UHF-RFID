package ale.ziolo.uhf_rfid.data

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "Profile", primaryKeys = ["id1", "id2"])
data class ProfileEntity(
    @ColumnInfo(name = "id1") val id1: Long,
    @ColumnInfo(name = "id2") val id2: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "device_id") var device_id: String

) {
    constructor() : this(0, 0, "", "", "")
}