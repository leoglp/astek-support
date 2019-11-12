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
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setUnderlineTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeInterviewPlan
import com.astek.asteksupport.utils.pdf.ThirdPagePdfUtil.Companion.createThirdPage
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("DEPRECATION")
class SecondPagePdfUtil {

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


        fun createSecondPage(canvasValue: Canvas , textPaintValue: TextPaint , activityValue: Activity ,
                             pdfDocumentValue: PdfDocument , pageValue: PdfDocument.Page) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue

            footer("2")

            writeInterviewPlan(1)

            val firstTitle = "BILAN DES MISSIONS / PROJETS DE L’ANNEE ECOULEE"
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.BOLD, 14F))
            drawText(firstTitle, 555, 20F, 150F,1.0F, false)

            val firstInfo = "Décrire succinctement les activités confiées, les compétences mises en œuvre et " +
                    "les résultats obtenus (Client, Période, Rôle occupé, Responsabilité, Environnement Technique et Fonctionnel)"
            textPaint.set(setTextOptions(Color.BLACK, Typeface.ITALIC, 12F))
            drawText(firstInfo, 555, 20F, 180F, 1.0F, false)


            //First Rectangle
            retrieveFirstRectangleValue()
        }


        private fun writeInFirstRectangle(value: String) {
            //First Rectangle
            val rect = Rect(20, 230, 580, 380)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 555, 25F, 233F, 1.3F, false)

            val secondInfo = "Appréciation du salarié sur les réalisations de l’année"
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.blue), Typeface.BOLD, 14F))
            drawText(secondInfo, 555, 20F, 390F,1.0F, false)

            //Second Rectangle
            retrieveSecondRectangleValue()
        }

        private fun writeInSecondRectangle(value: String) {
            //Second Rectangle
            val rect = Rect(20, 420, 580, 570)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 555, 25F, 423F, 1.3F, false)

            val secondInfo = "Appréciation du manager des réalisations de l’année"
            textPaint.set(setUnderlineTextOptions(activity.getColor(R.color.red), Typeface.BOLD, 14F))
            drawText(secondInfo, 555, 20F, 580F,1.0F, false)

            //Third Rectangle
            retrieveThirdRectangleValue()
        }

        private fun writeInThirdRectangle(value: String) {
            //Third Rectangle
            val rect = Rect(20, 610, 580, 760)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 555, 25F, 613F, 1.3F, false)

            pdfDocument.finishPage(page)

            PdfUtil.createPage(3)

            //Start Third Page
            createThirdPage(PdfUtil.getCanvas(), PdfUtil.getTextPaint(), activity, PdfUtil.getPdfDocument(), PdfUtil.getPage())
            //MailUtil.sendMailWithPdf(activity)
        }




        /************************************* DataBase *******************************************/
        private fun retrieveFirstRectangleValue() {
            db.collection("users").document(employeeDocumentId)
                .collection("bilanMission")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val bilanMission = document.get("bilanMission").toString()
                        writeInFirstRectangle(bilanMission)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        private fun retrieveSecondRectangleValue() {
            var text: String
            db.collection("users").document(employeeDocumentId)
                .collection("employeeAppreciation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val gain = document.get("gain").toString()
                        val improvement = document.get("improvement").toString()
                        val weaknesses = document.get("weaknesses").toString()

                        text = "Apports : $gain \n \n \n"
                        text += "Points Forts : $improvement \n \n \n"
                        text += "Points à améliorer : $weaknesses"

                        writeInSecondRectangle(text)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        private fun retrieveThirdRectangleValue() {
            var text: String
            db.collection("users").document(employeeDocumentId)
                .collection("managerAppreciation")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val gain = document.get("gain").toString()
                        val improvement = document.get("improvement").toString()
                        val weaknesses = document.get("weaknesses").toString()

                        text = "Apports : $gain \n \n \n"
                        text += "Points Forts : $improvement \n \n"
                        text += "Points à améliorer : $weaknesses"

                        writeInThirdRectangle(text)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


    }

}