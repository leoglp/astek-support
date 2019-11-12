package com.astek.asteksupport.utils.pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.text.TextPaint
import android.util.Log
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeDocumentId
import com.astek.asteksupport.utils.MailUtil
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.borderRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.closeDocument
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawTextWithoutJustification
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setUnderlineTextOptions
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


@Suppress("DEPRECATION")
class EighthPagePdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page

        private const val TAG = "SecondPdfPage"

        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity


        fun createEighthPage(canvasValue: Canvas , textPaintValue: TextPaint , activityValue: Activity ,
                             pdfDocumentValue: PdfDocument , pageValue: PdfDocument.Page) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue

            footer("8")

            val firstTitle = activity.getString(R.string.titleSynthesis).toUpperCase(Locale.ROOT)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 100F,1.0F, false)

            //First Rectangle
            retrieveValue()
        }


        private fun writeRectangle(value: String) {
            var rect = Rect(20, 130, 580, 280)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 555, 25F, 170F, 1.3F, false)

            val employeeSignature = activity.getString(R.string.pdfEmployeeSignature)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 13F))
            drawText(employeeSignature, 200, 20F, 290F,1.0F, false)

            val managerSignature = activity.getString(R.string.pdfEmployeeSignature)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 13F))
            drawText(managerSignature, 200, 398F, 290F,1.0F, false)

            val separation = activity.getString(R.string.pdfSeparation)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 12F))
            drawTextWithoutJustification(separation, 555, 20F, 420F,1.0F, false)

            val decision = activity.getString(R.string.pdfDecision)
            textPaint.set(setUnderlineTextOptions(Color.BLACK, Typeface.BOLD, 14F))
            drawTextWithoutJustification(decision, 555, 20F, 450F,1.0F, false)

            rect = Rect(20, 480, 580, 630)
            canvas.drawRect(rect, borderRectangleOptions())

            // finish the page
            pdfDocument.finishPage(page)

            closeDocument()

            MailUtil.sendMailWithPdf(activity)
        }


        /************************************* DataBase *******************************************/
        private fun retrieveValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("synthesis")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val synthesis = document.get("synthesis").toString()
                        writeRectangle(synthesis)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

    }

}