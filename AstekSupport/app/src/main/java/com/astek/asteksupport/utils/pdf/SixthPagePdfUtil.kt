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
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.borderRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.closeDocument
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.fillRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setUnderlineTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeInterviewPlan
import com.astek.asteksupport.utils.pdf.SeventhPagePdfUtil.Companion.createSeventhPage
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


@Suppress("DEPRECATION")
class SixthPagePdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page

        private const val TAG = "SixthPdfPage"

        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity


        fun createSixthPage(
            canvasValue: Canvas, textPaintValue: TextPaint, activityValue: Activity,
            pdfDocumentValue: PdfDocument, pageValue: PdfDocument.Page
        ) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue

            footer("6")

            writeInterviewPlan(3)

            val firstTitle = activity.getString(R.string.titleEvolution).toUpperCase(Locale.ROOT)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 150F, 1.0F, false)

            val infoText = activity.getString(R.string.pdfInfoPage6)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.ITALIC, 10F))
            drawText(infoText, 555, 20F, 175F, 1.0F, false)

            //Title Left Rectangle
            val leftTitle = activity.getString(R.string.pdfProfessionalTarget)
            var rect = Rect(20, 210, 207, 270)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(leftTitle, 175, 26F, 233F, 1.0F, true)

            //Title Middle Rectangle
            val middleTitle = activity.getString(R.string.justificationEvolution)
            rect = Rect(207, 210, 394, 270)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(middleTitle, 185, 209F, 233F, 1.0F, true)

            //Title Right Rectangle
            val rightTitle = activity.getString(R.string.meansEvolution)
            rect = Rect(394, 210, 580, 270)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(rightTitle, 175, 400F, 217F, 1.0F, true)

            retrieveValue("shortEvolution",270,370,275F)
        }

        private fun writeInLeftRectangle(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(20, top, 207, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 187, 25F, y, 1.3F, false)
        }

        private fun writeInMiddleRectangle(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(207, top, 394, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 187, 212F, y, 1.3F, false)
        }

        private fun writeInRightRectangle(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(394, top, 580, bottom)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 187, 399F, y, 1.3F, false)
        }

        private fun writeOthersInformationsTitle() {
            var title = activity.getString(R.string.mobilityEvolution) + " : "
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(title, 560, 20F, 575F, 1.3F, false)

            title = activity.getString(R.string.otherEvolution) + " : "
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(title, 560, 20F, 635F, 1.3F, false)

            title = activity.getString(R.string.pdfOthersWishes) + " : "
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(title, 560, 20F, 705F, 1.3F, false)

            retrieveOthersValue()
        }

        private fun writeOthersInformations(value: String , y: Float) {
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 560, 20F, y, 1.3F, false)
        }



        /************************************* DataBase *******************************************/
        private fun retrieveValue(collectionName: String,
                                  top: Int,
                                  bottom: Int,
                                  y: Float) {
            db.collection("users").document(employeeDocumentId)
                .collection(collectionName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        val justification = document.get("justification").toString()
                        writeInMiddleRectangle(justification, top, bottom,  y)
                        val means = document.get("means").toString()
                        writeInRightRectangle(means, top, bottom, y)

                        when(collectionName) {
                            "shortEvolution" -> {
                                val evolution = activity.getString(R.string.shortEvolution) + " : " +
                                        document.get("evolution").toString()
                                writeInLeftRectangle(evolution, top, bottom, y)
                                retrieveValue("mediumEvolution",370,470,375F)
                            }

                            "mediumEvolution" -> {
                                val evolution = activity.getString(R.string.mediumEvolution) + " : " +
                                        document.get("evolution").toString()
                                writeInLeftRectangle(evolution, top, bottom, y)
                                retrieveValue("longEvolution",470,570,475F)
                            }

                            "longEvolution" -> {
                                val evolution = activity.getString(R.string.longEvolution) + " : " +
                                        document.get("evolution").toString()
                                writeInLeftRectangle(evolution, top, bottom, y)
                                writeOthersInformationsTitle()
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        private fun retrieveOthersValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("othersEvolution")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val mobility = document.get("mobility").toString()
                        writeOthersInformations(mobility,587F)
                        val othersEvolution = document.get("othersEvolution").toString()
                        writeOthersInformations(othersEvolution,647F)
                        val othersWishes = document.get("others").toString()
                        writeOthersInformations(othersWishes,717F)


                        // finish the page
                        pdfDocument.finishPage(page)

                        PdfUtil.createPage(7)

                        //Start Fourth Page
                        createSeventhPage(PdfUtil.getCanvas(), PdfUtil.getTextPaint(), activity, PdfUtil.getPdfDocument(), PdfUtil.getPage())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

    }
}