package com.astek.asteksupport.utils.pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.text.TextPaint
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.DataBaseUtil.Companion.retrieveLeftRectangleValue
import com.astek.asteksupport.utils.DataBaseUtil.Companion.retrieveRightRectangleValue
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.borderRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.createPage
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.drawText
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.fillRectangleOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.footer
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.setTextOptions
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.writeTabInfo
import com.astek.asteksupport.utils.pdf.SecondPagePdfUtil.Companion.createSecondPage


@Suppress("DEPRECATION")
class FirstPagePdfUtil {

    companion object {

        private lateinit var canvas: Canvas
        private lateinit var textPaint: TextPaint
        private lateinit var pdfDocument: PdfDocument
        private lateinit var page: PdfDocument.Page

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        fun createFirstPage(canvas: Canvas , textPaint: TextPaint , activity: Activity , pdfDocument: PdfDocument , page: PdfDocument.Page) {

            Companion.canvas = canvas
            Companion.textPaint = textPaint
            Companion.activity = activity
            Companion.pdfDocument = pdfDocument
            Companion.page = page

            val infoFirstPage =
                "Le dossier de compétences du salarié doit être mis à jour avant tout entretien annuel et envoyé au manager avant la date programmée de cet entretien.\n \n" +
                        "Les informations en jaune doivent être remplies par le salarié avant l’entretien, le support devant être renvoyé au manager au moins 48h avant la date programmée de cet entretien.\n \n" +
                        "Les informations en rouge doivent être complétées par le manager pendant l’entretien.\n \n"

            val employeeIdentityTitle = "Identité du Salarié / Cadre de l’Entretien"

            createPage(1)

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


        fun writeInLeftRectancle(value: String, x: Float, y: Float) {
            //Left Rectangle
            val rect = Rect(80, 250, 298, 470)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, x, y, 1.3F, false)

            //Right Rectangle
            retrieveRightRectangleValue()
        }

        fun writeInRightRectancle(value: String, x: Float, y: Float) {
            val rect = Rect(298, 250, 515, 470)
            canvas.drawRect(rect, borderRectangleOptions())
            textPaint.set(setTextOptions(Color.BLACK, Typeface.NORMAL, 10F))
            drawText(value, 208, x, y, 1.3F, false)

            // finish the page
            pdfDocument.finishPage(page)

            //Start Second Page
            createSecondPage(canvas, textPaint, activity, pdfDocument, page)
        }
    }

}