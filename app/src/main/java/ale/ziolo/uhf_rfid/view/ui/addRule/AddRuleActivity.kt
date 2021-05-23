package ale.ziolo.uhf_rfid.view.ui.addRule

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.databinding.ActivityAddRuleBinding
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.view.ui.main.MainActivity
import ale.ziolo.uhf_rfid.firestore.FirestoreViewModel
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.time.LocalTime
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class AddRuleActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val addRuleViewModel: AddRuleViewModel by lazy {
        ViewModelProviders.of(this).get(
            AddRuleViewModel::class.java
        )
    }
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(this).get(
            FirestoreViewModel::class.java
        )
    }
    private lateinit var dbList: List<ItemEntity>
    private var checked: MutableList<Boolean> = ArrayList()
    private var final: MutableList<ItemEntity> = mutableListOf()

    private lateinit var binding: ActivityAddRuleBinding
    private lateinit var startTime: String
    private lateinit var stopTime: String
    private lateinit var chooser: TimePickerDialog
    private lateinit var chooserTimeStop: TimePickerDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStartTime.setOnClickListener {
            val c = java.util.Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)
            chooser = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, h, m ->
                startTime = LocalTime.of(h, m).toString()
                binding.textStartTime.text = startTime
            }, hour, minute, true)
            chooser.show()
        }
        binding.buttonStopTime.setOnClickListener {
            val c = java.util.Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)
            chooserTimeStop = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, h, m ->
                stopTime = LocalTime.of(h, m).toString()
                binding.textStopTime.text = stopTime
            }, hour, minute, true)
            chooserTimeStop.show()
        }

        dbList = addRuleViewModel.getList()
        for (i in dbList.indices) {
            checked.add(false)
        }

        for (i in dbList.indices) {
            val checkBox = CheckBox(this)
            checkBox.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            checkBox.text = dbList[i].name
            checkBox.scaleX = 1.2F
            checkBox.scaleY = 1.2F
            checkBox.setTextColor(ContextCompat.getColor(this, R.color.white))
            CompoundButtonCompat.setButtonTintList(
                checkBox,
                ColorStateList.valueOf(resources.getColor(R.color.white))
            )
            checkBox.setOnClickListener(View.OnClickListener {
                if (checkBox.isChecked) checked[i] = true
                if (!checkBox.isChecked) checked[i] = false
            })
            binding.checkboxLayout.addView(checkBox)
        }

        binding.buttonAddRule.setOnClickListener {
            finalList()
            check()
        }
    }

    private fun finalList() {
        for (i in checked.indices) {
            if (checked[i]) final.add(dbList[i])
        }
    }

    private fun check() {
        if (binding.inputRuleName.text.isNotEmpty() && final.isNotEmpty() && binding.textStartTime.text.isNotEmpty() && binding.textStopTime.text.isNotEmpty()) {
            addRule(binding.inputRuleName.text.toString())
        } else {
            Toast.makeText(this, resources.getString(R.string.complete_all), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addRule(title: String) {
        val id: Long = UUID.randomUUID().mostSignificantBits
        try {
            var rule = RuleEntity()
            if (final.size == 1) {
                rule = RuleEntity(
                    id,
                    startTime,
                    stopTime,
                    title,
                    final[0].tag,
                    final[0].name,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    final[0].tag
                )
                addRuleViewModel.insert(rule)
                firestoreViewModel.saveRule(rule)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                final.clear()
                checked.clear()
                Toast.makeText(
                    this,
                    resources.getString(R.string.rule_saved),
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (final.size == 2) {
                rule = RuleEntity(
                    id,
                    startTime,
                    stopTime,
                    title,
                    final[0].tag,
                    final[0].name,
                    final[1].tag,
                    final[1].name,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
                showRadioButtonDialog(rule)
            }
            if (final.size == 3) {
                rule = RuleEntity(
                    id,
                    startTime,
                    stopTime,
                    title,
                    final[0].tag,
                    final[0].name,
                    final[1].tag,
                    final[1].name,
                    final[2].tag,
                    final[2].name,
                    "",
                    "",
                    "",
                    "",
                    ""
                )
                showRadioButtonDialog(rule)
            }
            if (final.size == 4) {
                rule = RuleEntity(
                    id,
                    startTime,
                    stopTime,
                    title,
                    final[0].tag,
                    final[0].name,
                    final[1].tag,
                    final[1].name,
                    final[2].tag,
                    final[2].name,
                    final[3].tag,
                    final[3].name,
                    "",
                    "",
                    ""
                )
                showRadioButtonDialog(rule)
            }
            if (final.size == 5) {
                rule = RuleEntity(
                    id,
                    startTime,
                    stopTime,
                    title,
                    final[0].tag,
                    final[0].name,
                    final[1].tag,
                    final[1].name,
                    final[2].tag,
                    final[2].name,
                    final[3].tag,
                    final[3].name,
                    final[4].tag,
                    final[4].name,
                    ""
                )
                showRadioButtonDialog(rule)
            }

        } catch (e: Exception) {
        }
    }

    private fun showRadioButtonDialog(old: RuleEntity) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.radiobutton_dialog)
        val rg = dialog.findViewById(R.id.radio_group) as RadioGroup
        for (i in final.indices) {
            val rb = RadioButton(this)
            rb.text = final[i].name
            rg.addView(rb)
        }
        dialog.show()

        rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val childCount = group.childCount
            for (x in 0 until childCount) {
                val btn = group.getChildAt(x) as RadioButton
                if (btn.id == checkedId) {
                    try {
                        val rule = RuleEntity(
                            old.id,
                            old.start,
                            old.stop,
                            old.title,
                            old.tag1,
                            old.name1,
                            old.tag2,
                            old.name2,
                            old.tag3,
                            old.name3,
                            old.tag4,
                            old.name4,
                            old.tag5,
                            old.name5,
                            final[x].tag
                        )
                        addRuleViewModel.insert(rule)
                        firestoreViewModel.saveRule(rule)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        final.clear()
                        checked.clear()
                        Toast.makeText(
                            this,
                            resources.getString(R.string.rule_saved),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: java.lang.Exception) {
                    }

                }
            }
        })
    }

}