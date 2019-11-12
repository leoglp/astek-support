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
import com.astek.asteksupport.utils.pdf.FourthPagePdfUtil.Companion.createFourthPage
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.borderRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.closeDocument
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.fillRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setUnderlineTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeInterviewPlan
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("DEPRECATION")
class FifthPagePdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page

        private const val TAG = "ThirdPdfPage"

        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity


        fun createFifthPage(
            canvasValue: Canvas, textPaintValue: TextPaint, activityValue: Activity,
            pdfDocumentValue: PdfDocument, pageValue: PdfDocument.Page
        ) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue

            footer("5")

            writeInterviewPlan(2)

            val firstTitle = activity.getString(R.string.pdfTitlePage5)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 150F, 1.0F, false)

            val infoText = activity.getString(R.string.futureTargetExplanation)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.ITALIC, 10F))
            drawText(infoText, 555, 20F, 175F, 1.0F, false)

            //Title Left Rectangle
            val leftTitle = activity.getString(R.string.pdfTarget)
            var rect = Rect(80, 210, 298, 230)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(leftTitle, 218, 80F, 211F, 1.0F, true)

            //Title Right Rectangle
            val rightTitle = activity.getString(R.string.evaluationCriteria)
            rect = Rect(298, 210, 515, 230)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(rightTitle, 218, 298F, 211F, 1.0F, true)

            retrieveValue()
        }

        private fun writeInLeftRectangle(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(80, top, 298, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 85F, y, 1.3F, false)
        }

        private fun writeInRightRectangle(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(298, top, 515, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 303F, y, 1.3F, false)
        }





        /************************************* DataBase *******************************************/
        private fun retrieveValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("futureTargetEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val target1 = "1 - " + document.get("target1").toString()
                        writeInLeftRectangle(target1,230,320,235F)
                        if (document.get("target2") != null) {
                            val target2 = "2 - " + document.get("target2").toString()
                            writeInLeftRectangle(target2,320,410,325F)
                        } else {
                            writeInLeftRectangle("2 - /",320,410,325F)
                        }
                        if (document.get("target3") != null) {
                            val target3 = "3 - " + document.get("target3").toString()
                            writeInLeftRectangle(target3,410,500,415F)
                        } else {
                            writeInLeftRectangle("3 - /",410,500,415F)
                        }

                        val result1 = document.get("result1").toString()
                        writeInRightRectangle(result1,230,320,235F)
                        if (document.get("result2") != null) {
                            val result2 = document.get("result2").toString()
                            writeInRightRectangle(result2,320,410,325F)
                        } else {
                            writeInRightRectangle("/",320,410,325F)
                        }
                        if (document.get("result3") != null) {
                            val result3 = document.get("result3").toString()
                            writeInRightRectangle(result3,410,500,415F)
                        } else {
                            writeInRightRectangle("/",410,500,415F)
                        }

                        // finish the page
                        pdfDocument.finishPage(page)

                        PdfUtil.createPage(6)

                        //Start Fourth Page
                        createFifthPage(PdfUtil.getCanvas(), PdfUtil.getTextPaint(), activity, PdfUtil.getPdfDocument(), PdfUtil.getPage())
                        closeDocument()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

    }
}