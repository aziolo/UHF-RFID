package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Profile")
data class ProfileEntity(
    @ColumnInfo(name = "name") var name: String,
    @PrimaryKey var email: String,
    @ColumnInfo(name = "device_id") var device_id: String,
    @ColumnInfo(name = "token") var token: String

) {
    constructor() : this( "", "", "", "")
}