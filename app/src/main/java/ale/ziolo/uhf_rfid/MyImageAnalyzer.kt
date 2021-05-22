package ale.ziolo.uhf_rfid

import ale.ziolo.uhf_rfid.view.BarcodeResultBottomSheet
import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.fragment.app.FragmentManager
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class MyImageAnalyzer(
    private val fragmentManager: FragmentManager, state: String
) : ImageAnalysis.Analyzer {

    private var state = state
    private var bottomSheet = BarcodeResultBottomSheet(state)

    lateinit var tagtag: String

    override fun analyze(imageProxy: ImageProxy) {
        scanBarcode(imageProxy)
    }

    @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
    private fun scanBarcode(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(inputImage)
                .addOnCompleteListener {
                    imageProxy.close()
                    if (it.isSuccessful) {
                        readBarcodeData(it.result as List<Barcode>)
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    private fun readBarcodeData(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            when (barcode.valueType) {
                Barcode.TYPE_TEXT -> {
                    tagtag = barcode.displayValue.toString()
                    if (!bottomSheet.isAdded)
                        bottomSheet.show(fragmentManager, "")
                        bottomSheet.showTag(tagtag)
                }
            }
        }
    }
}