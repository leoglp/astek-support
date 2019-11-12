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
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("DEPRECATION")
class FourthPagePdfUtil {

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


        fun createThirdPage(
            canvasValue: Canvas, textPaintValue: TextPaint, activityValue: Activity,
            pdfDocumentValue: PdfDocument, pageValue: PdfDocument.Page
        ) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue

            footer("3")

            writeInterviewPlan()

            val firstTitle = "EVALUATION DES OBJECTIFS FIXES EAE ANNEE N-1"
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 160F, 1.0F, false)

            //Title Left Rectangle
            val leftTitle = "Rappel des objectifs"
            var rect = Rect(80, 190, 298, 210)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(leftTitle, 218, 80F, 191F, 1.0F, true)

            //Title Right Rectangle
            val rightTitle = "Résultats"
            rect = Rect(298, 190, 515, 210)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(rightTitle, 218, 298F, 191F, 1.0F, true)

            //First Rectangles
            retrieveFirstRectangleValue()
        }


        private fun writeInFirstLeftRectangle(value: String) {
            //First Left Rectangle
            val rect = Rect(80, 210, 298, 300)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 85F, 215F, 1.3F, false)
        }

        private fun writeInSecondLeftRectangle(value: String) {
            //Second Left Rectangle
            val rect = Rect(80, 300, 298, 390)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 85F, 305F, 1.3F, false)
        }

        private fun writeInThirdLeftRectangle(value: String) {
            //Third Left Rectangle
            val rect = Rect(80, 390, 298, 480)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 85F, 395F, 1.3F, false)
        }

        private fun writeInFirstRightRectangle(value: String) {
            //First Right Rectangle
            val rect = Rect(298, 210, 515, 300)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 303F, 215F, 1.3F, false)
        }

        private fun writeInSecondRightRectangle(value: String) {
            //Second Right Rectangle
            val rect = Rect(298, 300, 515, 390)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 303F, 305F, 1.3F, false)
        }

        private fun writeInThirdRightRectangle(value: String) {
            //Third Right Rectangle
            val rect = Rect(298, 390, 515, 480)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 303F, 395F, 1.3F, false)

            writeSecondInfo()
        }

        private fun writeSecondInfo() {

            //Title
            var rect = Rect(80, 480, 515, 500)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.BOLD, 10F))
            drawText(activity.getString(R.string.titleManagerAppreciation), 435, 80F, 481F, 1.0F, true)

            //First Rectangle
            rect = Rect(80, 500, 189, 520)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.verySatisfying),109,80F,502F,1.0F,true)

            //Second Rectangle
            rect = Rect(189, 500, 298, 520)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.satisfying),109,189F,502F,1.0F,true)

            //Third Rectangle
            rect = Rect(298, 500, 407, 520)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.medium),109,298F,502F,1.0F,true)

            //Fourth Rectangle
            rect = Rect(407, 500, 515, 520)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 9F))
            drawText(activity.getString(R.string.insufficient),109,407F,502F,1.0F,true)

            retrieveManagerEvaluationValue()
        }

        private fun fillRectangleValue(text: String) {
            val firstRect = Rect(80, 520, 189, 540)
            canvas.drawRect(firstRect, borderRectangleOptions())
            val secondRect = Rect(189, 520, 298, 540)
            canvas.drawRect(secondRect, borderRectangleOptions())
            val thirdRect = Rect(298, 520, 407, 540)
            canvas.drawRect(thirdRect, borderRectangleOptions())
            val fourthRect = Rect(407, 520, 515, 540)
            canvas.drawRect(fourthRect, borderRectangleOptions())

            when(text) {
                "Très Satisfaisant" -> canvas.drawRect(firstRect, fillRectangleOptions(Color.LTGRAY))
                "Satisfaisant" -> canvas.drawRect(secondRect, fillRectangleOptions(Color.LTGRAY))
                "Moyen" -> canvas.drawRect(thirdRect, fillRectangleOptions(Color.LTGRAY))
                "Insuffisant" -> canvas.drawRect(fourthRect, fillRectangleOptions(Color.LTGRAY))
            }

            retrieveCommentaryValue()


        }

        private fun writeCommentary(text: String) {
            textPaint.set(setUnderlineTextOptions(Color.BLACK, Typeface.BOLD, 14F))
            drawText(activity.getString(R.string.commentary), 555, 20F, 560F, 1.0F, false)

            val rect = Rect(20, 590, 580, 700)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(text, 555, 25F, 593F, 1.3F, false)

            pdfDocument.finishPage(page)

            closeDocument()
        }


        /************************************* DataBase *******************************************/
        private fun retrieveFirstRectangleValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("targetEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val target1 = "1 - " + document.get("target1").toString()
                        writeInFirstLeftRectangle(target1)
                        if (document.get("target2") != null) {
                            val target2 = "2 - " + document.get("target2").toString()
                            writeInSecondLeftRectangle(target2)
                        } else {
                            writeInSecondLeftRectangle("2 - /")
                        }
                        if (document.get("target3") != null) {
                            val target3 = "3 - " + document.get("target3").toString()
                            writeInThirdLeftRectangle(target3)
                        } else {
                            writeInThirdLeftRectangle("3 - /")
                        }

                        val result1 = document.get("result1").toString()
                        writeInFirstRightRectangle(result1)
                        if (document.get("result2") != null) {
                            val result2 = document.get("result2").toString()
                            writeInSecondRightRectangle(result2)
                        } else {
                            writeInSecondRightRectangle("/")
                        }
                        if (document.get("result3") != null) {
                            val result3 = document.get("result3").toString()
                            writeInThirdRightRectangle(result3)
                        } else {
                            writeInThirdRightRectangle("/")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        private fun retrieveManagerEvaluationValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("performanceEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        fillRectangleValue(document.get("performanceEvaluation").toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        private fun retrieveCommentaryValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("performanceEvaluation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.get("commentary") != null) {
                            val commentary = document.get("commentary").toString()
                            writeCommentary(commentary)
                        } else {
                            writeCommentary("/")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

    }
}