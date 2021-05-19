package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Item")
data class ItemEntity(
    @PrimaryKey val tag: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "status") var status: String
){
    constructor() : this("", "", "")
}
