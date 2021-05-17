package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "Profile", primaryKeys = ["email"])
data class ProfileEntity(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "device_id") var device_id: String

) {
    constructor() : this( "", "", "")
}