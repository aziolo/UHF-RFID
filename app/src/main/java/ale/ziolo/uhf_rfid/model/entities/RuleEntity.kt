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
    @ColumnInfo(name = "name1") var name1: String,
    @ColumnInfo(name = "tag2") var tag2: String,
    @ColumnInfo(name = "name2") var name2: String,
    @ColumnInfo(name = "tag3") var tag3: String,
    @ColumnInfo(name = "name3") var name3: String,
    @ColumnInfo(name = "tag4") var tag4: String,
    @ColumnInfo(name = "name4") var name4: String,
    @ColumnInfo(name = "tag5") var tag5: String,
    @ColumnInfo(name = "name5") var name5: String,
    @ColumnInfo(name = "priority") var priority: String
) {
    constructor() : this(0, "","", "","","","", "","","","", "","","","")
}