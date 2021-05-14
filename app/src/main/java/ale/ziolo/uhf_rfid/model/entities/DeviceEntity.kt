package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Device")
data class DeviceEntity(
    @PrimaryKey val tag: String
){
    constructor() : this("")
}