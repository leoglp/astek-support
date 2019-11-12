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
import com.astek.asteksupport.utils.pdf.EighthPagePdfUtil.Companion.createEighthPage
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.borderRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawTextWithoutJustification
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.fillRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setUnderlineTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeInterviewPlan
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


@Suppress("DEPRECATION")
class SeventhPagePdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page

        private const val TAG = "SeventhPdfPage"

        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity


        fun createSeventhPage(
            canvasValue: Canvas, textPaintValue: TextPaint, activityValue: Activity,
            pdfDocumentValue: PdfDocument, pageValue: PdfDocument.Page
        ) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue

            footer("7")

            writeInterviewPlan(4)

            val firstTitle = activity.getString(R.string.titleBilanFormation).toUpperCase(Locale.ROOT)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 150F, 1.0F, false)

            val infoText = activity.getString(R.string.pdfInfoPage7)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.ITALIC, 10F))
            drawText(infoText, 555, 20F, 175F, 1.0F, false)

            //Title Left Rectangle
            val leftTitle = activity.getString(R.string.pdfFormations)
            var rect = Rect(20, 210, 207, 270)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 11F))
            drawText(leftTitle, 175, 26F, 233F, 1.0F, true)

            //Title Middle Rectangle
            val middleTitle = activity.getString(R.string.pdfFormationImplementation)
            rect = Rect(207, 210, 394, 270)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 11F))
            drawText(middleTitle, 177, 210F, 211F, 1.0F, true)

            //Title Right Rectangle
            val rightTitle = activity.getString(R.string.commentary)
            rect = Rect(394, 210, 580, 270)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 11F))
            drawText(rightTitle, 175, 400F, 233F, 1.0F, true)

            retrieveValue()
        }

        private fun writeEntitled(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(20, top, 207, bottom)
            canvas.drawRect(rect, borderRectangleOptions())

            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            val entitled = activity.getString(R.string.formationEntitled) + " : "
            drawText(entitled, 175, 25F, y, 1.0F, false)

            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 177, 70F, y, 1.3F, false)
        }

        private fun writeDate(value: String , y: Float) {
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            val date = activity.getString(R.string.formationDate) + " : "
            drawText(date, 175, 25F, y, 1.0F, false)

            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 177, 60F, y, 1.3F, false)
        }

        private fun writeDuration(value: String , y: Float) {
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            val duration = activity.getString(R.string.formationDuration) + " : "
            drawText(duration, 175, 25F, y, 1.0F, false)

            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 177, 70F, y, 1.3F, false)
        }

        private fun writeInitiative(value: String , y: Float) {
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            val initiative = activity.getString(R.string.formationInitiative) + " : "
            drawText(initiative, 175, 25F, y, 1.0F, false)

            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 177, 85F, y, 1.3F, false)
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

        private fun writeSecondTitle() {
            val wishText = activity.getString(R.string.pdfCertificationWish)
            val targetText = activity.getString(R.string.pdfCertificationTarget)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.BOLD, 14F))
            drawText(wishText, 450, 20F, 425F, 1.3F, false)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.BOLD, 14F))
            drawText(targetText, 450, 223F, 425F, 1.3F, false)
        }

        private fun writeSecondTitleRectangle() {

            //Title Left Rectangle
            val leftTitle = activity.getString(R.string.titleWishFormation)
            var rect = Rect(20, 455, 207, 500)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 11F))
            drawText(leftTitle, 175, 26F, 462F, 1.0F, true)

            //Title Middle Rectangle
            val middleTitle = activity.getString(R.string.formationMotivation)
            rect = Rect(207, 455, 394, 500)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 11F))
            drawText(middleTitle, 177, 210F, 462F, 1.0F, true)

            //Title Right Rectangle
            val rightTitle = activity.getString(R.string.formationModality)
            rect = Rect(394, 455, 580, 500)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.yellow)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 11F))
            drawTextWithoutJustification(rightTitle, 175, 400F, 457F, 1.0F, true)

            retrieveSecondValue()
        }

        private fun writeTarget(value: String , top: Int , bottom: Int , y: Float) {
            val rect = Rect(20, top, 207, bottom)
            canvas.drawRect(rect, borderRectangleOptions())

            textPaint.set(setTextOptions(activity.getColor(R.color.red), Typeface.NORMAL, 10F))
            drawText(value, 177, 25F, y, 1.3F, false)
        }

        private fun writeWish(value: String , y: Float) {
            textPaint.set(setTextOptions(activity.getColor(R.color.blue), Typeface.NORMAL, 10F))
            drawText(value, 177, 25F, y, 1.3F, false)
        }

        private fun writeTitleCPF() {
            val cpfText = activity.getString(R.string.titleCPF)
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.BOLD, 14F))
            drawText(cpfText, 450, 20F, 605F, 1.3F, false)
        }

        private fun writeCPFInfo(numberHours: String , date: String , yesOrNot: String) {
            val cpfHours = activity.getString(R.string.cpfNumberHours) + " : "
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(cpfHours, 450, 20F, 625F, 1.3F, false)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(numberHours, 450, 190F, 625F, 1.3F, false)

            val cpfDate = " heures, " + activity.getString(R.string.cpfDate).toLowerCase(Locale.ROOT) + " : "
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(cpfDate, 450, 220F, 625F, 1.3F, false)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(date, 450, 330F, 625F, 1.3F, false)

            val cpfYesOrNot = activity.getString(R.string.cpfQuestion)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(cpfYesOrNot, 450, 20F, 645F, 1.3F, false)
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 10F))
            drawText(yesOrNot, 450, 430F, 645F, 1.3F, false)
        }

        private fun writeTitleCPFCommentary() {
            val cpfText = activity.getString(R.string.cpfCommentary)
            textPaint.set(setUnderlineTextOptions(Color.BLACK, Typeface.BOLD, 14F))
            drawText(cpfText, 555, 20F, 665F, 1.3F, false)
        }

        private fun writeCommentaryInfo(value: String) {
            val rect = Rect(20, 690, 580, 745)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 555, 25F, 693F, 1.3F, false)
        }



        /************************************* DataBase *******************************************/
        private fun retrieveValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("bilanFormation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        //First Line
                        val entitled1 = document.get("entitled1").toString()
                        writeEntitled(entitled1, 270,340,275F)
                        val date1= document.get("date1").toString()
                        writeDate(date1, 295F)
                        val duration1= document.get("duration1").toString()
                        writeDuration(duration1, 308F)
                        val initiative1= document.get("initiative1").toString()
                        writeInitiative(initiative1, 321F)

                        val implementation1 = document.get("implementation1").toString()
                        writeInMiddleRectangle(implementation1, 270,340,275F)
                        val commentary1 = document.get("commentary1").toString()
                        writeInRightRectangle(commentary1, 270,340,275F)


                        //Second Line
                        var entitled2 = document.get("entitled2").toString()
                        if(document.get("entitled2") == null) {
                            entitled2 = "/"
                        }
                        writeEntitled(entitled2, 340,410,345F)
                        var date2 = document.get("date2").toString()
                        if(document.get("date2") == null) {
                            date2 = "/"
                        }
                        writeDate(date2, 365F)
                        var duration2= document.get("duration2").toString()
                        if(document.get("duration2") == null) {
                            duration2 = "/"
                        }
                        writeDuration(duration2, 378F)
                        var initiative2= document.get("initiative2").toString()
                        if(document.get("initiative2") == null) {
                            initiative2 = "/"
                        }
                        writeInitiative(initiative2, 391F)

                        var implementation2 = document.get("implementation2").toString()
                        if(document.get("implementation2") == null) {
                            implementation2 = "/"
                        }
                        writeInMiddleRectangle(implementation2, 340,410,345F)
                        var commentary2 = document.get("commentary2").toString()
                        if(document.get("commentary2") == null) {
                            commentary2 = "/"
                        }
                        writeInRightRectangle(commentary2, 340,410,345F)

                        writeSecondTitle()

                        //Second Rectangle values
                        writeSecondTitleRectangle()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        private fun retrieveSecondValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("wishFormation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        //First Line
                        val target1 = document.get("target1").toString()
                        writeTarget(target1, 500,545,505F)
                        val wish1= document.get("wish1").toString()
                        writeWish(wish1, 525F)

                        val motivation1 = document.get("motivation1").toString()
                        writeInMiddleRectangle(motivation1, 500,545,505F)
                        val modality1 = document.get("modality1").toString()
                        writeInRightRectangle(modality1, 500,545,505F)


                        //Second Line
                        var target2 = document.get("target2").toString()
                        if(document.get("target2") == null) {
                            target2 = "/"
                        }
                        writeTarget(target2, 545,590,550F)
                        var wish2= document.get("wish2").toString()
                        if(document.get("wish2") == null) {
                            wish2 = "/"
                        }
                        writeWish(wish2, 570F)

                        var motivation2 = document.get("motivation2").toString()
                        if(document.get("motivation2") == null) {
                            motivation2 = "/"
                        }
                        writeInMiddleRectangle(motivation2, 545,590,550F)
                        var modality2 = document.get("modality2").toString()
                        if(document.get("modality2") == null) {
                            modality2 = "/"
                        }
                        writeInRightRectangle(modality2, 545,590,550F)

                        //CPF
                        writeTitleCPF()
                        retrieveCPFValue()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        private fun retrieveCPFValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("cpf")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {



                        val date= document.get("date").toString()
                        val numberHours = document.get("numberHours").toString()
                        val question = document.get("question").toString()
                        writeCPFInfo(numberHours,date,question)

                        writeTitleCPFCommentary()
                        val commentary = document.get("commentary").toString()
                        writeCommentaryInfo(commentary)

                        // finish the page
                        pdfDocument.finishPage(page)

                        PdfUtil.createPage(8)

                        //Start Third Page
                        createEighthPage(PdfUtil.getCanvas(), PdfUtil.getTextPaint(), activity, PdfUtil.getPdfDocument(), PdfUtil.getPage())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }






    }
}