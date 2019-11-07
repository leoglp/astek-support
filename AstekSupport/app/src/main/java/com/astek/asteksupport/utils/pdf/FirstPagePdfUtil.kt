package com.astek.asteksupport.utils.pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.text.TextPaint
import android.util.Log
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeDocumentId
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.borderRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.fillRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.getCanvas
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.getPage
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.getPdfDocument
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.getTextPaint
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeTabInfo
import com.astek.asteksupport.utils.pdf.SecondPagePdfUtil.Companion.createSecondPage
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("DEPRECATION")
class FirstPagePdfUtil {

    companion object {

        private const val TAG = "FirstPdfPage"


        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page
        @SuppressLint("StaticFieldLeak")
        private var db = FirebaseFirestore.getInstance()

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        fun createFirstPage(canvasValue: Canvas , textPaintValue: TextPaint , activityValue: Activity ,
                            pdfDocumentValue: PdfDocument , pageValue: PdfDocument.Page) {

            canvas = canvasValue
            textPaint = textPaintValue
            activity = activityValue
            pdfDocument = pdfDocumentValue
            page = pageValue
            db = FirebaseFirestore.getInstance()

            val infoFirstPage =
                "Le dossier de compétences du salarié doit être mis à jour avant tout entretien annuel et envoyé au manager avant la date programmée de cet entretien.\n \n" +
                        "Les informations en jaune doivent être remplies par le salarié avant l’entretien, le support devant être renvoyé au manager au moins 48h avant la date programmée de cet entretien.\n \n" +
                        "Les informations en rouge doivent être complétées par le manager pendant l’entretien.\n \n"

            val employeeIdentityTitle = "Identité du Salarié / Cadre de l’Entretien"

            textPaint.set(setTextOptions(Color.BLACK, Typeface.ITALIC, 14F))
            drawText(infoFirstPage, 555, 20F, 100F, 1.0F, false)

            //Title Rectangle
            val rect = Rect(80, 230, 515, 250)
            canvas.drawRect(rect, fillRectangleOptions(activity.getColor(R.color.green)))
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.BOLD, 12F))
            drawText(employeeIdentityTitle, 435, 80F, 231F, 1.0F, true)

            writeTabInfo()
            footer("1")

            retrieveLeftRectangleValue()
        }


        private fun writeInLeftRectangle(value: String) {
            //Left Rectangle
            val rect = Rect(80, 250, 298, 470)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208,85F,255F, 1.3F, false)

            //Right Rectangle
            retrieveRightRectangleValue()
        }

        private fun writeInRightRectangle(value: String) {
            val rect = Rect(298, 250, 515, 470)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, 303F,255F, 1.3F, false)

            // finish the page
            pdfDocument.finishPage(page)


            PdfUtil.createPage(2)

            //Start Second Page
            createSecondPage(getCanvas(), getTextPaint(), activity, getPdfDocument(), getPage())
        }



        /************************************* DataBase *******************************************/
        @SuppressLint("DefaultLocale")
        fun retrieveLeftRectangleValue() {

            var text: String
            db.collection("users").document(employeeDocumentId)
                .get()
                .addOnSuccessListener { result ->

                    val name = result.get("name").toString()
                    val surname = result.get("surname").toString()
                    val society = result.get("society").toString()
                    val birthDate = result.get("birthdate").toString()
                    val enterDate = result.get("enterDate").toString()
                    val experimentDate = result.get("experimentDate").toString()
                    val function = result.get("function").toString()
                    val diplom = result.get("diplom").toString()
                    val obtentionDate = result.get("obtentionDate").toString()

                    text = "NOM Prénom : $name $surname \n \n"
                    text += "Société : $society \n \n"
                    text += "Date de naissance : $birthDate \n \n"
                    text += "Date d'entrée chez Astek : $enterDate \n \n"
                    text += "Nbre d’Années d’expérience total : $experimentDate \n \n"
                    text += "Fonction : $function \n \n"
                    text += "Diplôme / Ecole : $diplom \n \n"
                    text += "Date d'obtention : $obtentionDate \n \n"

                    writeInLeftRectangle(text)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        @SuppressLint("DefaultLocale")
        fun retrieveRightRectangleValue() {
            var text: String

            db.collection("users").document(employeeDocumentId)
                .collection("interviewContext")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val bilanDate = document.get("bilanDate").toString()
                        val previousDate = document.get("previousDate").toString()
                        val interviewDate = document.get("interviewDate").toString()
                        val managerName = document.get("managerName").toString()


                        text = "Année du bilan : $bilanDate \n \n"
                        text += "Date de l’entretien N-1 : $previousDate \n \n"
                        text += "Date de l’entretien N : $interviewDate \n \n"
                        text += "Manager : $managerName \n \n"

                        writeInRightRectangle(text)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }



    }

}