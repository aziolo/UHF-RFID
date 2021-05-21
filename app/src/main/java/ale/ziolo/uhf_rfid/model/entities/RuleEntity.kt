package ale.ziolo.uhf_rfid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rule")
data class RuleEntity(
    @PrimaryKey var id: Long,
    @ColumnInfo(name = "start") var start: String,
    @ColumnInfo(name = "stop") var stop: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "tag1") var tag1: String,
    @ColumnInfo(name = "tag2") var tag2: String,
    @ColumnInfo(name = "name1") var name1: String,
    @ColumnInfo(name = "name2") var name2: String,
    @ColumnInfo(name = "priority") var priority: String
) {
    constructor() : this(0, "","", "","","","","", "")
}