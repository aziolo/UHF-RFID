package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rule")
data class RuleEntity(
    @PrimaryKey var id: Long,
    @ColumnInfo(name = "start") var start: String,
    @ColumnInfo(name = "stop") var stop: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "tag1") var tag1: String,
    @ColumnInfo(name = "tag2") var tag2: String
) {
    constructor() : this(0, "", "","","", "")
}